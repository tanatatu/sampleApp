package com.excellence.ebase6;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.excellence.dqube.base.BisinessLogic;
import com.excellence.dqube.base.IBisinessLogic;
import com.excellence.dqube.base.IModel;
import com.excellence.dqube.base.PModel;

/**
 * 画面用メッセージ取得ロジック
 * XMLを読み込んで、メッセージデータを応答する
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJMessage extends BisinessLogic implements IBisinessLogic {

	private static String XML_FILE = "control/msg.xml";

	private List<String> XML_PAMS;
	private String XML_KEY_LANG = "en";

	@Override
	public void setModel(IModel model){
		super.setModel(model);

		this.XML_KEY_LANG = firstParam("lang");
		this.XML_PAMS = Arrays.asList(getParam("ids[]"));
	}

	//ビジネスロジック部
	public boolean innerLogic(){

		boolean rtnFlg = true; //処理結果

		//格納モデルをインスタンス化
		outModel = new PModel();

		$log.trace("innerLogic start");

		//基本XMLをファイル化
		File xml = new File( inModel.getStringData("fullpath") + XML_FILE );
		$log.trace("XML File : " + inModel.getStringData("fullpath") + XML_FILE);

		//表示用モデルにメッセージリストを保存
		outModel.setData("json", CreateMessages.createMessages(xml, this.XML_PAMS, this.XML_KEY_LANG));

		return rtnFlg;
	}

}