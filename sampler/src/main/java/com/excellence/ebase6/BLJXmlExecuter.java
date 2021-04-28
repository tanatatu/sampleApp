package com.excellence.ebase6;

import com.excellence.dqube.base.BLJsonMap4XML;
import com.excellence.dqube.base.IBisinessLogic;
import com.excellence.dqube.base.IModel;
/**
 * 指定したXMLを読み込み、JsonMapを応答する
 *
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJXmlExecuter extends BLJsonMap4XML implements IBisinessLogic {


	@Override
	public void setModel(IModel model){

		super.setModel(model);

		super.pageTitle = "xml";

		//XML
		super.defaultXML = firstParam("xmlfile");

		//キー値
		super.defaultKeyTag = firstParam("xmlKey");

	}

}
