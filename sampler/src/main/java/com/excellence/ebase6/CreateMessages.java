package com.excellence.ebase6;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.excellence.dqube.base.IModel;
import com.excellence.dqube.base.XMLParserAPI;
/**
 * Messagesを作成する
 *
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class CreateMessages {


	private static String XML_KEY = "message";

	public static Messages createMessages(File xml,List<String> pams,String lang){
		//応答するモデル
		Messages jmsg = new Messages();

		Map<String,String> jmap = new HashMap<String,String>();

		if(xml.exists()){

			String keys[] = {lang};
			IModel messages = XMLParserAPI.parseXml4Model(xml, XML_KEY , keys);

			//データのものを全て抜き出す
			List<String> idList = messages.getKeys();
			for(int i=0;i<idList.size();i++){
				if(pams.indexOf(idList.get(i)) > -1){
					IModel msg = (IModel) messages.getData(idList.get(i));
					jmap.put(idList.get(i), msg.getStringData(lang));
				}
			}

			//JSON型データ保管箱に格納
			jmsg.setMessage(jmap);

		}

		return jmsg;
	}
}
