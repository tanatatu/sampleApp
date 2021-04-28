package com.excellence.dqube.base;

import java.util.List;

/**
 * Model's Common Interface
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public interface IModel {

	/**
	 * Get an object
	 * @param key
	 * @return
	 */
	public Object getData(String key);

	/**
	 * Get an String data
	 * @param key
	 * @return
	 */
	public String getStringData(String key);

	/**
	 * Get an int data
	 * @param key
	 * @return
	 */
	public int getIntData(String key);


	/**
	 * Set an object
	 * @param key
	 * @param obj
	 */
	public void setData(String key,Object obj);

	/**
	 * Check contain key
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * Get Key List
	 * @return
	 */
	public List<String> getKeys();
}
