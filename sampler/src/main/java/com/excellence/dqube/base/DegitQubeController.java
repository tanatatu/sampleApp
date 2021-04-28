package com.excellence.dqube.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excellence.api.StringAPI;

import net.arnx.jsonic.JSON;

/**
 * Main Controller
 * @author S.Yoshizawa
 * @category Controler
 * @version 1.0
 * @since 1.0
 */
public class DegitQubeController extends HttpServlet {

	private static String K_QUBE = "qube";
	private static String K_PRINCIPAL = "account_principal";
	//private static String K_OUTMODEL = "model";
	private static String K_SYSTEMCONTROL = "system_control_data";

	private static String XML_KEY_LOGPATH = "syslog";
	private static String XML_KEY_LOGLEVEL = "loglevel";
	private static String XML_KEY_DBJNDI = "db-jndi";

	/**
	 * POST Process
	 */
	public void doPost(HttpServletRequest req,
						  HttpServletResponse res)
							  throws ServletException, IOException {
		doControl(req,res);

	}

	/**
	 * GET Process
	 */
	public void doGet(HttpServletRequest req,
						  HttpServletResponse res)
							  throws ServletException, IOException {
		doControl(req,res);
	}


	/**
	 * 共通処理
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doControl(HttpServletRequest req,
			  HttpServletResponse res)
					  throws ServletException, IOException {


		StringAPI $sa = new StringAPI();

		// セッション開始
		HttpSession session = req.getSession();

		// コンテキストを取得
		ServletContext context = getServletConfig().getServletContext();

		// コンテキストのリアルパス
		String contextFullPath = context.getRealPath("/");

		// ログ制御（本ロジックONLY）
		LogControl log = new LogControl();

		// BusinessLogicへの受け渡し用Model生成
		boolean pamset = true;
		IModel parameter = new InModel();

		// 処理パターンを取得
		String actionID = req.getParameter("actionID");
		if(actionID==null||actionID.equals("")) actionID = FixedValue._LOGONPAGE; //とりあえずエラーページのIDを自動セットしておく。

		// システムコントロールデータをsessionから取得
		IModel systemctl = (IModel) session.getAttribute(K_SYSTEMCONTROL);
		if(systemctl == null){
			//取得できない場合は新規作成後にsessionに保持
			//String elements[] = {XML_KEY_LOGPATH,XML_KEY_LOGLEVEL,XML_KEY_DBJNDI};
			systemctl = (IModel) XMLParserAPI.parseXml4Model(new File(contextFullPath+"control/"+FixedValue._LOG_XML_File), "SYSTEMCTL").getData("0");
			session.setAttribute(K_SYSTEMCONTROL,systemctl);
		}
		parameter.setData(K_SYSTEMCONTROL, systemctl);

		//ロジックデータをsessionから取得
		Qube qb = (Qube) session.getAttribute(K_QUBE);
		if(qb == null) {
			//取得できない場合は新規作成後にsessionに保持
			qb = new Qube();
			qb.Init(new File(contextFullPath+"/control/"+FixedValue._QUBE_XML_FILE));
			session.setAttribute(K_QUBE,qb);
		}

		// ビジネスロジック名
		String logic_name = qb.getLogicName(actionID);
		// ビューのURI
		String view_uri = qb.getURI(actionID);
		// ビジネスロジックのログレベル
		String logic_loglevel = qb.getLogLevel(actionID);
		// 認証の要・不要
		boolean logonIgnore = $sa.isTrue(qb.getLogonIgnore(actionID));

		//認証チェック
		AccountPrincipal ap = (AccountPrincipal) session.getAttribute(K_PRINCIPAL);
		if(ap == null){ //認証情報がない
			//認証情報がない->認証処理に変換
			ap = new AccountPrincipal();
			ap.setData(AccountPrincipal.K_ACCOUNT_ID, "----------------");
			if(!logonIgnore){ //認証を無視しない場合
				actionID = FixedValue._LOGONPAGE;
			}
		}

		//ログフォーマットをセット（範囲：このクラスのみ）
		String lf[] = {"%d","%t","%l",this.getClass().getName(),ap.getAccountID(),"%m"};
		log.setFormat(lf);
		log.setLogPath(systemctl.getStringData(XML_KEY_LOGPATH));
		log.setLogLevel(systemctl.getStringData(XML_KEY_LOGLEVEL));
		log.trace("Log OK");
		log.trace("LogonIgnore : " + logonIgnore);

		log.debug("ActionID : " + actionID);

		// 受け渡しモデルの生成
		IModel outModel = new PModel();

		// コンテキストデータをパラメータで渡す
		parameter.setData("fullpath", contextFullPath);

		// 渡されたリクエストパラメータを受け渡し用Modelにセット
		try{
			Map<String, String[]> map_par = req.getParameterMap();
			Set<String> map_ite1 = map_par.keySet( );
			Iterator<String> i = map_ite1.iterator( );
			while (i.hasNext()) {
				String key = i.next();
				parameter.setData(key, map_par.get(key));
			}
		} catch (Exception e){
			log.error("Exception:" + e);
			view_uri = qb.getURI(FixedValue._ERRORPAGE);
			outModel.setData("error", ""+e);
			session.setAttribute("model",outModel);
			pamset = false;
			log.trace("Set Parameter Error");
		}

		// ビジネスロジックをインスタンス化
		if(pamset){
			IBisinessLogic ic;
			try {
				ic = (IBisinessLogic) Class.forName(logic_name).newInstance();

				//ビジネスロジックにリクエストパラメータを受け渡し用Modelで渡す
				//parameter.setData(FixedValue._CLASSNAME, logic_name); //処理するクラス名をセット ←ロジック側で自動セット
				parameter.setData(FixedValue._LOGFILE, systemctl.getStringData(XML_KEY_LOGPATH));
				parameter.setData(FixedValue._LOGLEVEL, logic_loglevel);
				String logform[] = {"%d","%t","%l","%c","%a","%m"};
				parameter.setData(FixedValue._LOGFORMAT, logform);
				parameter.setData(FixedValue._ACCOUNT, ap.getStringData(AccountPrincipal.K_ACCOUNT_ID));
				parameter.setData(FixedValue._JNDI, systemctl.getStringData(XML_KEY_DBJNDI));
				ic.setModel(parameter);
				log.trace("In-Model Set OK");

				//ビジネスロジックを実行
				if(ic.execute()){
					log.trace("Execute OK");
					// 生成したモデルを取得
					outModel = ic.getModel();

				}else{
					//ここにビジネスロジックが失敗した場合のエラー処理を追加
					view_uri = qb.getURI(FixedValue._ERRORPAGE);
					outModel.setData("error", "Error occurred "+logic_name);
					log.error("Error occurred :"+logic_name);
				}

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// ここにビジネスロジックがインスタンス化できなかった場合のエラー処理を追加
				view_uri = qb.getURI(FixedValue._ERRORPAGE);
				outModel.setData("error", ""+e);
				log.error("Exception:"+e);
			} catch (Exception e) {
				// ここにビジネスロジックがエラーの場合の処理を追加
				view_uri = qb.getURI(FixedValue._ERRORPAGE);
				outModel.setData("error", ""+e);
				log.error("Logic Exception:"+e);
			}
		}

		// 認証処理の場合だけの処理
		if( actionID.equals(FixedValue._LOGONAFTPAGE) && ! view_uri.equals(FixedValue._ERRORPAGE) ){
			ap = (AccountPrincipal) outModel.getData("account");
			session.setAttribute(K_PRINCIPAL,ap);
			log.trace("Out-Model Set OK");

		}


		if(view_uri.equalsIgnoreCase("JSON")){
			log.trace("Response Json");
			//JSON型の応答
			res.setContentType("application/json;charset=utf-8");

			//JsonMap jmap = (JsonMap) outModel.getData("jsonmap");

			//JSON型でModelを書きだし
			PrintWriter out = res.getWriter();
			//String jsonText = JSON.encode(jmap);
			String jsonText = JSON.encode(outModel.getData("json"));

			log.trace("JsonString [" + jsonText + "]");
			out.println(jsonText);

		}else{
			log.trace("Response View");
			//view（jsp）にフォワード
			session.setAttribute("model",outModel);
			context.getRequestDispatcher(view_uri).forward(req,res);
		}
	}

}
