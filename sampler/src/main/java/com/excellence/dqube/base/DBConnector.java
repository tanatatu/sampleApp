package com.excellence.dqube.base;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database access nouse jdbc-pool
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class DBConnector {

	public PreparedStatement pstmt  = null;
	public Connection conn = null;

	private static String DRIVER = "org.mariadb.jdbc.Driver"; //接続するDBのドライバーを指定
	private static String DATABASE = "sampledb"; //接続するデータベース
	private static String DBUSER = "root"; //DB接続ユーザー（自環境に合わせて変更すると使えます）
	private static String DBPASSWORD = "root"; //DB接続ユーザーのパス（自環境に合わせて変更すると使えます）
	private static String JNDI = "jdbc:mysql://localhost:3306/"; //DBがLocalにない場合は、localhost部分をDBサーバーのアドレスにする
	private static String ENCODE = "utf-8";


	/**
	 * @param driver
	 * @param jndi
	 * @param user_pass
	 * @param encode
	 * @param autocommit
	 * @return
	 */
	public synchronized boolean connect(){

		//ドライバー登録
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			return false;
		}

		try {
			// データベースへ接続
			Properties info = new Properties();
			info.put("user", DBUSER);
			info.put("password", DBPASSWORD);

			Driver drv = DriverManager.getDriver(JNDI+DATABASE+ "?useUnicode=true&characterEncoding=" + ENCODE);
			this.conn = drv.connect(JNDI+DATABASE+ "?useUnicode=true&characterEncoding=" + ENCODE, info);
		} catch (Exception e) {
			return false;
		}

		try {
			// オートコミット制御
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
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
}
