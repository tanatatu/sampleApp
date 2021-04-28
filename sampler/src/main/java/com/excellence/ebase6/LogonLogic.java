package com.excellence.ebase6;

import java.sql.SQLException;

import com.excellence.dqube.base.BisinessLogicUseDB;
import com.excellence.dqube.base.IBisinessLogic;
/**
 * Logon Logic
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class LogonLogic extends BisinessLogicUseDB implements IBisinessLogic {

	@Override
	public boolean innerLogic(){
		setSQL("select prop,value from property");
		try {
			$pstm.setInt(1,0);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return true;
	}

}
