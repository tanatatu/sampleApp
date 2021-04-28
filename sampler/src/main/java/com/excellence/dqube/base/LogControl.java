package com.excellence.dqube.base;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Common Log Control
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class LogControl {
	String _LogFile = "dqube.log";
	String _DateFormat = "yyyyMMdd";
	int _NowLevel = 4;
	String _Level[] = {"TRACE","DEBUG","ERROR","INFO","NONE","CONSOLE"};
	String _Format[] = {"%d","%t","%l","%m"};

	String _ClassName = ""; //クラス名
	String _AccountName = ""; //アカウント名

	/**
	 * Set Logifile path
	 */
	public void setLogPath(String path){
		_LogFile = path;
	}

	/**
	 * Set Log level
	 */
	public void setLogLevel(int level){
		_NowLevel = level;
	}

	/**
	 * Set Log level
	 */
	public void setLogLevel(String level){
		for(int i=0;i<_Level.length;i++){
			if(_Level[i].equals(level)){
				_NowLevel = i;
				break;
			}
		}
	}

	/**
	 * Set format
	 */
	public void setFormat(String format[]){
		_Format = format;
	}

	/**
	 * Output trace log
	 */
	public void trace(String msg){
		if(_NowLevel < 1){
			output(0,msg);
		}
		if(_NowLevel == 5){
			sysout(0,msg);
		}
	}

	/**
	 * Output debug log
	 */
	public void debug(String msg){
		if(_NowLevel < 2){
			output(1,msg);
		}
		if(_NowLevel == 5){
			sysout(1,msg);
		}
	}

	/**
	 * Output error log
	 */
	public void error(String msg){
		if(_NowLevel < 3){
			output(2,msg);
		}
		if(_NowLevel == 5){
			sysout(2,msg);
		}
	}

	/**
	 * Output info log
	 */
	public void info(String msg){
		if(_NowLevel < 4){
			output(3,msg);
		}
		if(_NowLevel == 5){
			sysout(3,msg);
		}
	}

	/**
	 * Output now-level log
	 */
	public void out(String msg){
		output(_NowLevel,msg);
	}

	/**
	 * Output now-level console
	 */
	public void console(String msg){
		sysout(_NowLevel,msg);
	}

	/**
	 * Output log message
	 */
	private void output(int level,String msg){
		Date date =new Date();

		// SimpleDateFormatインスタンス生成
		SimpleDateFormat formatter1,formatter2,formatter3;

		// yyyy/MM/dd HH:mm:ss形式
		formatter2 = new SimpleDateFormat("yyyy/MM/dd");
		formatter3 = new SimpleDateFormat("HH:mm:ss.SSS");

		// 形式
		formatter1 = new SimpleDateFormat(_DateFormat);
		// ログファイルの日付
		String fdate = formatter1.format(date);

		// 出力用日付
		String date_value = formatter2.format(date);
		String time_value = formatter3.format(date);

		try{
			FileWriter fout = new FileWriter(_LogFile + "." + fdate,true);
			for(int i=0;i<_Format.length;i++){
				if(_Format[i].equals("%d")){
					fout.write(date_value);
				}
				if(_Format[i].equals("%t")){
					fout.write(time_value);
				}
				if(_Format[i].equals("%l")){
					fout.write(_Level[level]);
				}
				if(_Format[i].equals("%m")){
					fout.write(msg);
				}
				if(_Format[i].equals("%c")){
					fout.write(_ClassName);
				}
				if(_Format[i].equals("%a")){
					fout.write("["+_AccountName+"]");
				}
				if(_Format[i].indexOf("%")<0){
					fout.write(_Format[i]);
				}
				if(i < (_Format.length - 1) ){
					fout.write(" ");
				}else{
					fout.write("\r\n");
				}
			}
			fout.close();
		}catch(IOException e){}
	}

	/**
	 * Output message for console
	 */
	private void sysout(int level,String msg){
		Date date =new Date();

		// SimpleDateFormatインスタンス生成
		SimpleDateFormat formatter1,formatter2;

		// yyyy/MM/dd HH:mm:ss形式
		formatter1 = new SimpleDateFormat("yyyy/MM/dd");
		formatter2 = new SimpleDateFormat("HH:mm:ss.SSS");

		// 出力用日付
		String date_value = formatter1.format(date);
		String time_value = formatter2.format(date);

		String out = "";

		for(int i=0;i<_Format.length;i++){
			if(_Format[i].equals("%d")){
				out += date_value + " ";
			}
			if(_Format[i].equals("%t")){
				out += time_value + " ";
			}
			if(_Format[i].equals("%l")){
				out += _Level[level];
			}
			if(_Format[i].equals("%m")){
				out += msg;
			}
			if(_Format[i].equals("%c")){
				out += _ClassName;
			}
			if(_Format[i].equals("%a")){
				out += "["+_AccountName+"]";
			}
			if(_Format[i].indexOf("%")<0){
				out += _Format[i];
			}
			if(i < (_Format.length - 1) ){
				out += " ";
			}
		}
		System.out.println(out);

	}

	/***** OPTION ****/

	/**
	 * Set class name
	 * @param classname
	 */
	public void setClassName(String classname){
		_ClassName = classname;
	}

	/**
	 * Set account name
	 * @param accountname
	 */
	public void setAccountName(String accountname){
		_AccountName = accountname;
	}

}
