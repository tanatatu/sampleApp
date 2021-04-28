package com.excellence.dqube.base;

/**
 * Fixed Value
 * @author S.Yoshizawa
 * @category Fixedvalue
 * @version 1.0
 * @since 1.0
 */
public interface FixedValue {

	public static String _LOGFILE = "_logfile_";	//LOGFILE KEY

	public static String _LOGLEVEL = "_loglevel_";	//LOG LEVEL KEY

	public static String _LOGFORMAT = "_logformat_"; //LOG FORMAT KEY

	public static String _ACCOUNT = "_accountname_"; //Account Name KEY

	public static String _CLASSNAME = "_classname_"; //Class Name KEY

	public static String _JNDI = "_dbjndi_"; //JNDI KEY


	/** Fixed **/
	public static String _ENCKEY = "1EXCELLENCE0EBASE6SYSTEM20172018"; //Encrypt KEY

	/** PAGE ID **/

	public static String _ERRORPAGE = "ERROR000"; //Error Page ID

	public static String _LOGONPAGE = "LOGON000"; //Logon Page ID

	public static String _LOGONAFTPAGE = "LOGON001"; //Logon Process Page ID

	/** XML FILE **/

	public static String _LOG_XML_File = "system.xml"; //System Parameter XML

	public static String _QUBE_XML_FILE = "control.xml"; //Qube Control XML

	public static String _COLNAME_XML_FILE = "colname.xml"; //Database Column Name Control XML
}
