package com.excellence.dqube.base;

import java.util.Map;
/**
 * JSON型に変換用MODEL
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public class JsonResult {
	private Map<String,String> result;

	public void setResult(Map<String,String> result) { this.result = result; }

	public Map<String,String> getResult() { return this.result; }
}
