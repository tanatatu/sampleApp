package com.excellence.ebase6;

import com.excellence.dqube.base.BLJsonMap4DB;
import com.excellence.dqube.base.IBisinessLogic;
import com.excellence.dqube.base.IModel;
/**
 * 指定したSQLを実行する
 * SQLを実行し、JsonMapを応答する
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BLJSQLExecuter extends BLJsonMap4DB implements IBisinessLogic {


	@Override
	public void setModel(IModel model){

		super.setModel(model);

		super.pageTitle = "db";

		super.defaultSQL = firstParam("sqltxt");

	}

}
