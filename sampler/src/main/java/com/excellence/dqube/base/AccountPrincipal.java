package com.excellence.dqube.base;
/**
 * Model for Account Principal
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public class AccountPrincipal extends PModel implements IModel {

	public static String K_ACCOUNT_ID = "accountid";
	public static String K_ACCOUNT_NAME = "accountname";
	public static String K_ACCOUNT_GROUPS = "accountgroups";

	/**
	 * get Account-ID
	 * @return
	 */
	public String getAccountID(){
		return getStringData(K_ACCOUNT_ID);
	}

	/**
	 * get AccountName
	 * @param accountname
	 * @return
	 */
	public String getAccountName(){
		String accountname[] = (String[]) getData(K_ACCOUNT_NAME);
		return accountname[0];
	}

	/**
	 * get AccountName locale
	 * @param accountname
	 * @return
	 */
	public String getAccountNameLocale(){
		String accountname[] = (String[]) getData(K_ACCOUNT_NAME);
		return accountname[1];
	}


}
