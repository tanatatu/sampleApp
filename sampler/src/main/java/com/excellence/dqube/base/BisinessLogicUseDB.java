package com.excellence.dqube.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Logic's Base class for database
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */

public class BisinessLogicUseDB extends BisinessLogic implements IBisinessLogic {

	public DBConnectionUsePool $db = new DBConnectionUsePool();
	public PreparedStatement $pstm = null;
	public ResultSet $recode = null;

	@Override
	public boolean execute(){
		$log.trace("Execute:" + getClass().getName());

		$db.connect((String)inModel.getData(FixedValue._JNDI));

		boolean rtn = innerLogic();

		try {
			if(! $pstm.isClosed()) $pstm.close();
		} catch (SQLException e) {
			$log.error("Exception : " + e);
		}

		$db.close();

		return rtn;
	}

	@Override
	public boolean innerLogic(){
		return true;
	}

	public void setSQL(String sql){
		$log.trace("Set sql [" + sql + "]");
		$db.sqlSet(sql);
		$pstm = $db.getStatement();
	}

	/*
	 * @return boolean true: exists resultset.
	 */
	public boolean executeSQL() throws SQLException{
		$log.trace("sql execute start");
		if($pstm.execute()){
			$log.trace("execute success");
			$recode = $pstm.getResultSet();
			return true;
		}
		return false;
	}
}
