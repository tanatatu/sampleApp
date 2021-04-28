
package com.excellence.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
* @author S.Yoshizawa
* @category Excellence Framework 1.0
* @version 1.0
* This is frame work utility for date.
*/
public class DateAPI {

	private StringAPI su = new StringAPI();
	private Locale locale = Locale.JAPAN;

	/**
	 * 言語のセット
	 * @param locale
	 */
	public void setLocale(Locale locale){
		this.locale = locale;
	}

	/**
	 * 言語の取得
	 * @return
	 */
	public Locale getLocale(){
		return this.locale;
	}

	/**
	 * 日本語表記
	 */
	public void localeJapan(){
		this.locale = Locale.JAPAN;
	}

	/**
	 * 英語表記
	 */
	public void localeEnglish(){
		this.locale = Locale.ENGLISH;
	}

	/**
	 * 年齢を計算する
	 */
	public int getAge(String birthdayYMD){
		int year = su.toInt(parseYMDFormat(birthdayYMD,"yyyyy"));
		int monthday = su.toInt(parseYMDFormat(birthdayYMD,"MMdd"));
		int today_year = su.toInt(getToday("yyyy"));
		int today_monthday = su.toInt(getToday("MMdd"));

		int diff_year = today_year - year;
		if(today_monthday < monthday) diff_year--;
		return diff_year;
	}

	/**
	 * 指定日は何週目
	 */
	public int getWeekofMonth(String ymd){
		String ym = this.parseYMDFormat(ymd, "yyyyMM");
		int d = Integer.parseInt(this.parseYMDFormat(ymd, "dd"));
		for(int i=1;i<7;i++){
			String st = getWeekStart(ym,i);
			int st_d = Integer.parseInt(this.parseYMDFormat(st, "dd"));
			String ed = getWeekEnd(ym,i);
			int ed_d = Integer.parseInt(this.parseYMDFormat(ed, "dd"));
			if(st_d <= d && ed_d >= d){
				//期間の間
				return i;
			}
			if(st_d > ed_d){
				//月またぎ
				if(st_d <= d || ed_d >= d){
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * 週の開始日を得る
	 * @param ym 年月
	 * @param week 週目
	 * @return yyyymmdd
	 */
	public String getWeekStart(String ym,String week){

		//1.まず、1日が何曜日かを求める
		int week_1 = su.week2int(getWeekday(ym+"01"));

		//2.何日が日替わりかを求める
		int[] chgday = {1+(7-week_1),8+(7-week_1),15+(7-week_1),22+(7-week_1),29+(7-week_1),36+(7-week_1)};

		return getDayFrom(ym+su.sp0(2,chgday[Integer.parseInt(week)-1]),"D",-7,"yyyyMMdd");

	}
	public String getWeekStart(String ym,int week){
		return getWeekStart(ym,""+week);
	}

	/**
	 * 週の最終日を得る
	 * @param ym 年月
	 * @param week 週目
	 * @return yyyymmdd
	 */
	public String getWeekEnd(String ym,String week){

		//1.まず、1日が何曜日かを求める
		int week_1 = su.week2int(getWeekday(ym+"01"));

		//2.何日が日替わりかを求める
		int[] chgday = {(7-week_1),7+(7-week_1),14+(7-week_1),21+(7-week_1),28+(7-week_1),35+(7-week_1)};

		return getDayFrom(ym+su.sp0(2,chgday[Integer.parseInt(week)-1]),"D",0,"yyyyMMdd");

	}
	public String getWeekEnd(String ym,int week){
		return getWeekEnd(ym,""+week);
	}

	/**
	 * 日のみを抽出
	 * @param yyyymmdd
	 * @return
	 */
	public String cutoutDay(String yyyymmdd){
		if(yyyymmdd.length() == 8){
			return yyyymmdd.substring(6,8);
		}
		if(yyyymmdd.length() == 10){
			return yyyymmdd.substring(8,10);
		}
		return null;
	}

	/**
	 * 月のみを抽出
	 * @param yyyymmdd
	 * @return
	 */
	public String cutoutMonth(String yyyymmdd){
		if(yyyymmdd.length() == 8){
			return yyyymmdd.substring(4,6);
		}
		if(yyyymmdd.length() == 10){
			return yyyymmdd.substring(5,7);
		}
		return null;
	}

	/**
	 * 年のみを抽出
	 * @param yyyymmdd
	 * @return
	 */
	public String cutoutYear(String yyyymmdd){
		if(yyyymmdd.length() == 8){
			return yyyymmdd.substring(0,4);
		}
		if(yyyymmdd.length() == 10){
			return yyyymmdd.substring(0,4);
		}
		return null;
	}

	/**
	 * X時間X分形式に変換
	 * @param time
	 * @return
	 */
	public String itime2s(double time){
	  String rtime = "0時間0分";
	  if(time > 0){
		int Hour = (int) time /60;
		int Min = (int) time % 60;
		rtime = Hour + "時間" + Min + "分" ;
	  }
	  return rtime;
	}

	/**
	 * XX:XX形式に変換
	 * @param time
	 * @return
	 */
	public String itime2t(double time){
	  String rtime = "00:00";
	  if(time > 0){
		int Hour = (int) time /60;
		int Min = (int) time % 60;
		rtime = su.sp0(2,Hour) + ":" + su.sp0(2,Min);
	  }
	  return rtime;
	}

	/**
	 * XX:XX形式に変換
	 * @param time
	 * @return
	 */
	public String stime2t(String time){
	  String rtime = "00:00";
	  if(time.length()>=5){
	  	return time.substring(0,5);
	  }
	  return rtime;
	}

	/**
	 * YYYYMMDD形式のフォーマット変換
	 * @param Ymd
	 * @param fm
	 * @return
	 */
	public String parseYMDFormat(String Ymd,String fm){
		return this.getDayFrom(Ymd,"D",0,fm);
	}

	/**
	 * long値を日付表示にする
	 * @param data
	 * @return
	 */
	public String parseYMDHMSFormat(long data){
		//カレンダーオブジェクト生成
		Date dt = new Date(data);
		return dt.toString();
	}

	/**
	 * long値を日付表示にする
	 * @param data
	 * @return
	 */
	public String parseYMDHMSFormat(long data,String fmt){
		SimpleDateFormat fm = new SimpleDateFormat(fmt, locale);
		Date dt = new Date(data);
		return fm.format(dt);
	}

	/**
	 * 現在のタイムスタンプ値を取得する
	 * @return
	 */
	public long getNowTimeStamp(){
		Date now = new Date();
		return now.getTime();
	}

	  /**
	   * カレンダーインスタンス取得
	   * @return カレンダーインスタンス
	   */
	  public Calendar getClndInstance(){
	    return Calendar.getInstance();
	  }

	  /**
	  * 本日取得
	  * @return 本日日付(yyyy-MM-dd)
	  **/
	  public String getToday(){
	    // Dateオブジェクトを生成
	    Date date =new Date();

	    // SimpleDateFormatインスタンス生成
	    SimpleDateFormat formatter;
	    String value;

	    // yyyy/MM/dd HH:mm:ss形式
	    formatter = new SimpleDateFormat("yyyy-MM-dd",locale);
	    value = formatter.format(date);

	    return value;
	  }

	  /**
	   * フォーマット指定の本日日付取得
	   * @param フォーマット形式
	   * @return 本日日付
	   */
	  public String getToday(String str){
	    // Dateオブジェクトを生成
	    Date date =new Date();

	    // SimpleDateFormatインスタンス生成
	    SimpleDateFormat formatter;
	    String value;

	    // yyyy/MM/dd HH:mm:ss形式
	    formatter = new SimpleDateFormat(str,locale);
	    value = formatter.format(date);

	    return value;
	  }

	  /**
	  * 曜日取得(当日)
	  **/
	  public String getWeekday(){
		//カレンダーオブジェクト生成
		Calendar clnd = Calendar.getInstance();

		Date date = clnd.getTime();

		// SimpleDateFormatインスタンス生成
		SimpleDateFormat formatter;
		String value;

		// yyyy/MM/dd HH:mm:ss形式
		formatter = new SimpleDateFormat("E",locale);
		value = formatter.format(date);

		return value;
	  }

	  /**
	  * 曜日取得(指定日)
	  **/
	  public String getWeekday(String yyyymmdd){

		int year = Integer.parseInt(yyyymmdd.substring(0,4));
		int month = Integer.parseInt(yyyymmdd.substring(4,6)) - 1;
		int day = Integer.parseInt(yyyymmdd.substring(6,8));

		//カレンダーオブジェクト生成
		Calendar clnd = Calendar.getInstance();
		clnd.set(year,month,day,0,0,0);

		Date date = clnd.getTime();

		// SimpleDateFormatインスタンス生成
		SimpleDateFormat formatter;
		String value;

		// yyyy/MM/dd HH:mm:ss形式
		formatter = new SimpleDateFormat("E",locale);
		value = formatter.format(date);

		return value;
	  }

	  /**
	   * 指定日からの差分日
	   **/
	   public String getDayFromMain(String yyyymmdd,String ptn,int index,String fmt){

	 	int year = Integer.parseInt(yyyymmdd.substring(0,4));
	 	int month = Integer.parseInt(yyyymmdd.substring(4,6)) - 1;
	 	int day = Integer.parseInt(yyyymmdd.substring(6,8));

	 	//カレンダーオブジェクト生成
	 	Calendar clnd = getClndInstance();

	 	clnd.set(year,month,day,0,0,0);

	 	//増分を考慮
	 	if(ptn.equals("Y") || ptn.equals("y")){
	 	  clnd.add(Calendar.YEAR, index);
	 	}else
	 	if(ptn.equals("M") || ptn.equals("m")){
	 	  clnd.add(Calendar.MONTH, index);
	 	}else
	 	if(ptn.equals("D") || ptn.equals("d")){
	 	  clnd.add(Calendar.DATE, index);
	 	}

	 	Date date = clnd.getTime();

	 	// SimpleDateFormatインスタンス生成
	 	SimpleDateFormat formatter;
	 	String value;

	 	// yyyy/MM/dd HH:mm:ss形式
	 	formatter = new SimpleDateFormat(fmt,locale);
	 	value = formatter.format(date);

	 	return value;
	   }

	   /**
	   * 指定日からの翌日、翌月、翌年
	   * （指定日、増加パターン[YMD]）
	   **/
	   public String getDayFrom(String yyyymmdd,String ptn){
	 	return getDayFromMain(yyyymmdd,ptn,1,"yyyy/MM/dd");
	   }

	   /**
	   * 指定日からの増分日取得
	   * （指定日、増分）
	   **/
	   public String getDayFrom(String yyyymmdd,int index){
	 	return getDayFromMain(yyyymmdd,"D",index,"yyyy/MM/dd");
	   }

	   /**
	   * 指定日からの増分日、増分月、増分年取得
	   * （指定日、増加パターン[YMD]、増分）
	   **/
	   public String getDayFrom(String yyyymmdd,String ptn,int index){
	 	return getDayFromMain(yyyymmdd,ptn,index,"yyyy/MM/dd");
	   }

	   /**
	   * 指定日からの増分日、増分月、増分年 フォーマット指定取得
	   * （指定日、増加パターン[YMD]、増分、フォーマット）
	   **/
	   public String getDayFrom(String yyyymmdd,String ptn,int index,String fmt){
	 	return getDayFromMain(yyyymmdd,ptn,index,fmt);
	   }

	   /**
	   * 当日からの増分日取得
	   * arg1 String: Y or M or D
	   * arg2 int: 増分
	   * arg3 String: フォーマット
	   **/
	   private String getDayMain(String ptn,int index,String fmt){
	 	//カレンダーオブジェクト生成
	 	Calendar clnd = getClndInstance();

	 	//増分を考慮
	 	if(ptn.equals("Y") || ptn.equals("y")){
	 	  clnd.add(Calendar.YEAR, index);
	 	}else
	 	if(ptn.equals("M") || ptn.equals("m")){
	 	  clnd.add(Calendar.MONTH, index);
	 	}else
	 	if(ptn.equals("D") || ptn.equals("d")){
	 	  clnd.add(Calendar.DATE, index);
	 	}

	 	Date date = clnd.getTime();

	 	// SimpleDateFormatインスタンス生成
	 	SimpleDateFormat formatter;
	 	String value;

	 	// yyyy/MM/dd HH:mm:ss形式
	 	formatter = new SimpleDateFormat(fmt,locale);
	 	value = formatter.format(date);

	 	return value;
	   }

	   /**
	   * 当日からの増加日、増加月、増加年取得
	   * （増加パターン[YMD]、増分）
	   **/
	   public String getDay(String ptn,int index){
	 	return getDayMain(ptn,index,"yyyy/MM/dd");
	   }

	   /**
	   * 当日からの増加日、増加月、増加年 フォーマット指定取得
	   * （増加パターン[YMD]、増分、フォーマット指定）
	   **/
	   public String getDay(String ptn,int index,String fmt){
	 	return getDayMain(ptn,index,fmt);
	   }

	   /**
	   * 当日からの増加日取得
	   * （増分）
	   **/
	   public String getDay(int index){
	 	return getDayMain("d",index,"yyyy/MM/dd");
	   }

	   /**
	   * 当日からの増加日 フォーマット指定取得
	   * （増分、フォーマット指定）
	   **/
	   public String getDay(int index,String fmt){
	 	return getDayMain("d",index,fmt);
	   }

	   /**
	   * 当日フォーマット指定取得
	   * （フォーマット指定）
	   **/
	   public String getDay(String fmt){
	 	return getDayMain("",0,fmt);
	   }

	   /**
	   * 当日取得
	   **/
	   public String getDay(){
	 	return getDayMain("",0,"yyyy/MM/dd");
	   }

	   /**
	   * 年と月と週と曜日を指定された日の取得
	   **/
	   public int[] getWeekDay(int year,int month,int wofm,int dofw){
	 	int dbox[] = new int[4];
	 	month = month - 1;
	 		Calendar clnd = Calendar.getInstance();
	 		clnd.set(Calendar.MONTH,month);
	 		clnd.set(Calendar.YEAR,year);
	 		clnd.set(Calendar.WEEK_OF_MONTH,wofm);
	 		clnd.set(Calendar.DAY_OF_WEEK,dofw);

	 	dbox[0] = clnd.get(Calendar.YEAR);
	 	dbox[1] = clnd.get(Calendar.MONTH) + 1;
	 	dbox[2] = clnd.get(Calendar.DATE);
	 	dbox[3] = clnd.get(Calendar.DAY_OF_WEEK);

	 	return dbox;
	   }

	   /**
	    * 月の最終週
	    */
	   public int getLastWeekofMonth(String data){
	 	int i_year = Integer.parseInt(data.substring(0,4));
	 	int i_month = Integer.parseInt(data.substring(4,6));
	 	int index=0;
	 	for(int i=2;i<=7;i++){
	 	  int sun[] = getWeekDay(i_year,i_month,i,1);
	 	  int sat[] = getWeekDay(i_year,i_month,i,7);
	 	  if(sun[2]>sat[2]){
	 		index = i - 1;
	 		break;
	 	  }
	 	}
	 	return index;
	   }

	   /**
	   * 月の最終日取得
	   **/
	   public int getLastDayofMonth(String ym){

	 	int year = 0;
	 	int month = 0;

	 	if(ym.length() != 6){
	 		return 0;
	 	}else{
	 		year = Integer.parseInt(ym.substring(0,4));
	 		month = Integer.parseInt(ym.substring(4,6));
	 	}

	 	if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ){
	 		return 31;
	 	}else
	 	if(month == 4 || month == 6 || month == 9 || month == 11){
	 		return 30;
	 	}else
	 	if(month == 2){
	 		int i = year % 4;
	 		int j = year % 100;
	 		if( i == 0 || j == 0 ){
	 			return 29;
	 		}else{
	 			return 28;
	 		}
	 	}else{
	 		return 0;
	 	}
	   }

	   /**
	    * 日付の差分取得
	    */
	   public long getDaysDiff(String ymd1,String ymd2){
	 	long lCount =0;
	 	int year1 = Integer.parseInt(ymd1.substring(0,4));
	 	int month1 = Integer.parseInt(ymd1.substring(4,6));
	 	int day1 = Integer.parseInt(ymd1.substring(6,8));
	 	int year2 = Integer.parseInt(ymd2.substring(0,4));
	 	int month2 = Integer.parseInt(ymd2.substring(4,6));
	 	int day2 = Integer.parseInt(ymd2.substring(6,8));

	 	Calendar cl_1 = Calendar.getInstance();
	 	Calendar cl_2 = Calendar.getInstance();

	 	//日付セット
	 	cl_1.set(year1,month1,day1);
	 	cl_2.set(year2,month2,day2);

	 	//ミリ秒で差を求める
	 	lCount = cl_2.getTimeInMillis()-cl_1.getTimeInMillis();

	 	//1日は60秒の60分の24時間の1000ミリ秒だので
	 	lCount = lCount / 60 / 60 / 24 /1000;

	 	return lCount;

	   }

	   /**
	    * １０文字日付を８文字日付に変更
	    * @param ymd10
	    * @return
	    */
	   public String chg10D8D(String ymd10){
	 	  if(ymd10==null) return null;
	 	  if(ymd10.equals("")) return null;
	 	  if(ymd10.length() != 10) return null;
	 	  return ymd10.substring(0,4) + ymd10.substring(5,7) + ymd10.substring(8,10);
	   }

	   /**
	    * ８文字日付を１０文字日付に変更
	    * @param ymd8
	    * @param dem 区切り文字
	    * @return
	    */
	   public String chg8D10D(String ymd8,String dem){
	 	  String sdem = dem;
	 	  if(ymd8==null) return null;
	 	  if(ymd8.length() != 8) return null;
	 	  if(dem == null) sdem = "/";
	 	  if(dem.length() !=1) sdem = "/";
	 	  return ymd8.substring(0,4) + sdem + ymd8.substring(4,6) + sdem + ymd8.substring(6,8);
	   }

	   /**
	    * タイムスタンプ取得
	    * @return
	    */
	   public long getTimestamp(){
		   Date date =new Date();
		   return date.getTime();
	   }
}
