package com.excellence.api;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

public class DBControlAPI {
	/**
	 * フィールド
	 */

	public ResultSet result = null;
	public PreparedStatement pstmt  = null;
	public Connection conn = null;


	private ArrayList<String> errorcd = new ArrayList<String>();
	private ArrayList<String> errormsg = new ArrayList<String>();

	private boolean closeflg = true;
	private boolean autocommit = true;


	/**
	 * コンストラクタ
	 */
	public DBControlAPI(){
	}

	/**
	 * パラメータ指定接続（XMLを使わない方式用）
	 *
	 * @param driver
	 * @param jndi
	 * @param user_pass
	 * @param encode
	 * @param autocommit
	 * @return
	 */
	public boolean connect(String driver,String jndi,String user_pass[],String encode,boolean autocommit){
		//ドライバー登録
		try {

			Class.forName(driver);
		} catch (Exception e) {
			setDBErrorCd("DBA0-0101",e.getMessage());
			return false;
		}

		try {
			// データベースへ接続
			Properties info = new Properties();
			info.put("user", user_pass[0]);
			info.put("password", user_pass[1]);

			Driver drv = DriverManager.getDriver(jndi+ "?useUnicode=true&characterEncoding=" + encode);
			this.conn = drv.connect(jndi+ "?useUnicode=true&characterEncoding=" + encode, info);
		} catch (Exception e) {
			setDBErrorCd("DBA0-0201",e.getMessage());
			return false;
		}

		try {
			// オートコミット制御
			this.autocommit = autocommit;
			this.conn.setAutoCommit(this.autocommit);

			this.closeflg = false;
		} catch (SQLException e) {
			setDBErrorCd("DBA0-0002",e.getMessage());
			return false;
		} catch (Exception e) {
			setDBErrorCd("DBA0-0001",e.getMessage());
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
	public boolean sqlSet(String sql){
		if(this.conn == null) {
			setDBErrorCd("DBA1-0001");
			return false;
		}
		try {
			this.pstmt = this.conn.prepareStatement(sql);
			//SQLセット後にパラメータを初期化
			this.pstmt.clearParameters();
		} catch (SQLException e) {
			setDBErrorCd("DBA1-0002");
			return false;
		}
		return true;
	}


	public void setInt(int no,int val){
		try {
			this.pstmt.setInt(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0001");
		}
	}

	public void setLong(int no,long val){
		try {
			this.pstmt.setLong(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0002");
		}
	}

	public void setFloat(int no,float val){
		try {
			this.pstmt.setFloat(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0003");
		}
	}

	public void setDouble(int no,double val){
		try {
			this.pstmt.setDouble(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0004");
		}
	}

	public void setBoolean(int no,boolean val){
		try {
			this.pstmt.setBoolean(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0005");
		}
	}

	public void setString(int no,String val){
		try {
			this.pstmt.setString(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0006");
		}
	}

	public void setTimestamp(int no,String val){
		try {
			Timestamp tsm = Timestamp.valueOf(val);
			this.pstmt.setTimestamp(no, tsm);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0007");
		}
	}

	public void setDate(int no,String val){
		try {
			Date date = Date.valueOf(val);
			this.pstmt.setDate(no, date);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0008");
		}
	}

	public void setTime(int no,String val){
		try {
			Time t = Time.valueOf(val);
			this.pstmt.setTime(no, t);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0009");
		}
	}

	public void setBinaryStream(int no,InputStream val){
		try {
			//this.pstmt.setBinaryStream(no, val);
			this.pstmt.setBlob(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0014");
		}
	}

	public void setBlob(int no,InputStream val){
		try {
			this.pstmt.setBlob(no, val);
		} catch (SQLException e) {
			setDBErrorCd("SQL0-0015");
		}
	}

	/**
	 * ステートメントを取得
	 * @return
	 */
	public PreparedStatement getStatement(){
		return this.pstmt;
	}

	/**
	 * SQLの実行
	 * @return
	 */
	public boolean execute(){
		if(this.pstmt==null) {
			setDBErrorCd("DBA3-0001","statement is null.");
			return false;
		}
		try {
			this.pstmt.execute();
			return true;
		} catch (Exception e) {
			setDBErrorCd("DBA3-0001",e.getMessage());
			//logMetaData();
			return false;
		}
	}


	/**
	 * コッミト処理
	 */
	public boolean commit(){
		try{
			if(this.conn != null){
				this.conn.commit();
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * ロールバック処理
	 */
	public boolean rollback(){
		try{
			if(this.conn != null){
				this.conn.rollback();
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * クローズ処理
	 */
	public void close(){
		try{
			if(this.result != null){
				this.result.close();
				this.result = null;
			}
			if(this.pstmt != null){
				this.pstmt.close();
				this.pstmt = null;
			}
			if(this.conn != null){
				this.conn.commit();
				this.conn.close();
				this.conn = null;
			}
			this.closeflg = true;
		}catch(Exception e){
			setDBErrorCd("DBA4-0001");
		}
	}

	/**
	 * 接続中であるかどうか
	 * @return
	 */
	public boolean isActive(){
		return ! this.closeflg;
	}


	/**
	 * ステートメントクローズ処理
	 */
	public void stmtClose(){
		try{
			if(this.result != null){
				this.result.close();
				this.result = null;
			}
			if(this.pstmt != null){
				this.pstmt.close();
				this.pstmt = null;
			}
			//conn.commit();
		}catch(Exception e){
			setDBErrorCd("DBA4-0002");
		}
	}

	/**
	 * 結果取得処理
	 */
	public ResultSet getResultSet(){
		try{
			this.result = this.pstmt.getResultSet();
			return this.result;
		}catch(SQLException se){
			setDBErrorCd("DBA6-0001");
			return null;
		}
	}

	  /**
	  * 結果行数取得処理
	  **/
	public int getSize(){
		try{
			return this.pstmt.getFetchSize();
		}catch(SQLException se){
			setDBErrorCd("DBA6-0002");
			return 0;
		}
	}

	  /**
	  * ResultSetのMetaData取得処理
	  **/
	public ResultSetMetaData getMetaData(){
		if(this.result == null){
			return null;
		}
		try{
			return this.result.getMetaData();
		}catch(SQLException se){
			setDBErrorCd("DBA6-0003");
			return null;
		}
	}

	  /**
	  * テーブルの列数取得処理
	  **/
	 public int getColumnCount(){
		ResultSetMetaData rmd = getMetaData();
		if(rmd == null){
			//取得失敗
			setDBErrorCd("DBA6-0004");
			return 0;
		}
		try{
			return rmd.getColumnCount()+1;
		}catch(SQLException se){
			setDBErrorCd("DBA6-0005");
			return 0;
		}
	 }

	  /**
	  * 列名取得処理
	  **/
	public String getColumnName(int index){
		ResultSetMetaData rmd = getMetaData();
		if(rmd == null){
			//取得失敗
			setDBErrorCd("DBA6-0006");
			return "";
		}
		try{
			return rmd.getColumnName(index);
		}catch(SQLException se){
			setDBErrorCd("DBA6-0007");
			return "";
		}
	}

	  /**
	  * 列タイプ取得処理
	  **/
	public String getColumnType(int index){
		ResultSetMetaData rmd = getMetaData();
		if(rmd == null){
			//取得失敗
			setDBErrorCd("DBA6-0008");
			return "";
		}
		try{
			return rmd.getColumnTypeName(index);
		}catch(SQLException se){
			setDBErrorCd("DBA6-0009");
			return "";
		}
	}

	  /**
	  * 列の桁数取得処理
	  **/
	public int getColumnLength(int index){
		ResultSetMetaData rmd = getMetaData();
		if(rmd == null){
			//取得失敗
			setDBErrorCd("DBA6-0010");
			return 0;
		}
		try{
			return rmd.getColumnDisplaySize(index);
		}catch(SQLException se){
			setDBErrorCd("DBA6-0011");
			return 0;
		}
	}

	  /**
	  * 列がNULL値を許可しているか？
	  **/
	public int chkColumnisNull(int index){
		ResultSetMetaData rmd = getMetaData();
		if(rmd == null){
			//取得失敗
			setDBErrorCd("DBA6-0012");
			return 1;
		}
		try{
			return rmd.isNullable(index);
		}catch(SQLException se){
			setDBErrorCd("DBA6-0013");
			return 1;
		}
	}

	/**
	 * エラーコード取得
	 * @return
	 */
	public ArrayList<String> getDBErrorCd(){
		return this.errorcd;
	}

	/**
	 * エラーメッセージ取得
	 * @return
	 */
	public ArrayList<String> getDBErrorMsg(){
		return this.errormsg;
	}

	/**
	 * エラーコードセット
	 * @return
	 */
	public void setDBErrorCd(String error){
		this.errorcd.add(error);
	}
	public void setDBErrorCd(String error,String addmsg){
		this.errorcd.add(error);
		this.errormsg.add(addmsg);
	}
	/**
	 * エラーの有無
	 * @return
	 */
	public boolean isError(){
		if(this.errorcd.size() > 0) return true;
		return false;
	}

}
