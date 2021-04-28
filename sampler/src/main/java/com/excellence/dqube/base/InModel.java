/**
 *
 */
package com.excellence.dqube.base;

/**
 * Input Model for DQC
 * @author S.Yoshizawa
 * @category Model
 * @version 1.0
 * @since 1.0
 *
 */
public class InModel extends PModel implements IModel {

	public InModel(){

		setData(FixedValue._LOGFILE, "dqube.log");
		setData(FixedValue._LOGLEVEL, "NONE");
		String format[] = {"%d","%t","%l","%c","%a","%m"};
		setData(FixedValue._LOGFORMAT, format);

	}
}
