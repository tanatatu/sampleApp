package com.excellence.dqube.base;

import com.excellence.api.StringAPI;

/**
 * Logic's Base class
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 *
 */
public class BisinessLogic implements IBisinessLogic {

	public IModel inModel; //use Input
	public IModel outModel; //use Output

	public LogControl $log = new LogControl(); //common log logic
	public StringAPI $sa = new StringAPI(); //String Utility

	@Override
	public boolean execute() {
		$log.trace("Execute:" + getClass().getName());
		return innerLogic();
	}

	/**
	 * Logic
	 * @return
	 */
	public boolean innerLogic(){
		return true;
	}


	@Override
	public IModel getModel() {
		if(outModel == null) outModel = new PModel();
		$log.trace("SetOutModel:" + outModel.getClass().getName());
		return outModel;
	}

	@Override
	public void setModel(IModel model){
		inModel = model;

		$log.setLogPath(model.getStringData(FixedValue._LOGFILE));
		$log.setLogLevel(model.getStringData(FixedValue._LOGLEVEL));
		$log.setFormat((String[])model.getData(FixedValue._LOGFORMAT));
		String account = model.getStringData(FixedValue._ACCOUNT);
		if(account == null) account = "-";
		$log.setAccountName(account);
		//String classname = model.getStringData(FixedValue._CLASSNAME);
		String classname = getClass().getName();
		//if(classname == null) classname = "-";
		$log.setClassName(classname);

		$log.trace("SetInModel:" + inModel.getClass().getName());
	}

	/*
	 * inModelから指定パラメータの最初の値を取り出す
	 */
	public String firstParam(String param){
		String pam = ((String[]) inModel.getData(param))[0];
		$log.trace("firstParam:" + param + " / " + pam );
		return pam;
	}

	/*
	 * inModelから指定パラメータの値を取り出す
	 */
	public String[] getParam(String param){
		$log.trace("getParam:" + param);
		String pam[] = (String[]) inModel.getData(param);
		for(int i=0;i<pam.length;i++) { $log.trace("getParam[" + param + "] / " + pam[i]); }
		return pam;
	}

}
