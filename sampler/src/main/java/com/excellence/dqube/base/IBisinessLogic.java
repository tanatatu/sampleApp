package com.excellence.dqube.base;

/**
 * Logic's Common Interface
 * @author S.Yoshizawa
 * @category Logic
 * @version 1.0
 * @since 1.0
 */
public interface IBisinessLogic {

	/**
	 * Execute processing
	 * @return
	 */
	public boolean execute();

	/**
	 * Get Model
	 * @return
	 */
	public IModel getModel();


	/**
	 * Set Model
	 * @param model
	 */
	public void setModel(IModel model);
}
