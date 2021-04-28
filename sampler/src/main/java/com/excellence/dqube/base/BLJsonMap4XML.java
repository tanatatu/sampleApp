package com.excellence.dqube.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLファイル読込結果出力用ビジネスロジック
 * XMLを読み込んで、JsonMapを応答する
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJsonMap4XML extends BisinessLogic implements IBisinessLogic {

	//画面で使うタイトル（継承先のコンストラクタでタイトルは定義する）
	public String pageTitle = "Jsonmap for xml";

	//基本XML(継承先のコンストラクタでXMLは定義する)
	public String defaultXML = "";

	//キー値
	public String defaultKeyTag = "default";

	//ビジネスロジック部
	public boolean innerLogic(){

		boolean rtnFlg = true; //処理結果

		//格納モデルをインスタンス化
		outModel = new PModel();

		$log.trace("innerLogic start");

		//応答するJsonMap
		JsonMap jsonmap = new JsonMap();

		//以下で画面のタイトルを定義する
		jsonmap.setTitle(pageTitle);

		//画面引き渡しパラメータ
		Map<String,String> pams = new HashMap<String,String>();
		pams.put("xml",defaultXML);
		pams.put("key",defaultKeyTag);

		//アラートのリストを格納するコンテナ生成
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();

		//基本XMLをファイル化
		File xml = new File( inModel.getStringData("fullpath") + defaultXML );
		$log.trace("XML File : " + inModel.getStringData("fullpath") + defaultXML);

		List<String> cols = new ArrayList<String>(); //カラム保存用リスト
		Map<String,Map<String,String>> colData = new HashMap<String,Map<String,String>>(); //カラム名保存用リスト

		//処理応答用
		Map<String,String> resultColPam = new HashMap<String,String>();
		resultColPam.put("name", "result");
		resultColPam.put("queryname", "result");
		resultColPam.put("classname", "result");
		resultColPam.put("select", "true");
		resultColPam.put("mode","text");

		if(!xml.exists()){
			//XMLファイルが存在しない
			$log.error("xml file not exist");
			cols.add("result");
			colData.put("result",resultColPam);
			jsonmap.setKeys(cols);
			jsonmap.setKeyProp(colData);
			Map<String,String> map = new HashMap<String,String>();
			map.put("result", "xml file not exist");
			result.add(map);
		}


		try{

			Document doc = XMLParserAPI.parseXml4Doc(xml);
			// ルート要素を取得
			Element root = doc.getDocumentElement();
			// page要素のリストを取得
			NodeList list = root.getElementsByTagName(defaultKeyTag);
			for(int i=0;i<list.getLength();i++){
				// 要素を取得
				Element element = (Element)list.item(i);

				//保持するモデルを生成
				Map<String,String> map = new HashMap<String,String>();
				if(!cols.contains("id")){
					//カラムリストに追加
					cols.add("id");
					//カラム表示データをマッピング
					Map<String,String> colPam = new HashMap<String,String>();
					colPam.put("name","id" );
					colPam.put("queryname", "id");
					colPam.put("classname", "c_id");
					colPam.put("select", "true");
					colPam.put("mode", "text");
					colData.put("id",colPam);
				}
				map.put("id", element.getAttribute("id"));

				//子ノードをリスト化
				NodeList nlist = element.getChildNodes();

				for(int j=1;j<nlist.getLength();j++){

					Node node = nlist.item(j);
					String elementName = node.getNodeName();

					if(!"#text".equals(elementName)){
						if(!cols.contains(elementName)){
							//カラムリストに追加
							cols.add(elementName);
							//カラム表示データをマッピング
							Map<String,String> colPam = new HashMap<String,String>();
							colPam.put("name",elementName );
							colPam.put("queryname", elementName);
							colPam.put("classname", "c_" + elementName);
							colPam.put("select", "false");
							colPam.put("mode", "text");
							colData.put(elementName,colPam);
						}

						NodeList valList = element.getElementsByTagName(elementName);
						// 要素を取得
						Element valElement = (Element)valList.item(0);
						if (valElement != null){
							// 要素の最初の子ノード（テキストノード）の値を取得
							String val = valElement.getFirstChild().getNodeValue();
							map.put(elementName, val); //カラム名,データ
					    }
					}
				}

				result.add(map); //データ格納（テーブルrowデータ）

			}

			//JSON型データ保管箱に格納
			jsonmap.setKeys(cols); //データ格納 (カラム名一覧）
			jsonmap.setKeyProp(colData); //データ格納 (カラム名、カラム日本語名のマッチング情報）

		}catch(Exception e){
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
