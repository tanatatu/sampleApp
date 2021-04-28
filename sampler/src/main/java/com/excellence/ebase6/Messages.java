package com.excellence.ebase6;

import java.util.Map;
/**
 * メッセージ用JSONモデル
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public class Messages {
	private Map<String,String> message;

	public void setMessage(Map<String,String> message){this.message = message;}

	public Map<String,String> getMessage(){return this.message;}
}
