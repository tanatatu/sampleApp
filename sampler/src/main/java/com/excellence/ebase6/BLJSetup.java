package com.excellence.ebase6;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初期セットアップ処理
 *
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
import com.excellence.dqube.base.BisinessLogicUseDB;
import com.excellence.dqube.base.IBisinessLogic;
import com.excellence.dqube.base.IModel;
import com.excellence.dqube.base.JsonResult;
import com.excellence.dqube.base.PModel;
import com.excellence.dqube.base.XMLParserAPI;
/**
 * 初期セットアップ処理
 *
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJSetup extends BisinessLogicUseDB implements IBisinessLogic {

	private static String K_SYSTEMCONTROL = "system_control_data";
	private static String K_INIT_XML = "xml-init";

	//ビジネスロジック部
	public boolean innerLogic(){

		outModel = new PModel();

		//システム設定値を取得
		IModel systemctl = (IModel)inModel.getData(K_SYSTEMCONTROL);
		//XMLファイルはシステム値から取得して、そのXMLファイルから初期セットアップ用SQLを抜き出す
		String keys[] = {"sql","skip"};
		$log.debug(inModel.getStringData("fullpath") + systemctl.getStringData(K_INIT_XML));
		IModel execSQL = XMLParserAPI.parseXml4Model(
				new File(inModel.getStringData("fullpath") + systemctl.getStringData(K_INIT_XML).split(":")[0]),
				systemctl.getStringData(K_INIT_XML).split(":")[1],
				keys);

		//応答するJsonResult
		JsonResult json = new JsonResult();
		Map<String,String> result = new HashMap<String,String>();

		//SQL１つずつを順番に実行していく
		List<String> idList = execSQL.getKeys();
		for(int i=0;i<idList.size();i++){
			IModel sql = (IModel) execSQL.getData(idList.get(i));
			String sqlstr = sql.getStringData("sql");
			$log.debug("SQL : " + sqlstr);
			setSQL(sqlstr);
			try {
				executeSQL();
			} catch (Exception e) {
				$log.debug(""+e);
				if(sql.getStringData("skip").equals("false")) {
					result.put("result", e + " ["+ sqlstr + "]");
					json.setResult(result);
					outModel.setData("json", json);
					return false;
				}
			}
		}

		result.put("result", "Success.");
		json.setResult(result);
		outModel.setData("json", json);
		return true;
	}

}
