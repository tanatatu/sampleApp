package com.excellence.dqube.base;

import java.util.List;
import java.util.Map;
/**
 * JSON型に変換用MODEL
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public class JsonMap {

	private String title; //データのタイトル
	private List<String> keys; //データキーリスト
	private Map<String,Map<String,String>> keyprop; //キーのプロパティ値
	private List<Map<String,String>> tblData; //テーブル型データ
	private Map<String,String> pams; //受け渡しパラメータ

	public String getTitle(){ return this.title; }
	public List<String> getKeys(){ return this.keys; }
	public Map<String,Map<String,String>> getTblColData(){ return this.keyprop; }
	public List<Map<String,String>> getTblData(){ return this.tblData; }
	public Map<String,String> getPams(){ return this.pams; }

	public void setTitle(String title){ this.title = title; }
	public void setKeys(List<String> keys){ this.keys = keys; }
	public void setKeyProp(Map<String,Map<String,String>> keyprop){ this.keyprop = keyprop; }
	public void setTblData(List<Map<String,String>> tblData){ this.tblData = tblData; }
	public void setPams(Map<String,String> pams) { this.pams = pams; }

}
