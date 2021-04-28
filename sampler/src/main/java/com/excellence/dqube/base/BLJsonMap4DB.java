package com.excellence.dqube.base;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DBアクセス結果出力用ビジネスロジック
 * SQLを実行し、JsonMapを応答する
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJsonMap4DB extends BisinessLogicUseDB implements IBisinessLogic {

	//画面で使うタイトル（継承先のコンストラクタでタイトルは定義する）
	public String pageTitle = "Jsonmap for db";

	//基本SQL(継承先のコンストラクタでSQLは定義する)
	public String defaultSQL = "";

	//SQLの結果並び順、フェッチ最大件数（継承先のコンストラクタでSQLは定義する）
	public String defaultSQLOrderAndLimit = "";

	//カラム名マッチング用のキー値
	public String colMatchKey = "default";

	//ビジネスロジック部
	public boolean innerLogic(){

		boolean rtnFlg = true; //処理結果

		//格納モデルをインスタンス化
		outModel = new PModel();

		$log.trace("innerLogic start");

		//検索条件を取得してString配列で格納
		//searchlistは画面側からパラメータとして配列で渡ってくる前提
		String pam[] = (String[]) inModel.getData("searchlist[]");

		//応答するJsonMap
		JsonMap jsonmap = new JsonMap();

		//以下で画面のタイトルを定義する
		jsonmap.setTitle(pageTitle);

		//アラートのリストを格納するコンテナ生成
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();


		//SQLに基本SQLをセット
		String sql = defaultSQL;

		//検索条件に合わせてSQLに追記していく
		if(pam != null){
			sql += "where ";
			for(int i=0;i<pam.length;i++){
				String pamData[] = pam[i].split("%");
				if(i>0) sql += pamData[0] + " ";
				sql += pamData[1];
				if(pamData[2].equals("between")) sql += " between ? and ? ";
				if(pamData[2].equals("equal")) sql += " = ? ";
				if(pamData[2].equals("not equal")) sql += " <> ? ";
				if(pamData[2].equals("like")) sql += " like ? ";
				if(pamData[2].equals("not like")) sql += " not like ? ";
			}
		}

		//最後に並び順、最大フェッチ数を加えてSQLを完成させる
		sql += defaultSQLOrderAndLimit;

		//画面引き渡しパラメータ
		Map<String,String> pams = new HashMap<String,String>();
		pams.put("sql",sql);

		//SQLをセット
		setSQL(sql);

		List<String> cols = new ArrayList<String>(); //カラム保存用リスト
		Map<String,Map<String,String>> colData = new HashMap<String,Map<String,String>>(); //カラム名保存用リスト

		//select以外の処理応答用
		Map<String,String> resultColPam = new HashMap<String,String>();
		resultColPam.put("name", "result");
		resultColPam.put("queryname", "result");
		resultColPam.put("classname", "result");
		resultColPam.put("select", "true");
		resultColPam.put("mode","text");

		try{
			//プリファードステートメントに変数値をセット
			if(pam != null){
				int idx = 0;
				for(int i=0;i<pam.length;i++){
					String pamData[] = pam[i].split("%");
					if(pamData[2].equals("between")) {
						$pstm.setString(++idx, pamData[3]);
						$pstm.setString(++idx, pamData[4]);
					}else if(pamData[2].equals("like")) {
						$pstm.setString(++idx,"%"+pamData[3]+"%");
					}else{
						$pstm.setString(++idx,pamData[3]);
					}
				}
			}

			if(executeSQL()) {

				if($recode == null){
					//更新系処理

					cols.add("result");
					colData.put("result",resultColPam);
					jsonmap.setKeys(cols);
					jsonmap.setKeyProp(colData);
					Map<String,String> map = new HashMap<String,String>();
					map.put("result", "success");
					result.add(map);

				} else {
					//参照系処理

					//カラムリストの作成
					ResultSetMetaData rmeta = $recode.getMetaData(); //結果のメタデータを取得
					int colLen = rmeta.getColumnCount(); //カラム数を取得

					for(int i=0;i<colLen;i++){

						cols.add(rmeta.getColumnName(i+1)); //カラムを追加していく

						//カラム表示データをマッピング
						Map<String,String> colPam = new HashMap<String,String>();
						String elements[] = {"name","queryname","classname","select","mode"};
						IModel xmlColData = (IModel) XMLParserAPI.parseXml4Model(new File(inModel.getData("fullpath")+"/control/" + FixedValue._COLNAME_XML_FILE), colMatchKey, elements).getData(rmeta.getColumnName(i+1));
						colPam.put("name", XmlTool.getData(xmlColData, "name", rmeta.getColumnName(i+1)));
						colPam.put("queryname", XmlTool.getData(xmlColData, "queryname", rmeta.getColumnName(i+1)));
						colPam.put("classname", XmlTool.getData(xmlColData, "classname", ""));
						colPam.put("select", XmlTool.getData(xmlColData, "select", "false"));
						colPam.put("mode", XmlTool.getData(xmlColData, "mode", "text"));
						colData.put(rmeta.getColumnName(i+1),colPam);
					}

					//JSON型データ保管箱に格納
					jsonmap.setKeys(cols); //データ格納 (カラム名一覧）
					jsonmap.setKeyProp(colData); //データ格納 (カラム名、カラム日本語名のマッチング情報）

					//データリストの作成
					while($recode.next()){
						//データ情報を保持するモデルを生成
						Map<String,String> map = new HashMap<String,String>();

						for(int i=0;i<colLen;i++){
							map.put(rmeta.getColumnName(i+1), $recode.getString(rmeta.getColumnName(i+1))); //カラム名,データ
						}
						result.add(map); //データ格納（テーブルrowデータ）
					}
				}
			} else {
				$log.error("execute error");
				//rtnFlg = false;
				cols.add("result");
				colData.put("result",resultColPam);
				jsonmap.setKeys(cols);
				jsonmap.setKeyProp(colData);
				Map<String,String> map = new HashMap<String,String>();
				map.put("result", "execute error");
				result.add(map);
			}

		}catch(SQLException e){
			$log.error("" + e);
			//rtnFlg = false;
			cols.add("result");
			colData.put("result",resultColPam);
			jsonmap.setKeys(cols);
			jsonmap.setKeyProp(colData);
			Map<String,String> map = new HashMap<String,String>();
			map.put("result", ""+e);
			result.add(map);
		}

		//結果をJsonMapに保存
		jsonmap.setTblData(result);
		//画面引き渡しパラメータをJsonMapにセット
		jsonmap.setPams(pams);

		//表示用モデルにアラートリストを保存
		outModel.setData("json", jsonmap);

		return rtnFlg;
	}

}

/**
 * XMLのデータのNULLチェック (内部クラス)
 *
 * XMLデータをキー検索し、キー値に合致する値を応答。
 * キー値が無効か値がとれない場合はalternativeに指定した値を応答。
 * @author S.Yoshizawa
 * @category LocalUtility
 * @version 1.0
 * @since 1.0
 *
 */
class XmlTool {
	public static String getData(IModel xmlData,String key,String alternative){
		if(xmlData == null || xmlData.getData(key) == null ) return alternative;
		return (String)xmlData.getData(key);
	}
}