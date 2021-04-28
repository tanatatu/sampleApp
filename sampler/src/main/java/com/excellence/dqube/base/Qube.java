package com.excellence.dqube.base;

import java.io.File;

/**
 * Cube management class
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class Qube {

	private IModel qube = new PModel(); //とりあえず空で作成
	private String elements[] = {"logic","urls","loglevel","logonignore"};

	public void Init(File xml){
		this.qube = XMLParserAPI.parseXml4Model(xml, "QUBE", elements);
	}

	/**
	 * ロジックの取得
	 * @param actionid
	 * @return
	 */
	public String getLogicName(String actionid){
		IModel qube = (IModel) this.qube.getData(actionid);
		return qube.getStringData("logic");
	}

	/**
	 * URIの取得
	 * @param actionid
	 * @return
	 */
	public String getURI(String actionid){
		IModel qube = (IModel) this.qube.getData(actionid);
		return qube.getStringData("urls");
	}

	/**
	 * LOG LEVELの取得
	 * @param actionid
	 * @return
	 */
	public String getLogLevel(String actionid){
		IModel qube = (IModel) this.qube.getData(actionid);
		return qube.getStringData("loglevel");
	}


	/**
	 * LOGONの必要性取得
	 * @param actionid
	 * @return
	 */
	public String getLogonIgnore(String actionid){
		IModel qube = (IModel) this.qube.getData(actionid);
		return qube.getStringData("logonignore");
	}
}
