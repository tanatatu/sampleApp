package com.excellence.dqube.base;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default Model
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 */
public class PModel implements IModel {

	Map<String,Object> data = new HashMap<String,Object>();
	List<String> keylist = new ArrayList<String>();

	@Override
	public Object getData(String key) {
		return data.get(key);
	}

	@Override
	public String getStringData(String key) {
		try {
			String tmp =  (String) data.get(key);
			return tmp;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getIntData(String key) {
		try {
			int tmp =  Integer.parseInt( (String) data.get(key) );
			return tmp;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public void setData(String key, Object obj) {
		data.remove(key);
		data.put(key, obj);
		keylist.add(key);
	}

	@Override
	public boolean containsKey(String key){
		return  data.containsKey(key);
	}

	@Override
	public List<String> getKeys(){
		return keylist;
	}

}
