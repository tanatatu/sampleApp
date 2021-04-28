package com.excellence.dqube.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Database access
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class DBConnectionUsePool {

	private PreparedStatement pstmt  = null;
	private Connection conn = null;

	/**
	 * Connection Start
	 * @return
	 */
	public synchronized boolean connect(String jndi){
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(jndi);
			this.conn = ds.getConnection();
		} catch (NamingException e) {
			return false;
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * コネクションクローズ
	 */
	public synchronized void close(){
		try {
			this.pstmt.close();
		} catch (SQLException e) {
			this.pstmt = null;
		}
		try {
			this.conn.close();
		} catch (SQLException e) {
			this.conn = null;
		}
	}

	/**
	 * SQLをセットする
	 *
	 * @param sql
	 * @return
	 */
	public synchronized boolean sqlSet(String sql){
		if(this.conn == null) {
			return false;
		}
		try {
			this.pstmt = this.conn.prepareStatement(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * ステートメントを取得
	 * @return
	 */
	public synchronized PreparedStatement getStatement(){
		return this.pstmt;
	}
}
