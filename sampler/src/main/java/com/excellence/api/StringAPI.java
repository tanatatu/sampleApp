package com.excellence.api;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
* @author S.Yoshizawa
* @category Excellence Framework 1.0
* @version 1.0
* @since 1.1 全角カナチェック追加
* This is frame work utility for String operation.
*/
public class StringAPI {

	public final String PASSSTR[] = {"A","B","C","D","E","F","G",
		"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s",
		"t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};


	public String STRING_NULL=null;
	public String STRING_EMP="";

	private String ZenkakuKana = "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンァィゥェォッャュョガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴー";
	private String ZenkakuHriragana = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんぁぃぅぇぉゃゅょがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽーゑゐ";
	private String ZenkakuAlpha = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

	/**
	 * 対象外のアカウント文字列が利用されていないかのチェック
	 * @param str
	 * @return
	 */
	public boolean chkPassSTR(String str){
		for(int i=0;i<str.length();i++){
			String s = str.substring(i,i+1);
			boolean flg = true;
			for(int j=0;j<PASSSTR.length;j++){
				if(s.equals(PASSSTR[j])){
					flg = false;
					break;
				}
			}
			if(flg) return false;
		}
		return true;
	}

	/**
	 * 数値の英語化
	 * @param num
	 * @return
	 */
	public String chgNumEn(int num){
		switch(num){
			case 1:
				return "1st";
			case 2:
				return "2nd";
			case 3:
				return "3rd";
			default:
				return num +"th";
		}
	}

	/**
	 * 数値をboolean型に変換
	 * @param keyword 数値
	 * @return
	 */
	public boolean isTrue(int keyword){
		switch(keyword){
			case 0 :
				return false;
			case 1 :
				return true;
			default :
				return false;
		}
	}

	/**
	 * Boolean値に変換
	 * @param val
	 * @return
	 */
	public boolean toBoolean(String val){
		return isTrue(val);
	}

	/**
	 * 文字列をboolean型に変換
	 * @param keyword 文字列
	 * @return
	 */
	public boolean isTrue(String keyword){
		if(isNull(keyword)){
			return false;
		}
		if(isEmpty(keyword)){
			return false;
		}
		if(keyword.equalsIgnoreCase("true")){
			return true;
		}else if(keyword.equals("1")){
			return true;
		}else if(keyword.equalsIgnoreCase("yes")){
			return true;
		}else if(keyword.equalsIgnoreCase("ok")){
			return true;
		}else if(keyword.equalsIgnoreCase("on")){
			return true;
		}else if(keyword.equalsIgnoreCase("t")){
			return true;
		}else if(keyword.equals("○")){
			return true;
		}
		return false;
	}

	/**
	 * boolean型を応答
	 * @param keyword boolean値
	 * @return
	 */
	public boolean isTrue(boolean keyword){
		return keyword;
	}

	/**
	 * オブジェクトがある
	 * @param obj オブジェクトがあるなしで判断
	 * @return
	 */
	public boolean isTrue(Object obj){
		if(obj==null){
			return false;
		}
		return true;
	}

	/**
	 * 金額型に変換
	 * @param str
	 * @return
	 */
	public String toMoney(int number){
		return String.format("%1$,3d", number);
	}

	/**
	 * 16進数表記
	 * @param number
	 * @return
	 */
	public String toHexa(int number){
		return String.format("%1$x", number);
	}

	/**
	 * 8進数表記
	 * @param number
	 * @return
	 */
	public String toOctal(int number){
		return String.format("%1$o", number);
	}

	/**
	 * ゼロサプレス
	 * @param leng 必要文字列長
	 * @param str 文字列
	 * @return
	 */
	public String sp0(int leng,String str){
		return suppress(leng,'0',str);
	}

	/**
	 * ゼロサプレス（数値指定）
	 * @param leng
	 * @param str
	 * @return
	 */
	public String sp0(int leng,int str){
		return suppress(leng,'0',""+str);
	}

	/**
	 * スペースサプレス
	 * @param size
	 * @param number
	 * @return
	 */
	public String spS(int size,int number){
		return String.format("%1$"+size+"d", number);
	}

	/**
	 * 文字詰め
	 * @param leng 文字列長
	 * @param supchar 詰めるキャラクタ
	 * @param str
	 * @return
	 */
	public String suppress(int leng,char supchar ,String str){
		String chgstr = "";
		if(str.length() >= leng){
			return str;
		}
		// サプレス長
		int spress = leng - str.length();
		for(int i=0;i<spress;i++){
			chgstr = chgstr + supchar;
		}
		return (chgstr + str);
	}

	/**
	 * 改行数を数える
	 * @param str
	 * @return
	 */
	public int return_count(String str){
		int count = 0;
		String chk = replaceS2S(str,"\r\n","\n");
		for (int i = 0; i < chk.length(); i++) {
			char c1 = chk.charAt(i);
			if (c1 == '\n') {
				count ++;
			}else
			if (c1 == '\r') {
				count ++;
			}
		}
		return count;
	}

	/**
	 * 曜日の数字化
	 * @param 曜日
	 * @return 数字化した結果
	 */
	public int week2int(String week){
		if(week.equalsIgnoreCase("Sun")||week.equals("日")){
			return 0;
		}else if(week.equalsIgnoreCase("Mon")||week.equals("月")){
			return 1;
		}else if(week.equalsIgnoreCase("Tue")||week.equals("火")){
			return 2;
		}else if(week.equalsIgnoreCase("Wed")||week.equals("水")){
			return 3;
		}else if(week.equalsIgnoreCase("Thu")||week.equals("木")){
			return 4;
		}else if(week.equalsIgnoreCase("Fri")||week.equals("金")){
			return 5;
		}else if(week.equalsIgnoreCase("Sat")||week.equals("土")){
			return 6;
		}else{
			return 7;
		}
	}

	/**
	 * 数値の曜日英語短縮形化
	 * @return 曜日
	 */
	public String int2EnWeek(int week){
		switch(week){
			case 0:
				return "Sun";
			case 1:
				return "Mon";
			case 2:
				return "Tue";
			case 3:
				return "Wed";
			case 4:
				return "Thu";
			case 5:
				return "Fri";
			case 6:
				return "Sat";
			default:
				return "";
		}
	}

	/**
	 * 数値の曜日日本語短縮形化
	 * @return 曜日
	 */
	public String int2JaWeek(int week){
		switch(week){
			case 0:
				return "日";
			case 1:
				return "月";
			case 2:
				return "火";
			case 3:
				return "水";
			case 4:
				return "木";
			case 5:
				return "金";
			case 6:
				return "土";
			default:
				return "";
		}
	}

	/**
	 * 数値の英語短縮形化
	 * @return 月
	 */
	public String int2EnMon(int mon){
		switch(mon){
			case 1:
				return "JAN";
			case 2:
				return "FEB";
			case 3:
				return "MAR";
			case 4:
				return "APR";
			case 5:
				return "MAY";
			case 6:
				return "JUN";
			case 7:
				return "JUL";
			case 8:
				return "AUG";
			case 9:
				return "SEP";
			case 10:
				return "OCT";
			case 11:
				return "NOV";
			case 12:
				return "DEC";
			default:
				return "";
		}
	}

	/**
	 * 数値の英語化
	 * @return 月
	 */
	public String int2EnMonth(int mon){
		switch(mon){
			case 1:
				return "January";
			case 2:
				return "February";
			case 3:
				return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August";
			case 9:
				return "September";
			case 10:
				return "October";
			case 11:
				return "November";
			case 12:
				return "December";
			default:
				return "";
		}
	}

	/**
	 * YYYYMMDD形式の日付をYYYY/MM/DD形式に変換
	 * @param yyyymmdd
	 * @return
	 */
	public String toDate(String yyyymmdd){
		if(yyyymmdd.length() != 8){
			return yyyymmdd;
		}
		return yyyymmdd.substring(0,4)+"/"+yyyymmdd.substring(5,6)+"/"+yyyymmdd.substring(7,8);
	}

	/**
	 * メールアドレス形式かどうかのチェック
	 * @param addr
	 * @return
	 */
	public boolean isMailaddr(String addr){
		if(isNullEmpty(addr)){
			return false;
		}
		if(addr.indexOf("@") < 1){
			return false;
		}
		return true;
	}

	/**
	 * 含まれる数値をカウント
	 * @param str
	 * @return
	 */
	public int numericCount(String str){
		String NUMBER = "0123456789";
		int counter = 0;

		if (isNullEmpty(str)) return counter;

		StringCharacterIterator sci = new StringCharacterIterator(str);

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (NUMBER.indexOf((int)c) > -1) {
				counter ++;
			}
		}
		return counter;
	}

	/**
	 * 大文字英字が含まれる数
	 * @param str
	 * @return
	 */
	public int bigAlphaCount(String str){
		String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int counter = 0;

		if (isNullEmpty(str)) return counter;

		StringCharacterIterator sci = new StringCharacterIterator(str);

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (ALPHA.indexOf((int)c) > -1) {
				counter ++;
			}
		}
		return counter;
	}

	/**
	 * 小文字英字が含まれる数
	 * @param str
	 * @return
	 */
	public int smallAlphaCount(String str){

		String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		int counter = 0;

		if (isNullEmpty(str)) return counter;

		StringCharacterIterator sci = new StringCharacterIterator(str);

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (ALPHA.indexOf((int)c) > -1) {
				counter ++;
			}
		}
		return counter;
	}

	/**
	 * 英数字以外が含まれる数
	 * @param str
	 * @return
	 */
	public int signCount(String str){

		int alphanum = bigAlphaCount(str) + smallAlphaCount(str) + numericCount(str);
		int len = str.length();

		int counter = len - alphanum;

		return counter;
	}

	/**
	 * 文字列の置換を行う
	 *
	 * @param input 処理の対象の文字列
	 * @param pattern 置換前の文字列
	 * @oaram replacement 置換後の文字列
	 * @return 置換処理後の文字列
	 */
	public String substitute(String input, String pattern, String replacement) {
	    // 置換対象文字列が存在する場所を取得
	    int index = input.indexOf(pattern);

	    // 置換対象文字列が存在しなければ終了
	    if(index == -1) {
	        return input;
	    }

	    // 処理を行うための StringBuffer
	    StringBuffer buffer = new StringBuffer();

	    buffer.append(input.substring(0, index) + replacement);

	    if(index + pattern.length() < input.length()) {
	        // 残りの文字列を再帰的に置換
	        String rest = input.substring(index + pattern.length(), input.length());
	        buffer.append(substitute(rest, pattern, replacement));
	    }
	    return buffer.toString();
	}

	/**
	 * HTML 出力用に次の置換を行う
	 * & -> &amp;
	 * < -> &lt;
	 * > -> &gt;
	 * " -> &quot;
	 * ' -> &#39;
	 * ! -> &#33;
	 * SPACE -> &nbsp;
	 * @param input 置換対象の文字列
	 * @return 置換処理後の文字列
	 */
	public String HTMLEscape(String input) {
	    input = substitute(input, "&",  "&amp;");
	    input = substitute(input, "<",  "&lt;");
	    input = substitute(input, ">",  "&gt;");
	    input = substitute(input, "\"", "&quot;");
	    //input = substitute(input, " ",  "&nbsp;");
	    //input = substitute(input, "'",  "&#39;");
	    //input = substitute(input, "!",  "&#33;");
	    return input;
	}

	/**
	 * シングルクォートのエスケープ処理
	 * @param input
	 * @return
	 */
	public String SQuotesEscape(String input){
		input = substitute(input, "'",  "\\'");
	    return input;
	}

	/**
	 * 半角スペース（HTML上の）を返す
	 * @return
	 */
	public String sp(){
		return "&nbsp;";
	}

	/**
	 * 半角スペース（HTML上の）を指定文字返す
	 * @param num
	 * @return
	 */
	public String sp(int num){
		String str = "";
		for(int i=0;i<num;i++){
			str = str + "&nbsp;";
		}
		return str;
	}

	/**
	 * INT型に型変換
	 * @param intval
	 * @return
	 */
	public int toInt (String intval){
		return Integer.parseInt(intval);
	}

	/**
	 * LONG型に型変換
	 * @param longval
	 * @return
	 */
	public long toLong (String longval){
		return Long.parseLong(longval);
	}


	/**
	 * Double型に型変換
	 * @param doubleval
	 * @return
	 */
	public double toDouble (String doubleval){
		return Double.parseDouble(doubleval);
	}

	/**
	 * シングルクォートエスケープ
	 * @param str
	 * @return
	 */
	public String sq_escape(String str){
		if(isNullEmpty(str)) return str;
		return str.replaceAll("'", "''");
	}

	/**
	 * 英数字チェック
	 * @param s
	 * @return true:英数字 false:英数字以外
	 */
	public boolean isAlphaNumeric(String s) {
		String FONT_NUMBER = "0123456789";
		String FONT_ALPHA_LOWER = "abcdefghijklmnopqrstuvwxyz";
		String FONT_ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String FONT_ALPHA_NUMBER = FONT_NUMBER + FONT_ALPHA_LOWER + FONT_ALPHA_UPPER;

		if (s == null) return false;
		if (s.equals("") == true) return false;

		StringCharacterIterator sci = new StringCharacterIterator(s);

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (FONT_ALPHA_NUMBER.indexOf((int)c) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * １文字変換
	 * @param str : 対象文字列
	 * @param bf_c : 変換前のキャラクタ
	 * @param af_c : 変換後のキャラクタ
	 * @return
	 */
	public String chgChar(String str,char bf_c,char af_c){
		if (str == null) return str;
		if (str.equals("") == true) return str;
		String rtn_str = "";

		StringCharacterIterator sci = new StringCharacterIterator(str);

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (bf_c == (int)c) {
				rtn_str = rtn_str + af_c;
			}else{
				rtn_str = rtn_str + c;
			}
		}
		return rtn_str;
	}

	/**
	 * NULL文字、空文字を文字に変換
	 * @param str 元の文字
	 * @param chg 変換後の文字
	 * @return 変換後の文字列
	 */
	public String ne2s(String str,String chg){
	  String t_str = str;
	  if(isNull(t_str)){
	    t_str = chg;
	  }
	  if(isEmpty(t_str)){
	    t_str = chg;
	  }
	  return t_str;
	}

	/**
	 * NULL文字を空文字に変換
	 * @param str
	 * @return 変換後の文字列
	 */
	public String n2s(String str){
	  String t_str = str;
	  if(isNull(t_str)){
		  t_str = "";
	  }
	  return t_str;
	}

	/**
	 * NULL文字を文字列に変換
	 * @param str 変換前の文字列
	 * @param chg 変換後の文字列
	 * @return 変換後の文字列
	 */
	public String n2s(String str,String chg){
	  String t_str = str;
	  if(isNull(t_str)){
	    t_str = chg;
	  }
	  return t_str;
	}

	/**
	 * 空文字を文字列に変換
	 * @param str 変換前の文字列
	 * @param chg 変換後の文字列
	 * @return 変換後の文字列
	 */
	public String e2s(String str,String chg){
	  String t_str = str;
	  if(isEmpty(t_str)){
	    t_str = chg;
	  }
	  return t_str;
	}

	/**
	 * NULL文字を空文字に変換
	 * @param str
	 * @return 変換後の文字列
	 */
	public String[] n2s(String[] str){
	  if(str == null){
	    return str;
	  }
	  String t_str[] = str;
	  for(int i=0;i<t_str.length;i++){
	    if(isNull(t_str[i])){
		  t_str[i] = "";
	    }
	  }
	  return t_str;
	}

	/**
	 * NULL文字をint型に変換
	 * @param str 元文字列
	 * @return 変換後の数値
	 */
	public int n2i(String str){
          int t_int = 0;
	  if(! isNumeric(str)){
		  t_int = 0;
	  }else{
             try{
		  t_int = Integer.parseInt(str);
             }catch(NumberFormatException e){
                  t_int = 0;
             }
          }
	  return t_int;
	}

	/**

	/**
	 * NULL文字をint型に変換
	 * @param str 元文字列
	 * @param elseint 数値で無い場合の代わりの文字列
	 * @return 変換後の数値
	 */
	public int n2i(String str,int elseint){
          int t_int;
	  if(! isNumeric(str)){
		  t_int = elseint;
	  }else{
             try{
		  t_int = Integer.parseInt(str);
             }catch(NumberFormatException e){
                  t_int = elseint;
             }
          }
	  return t_int;
	}

	/**
	 * クォートエスケープ
	 * @author yoshizawa
	 *
	 * シングルクォートとダブルクォートをエスケープする。
	 */
	public String qtesc(String str){
		String mvstr = "";
		for (int i=0; i<str.length(); i++){
			String tmp = str.substring(i,i+1);
			if(tmp.equals("\'")){
				mvstr = mvstr + "\\\'";
			}else
			if(tmp.equals("\"")){
				mvstr = mvstr + "\\\"";
			}else{
				mvstr = mvstr + tmp;
			}
		}
		return mvstr;
	}

	/**
	 * 金額に変換(文字)
	 * @param str 金額文字列
	 * @param flg ￥記号を付ける
	 * @return
	 */
	public String parseYen(String str,boolean flg){
		String rtn = "0";
		if(flg){
			rtn = "￥0";
		}

		if(isNull(str)){
			return rtn;
		}else
		if(isEmpty(str)){
			return rtn;
		}else
		if(isNumeric(str)){
			rtn = "";
			int j = 0;
			for(int i=str.length();i>0;i--){
				if(j==3){
					rtn = "," + rtn;
					j = 0;
				}
				rtn = str.substring(i-1,i) + rtn;
				j++;
			}
			if(flg){
				rtn = "￥" + rtn;
			}
		}else{
			return rtn;
		}
		return rtn;
	}
	public String parseYen(String str){
		return parseYen(str,true);
	}
	/**
	 * 金額型に変換(数値)
	 * @param　val 金額値
	 * @return
	 */
	public String parseYen(int val,boolean flg){
		return parseYen(""+val,flg);
	}
	public String parseYen(long val,boolean flg){
		return parseYen(""+val,flg);
	}
	public String parseYen(int val){
		return parseYen(""+val,true);
	}
	public String parseYen(long val){
		return parseYen(""+val,true);
	}

	/**
	 * 文字列をBoolean型に変換
	 * @author yoshizawa
	 *
	 */
	public boolean parseBoolean(String str){
		if(str == null){
			return false;
		}
		if(str.equalsIgnoreCase("TRUE")){
			return true;
		}
		if(str.equalsIgnoreCase("YES")){
			return true;
		}
		if(str.equalsIgnoreCase("0")){
			return true;
		}
		if(str.equalsIgnoreCase("FALSE")){
			return false;
		}
		if(str.equalsIgnoreCase("NO")){
			return false;
		}
		return false;
	}

	/**
	 * 数値をBoolean型に変換
	 * @author yoshizawa
	 *
	 */
	public boolean parseBoolean(int i){
		if(i==0){
			return true;
		}
		return false;
	}

	/**
	 * 文字列をint型に変換
	 * @author yoshizawa
	 *
	 */
	public int parseInt(String str){
		int i_str = 0;
		try{
			i_str = Integer.parseInt(str);
		}catch(NumberFormatException e){
			i_str = 0;
		}
		return i_str;
	}

	/**
	 * キャラ変換
	 * @author yoshizawa
	 *
	 * @param str 変換前文字列
	 * @param frm 変換文字
	 * @param to 変換後文字列
	 * @return r_str 変換後文字列
	 */
	public String replaceC2S(String str,char frm,String to){
		return replaceS2S(str,""+frm,to);
	}

	/**
	 * String変換
	 * @author yoshizawa
	 *
	 * @param str 変換前文字列
	 * @param frm 変換文字列
	 * @param to 変換後文字列
	 * @return r_str 変換後文字列
	 */
	public String replaceS2S(String str,String frm,String to){
		String r_str = str;
		if(isNull(r_str)){
			return "";
		}else
		if(isEmpty(r_str)){
			return r_str;
		}else{
			// 変換
			int string_length = frm.length(); //変換文字列の長さ


			while(true){
				int start_index = r_str.indexOf(frm); //変換文字列の開始位置
				if(start_index < 0){
					//変換文字が無い
					break;
				}else if(start_index == 0){
					//先頭に変換文字列がある
					r_str = to + r_str.substring(string_length,r_str.length());
				}else if(start_index + string_length == r_str.length()){
					//末尾に変換文字列がある
					r_str = r_str.substring(0,start_index) + to;
				}else{
					//間に変換文字列がある。
					r_str = r_str.substring(0,start_index) + to + r_str.substring(start_index + string_length,r_str.length());
				}
			}
		}
		return r_str;
	}


	  /**
	  * 数値かどうかチェック(文字)
	  **/
	  public boolean isNumeric(String str){
	    try{
	      @SuppressWarnings("unused")
		  double in = Double.parseDouble(str);
	      in += 0;
	      return true;
	    }catch(Exception e){
	      return false;
	    }
	  }

	  /**
	  * 数値かどうかチェック(int)
	  **/
	  public boolean isNumeric(int num){
	    return true;
	  }

	  /**
	  * 数値かどうかチェック(long)
	  **/
	  public boolean isNumeric(long num){
	    return true;
	  }

	  /**
	  * 数値かどうかチェック(Double)
	  **/
	  public boolean isNumeric(double num){
	    return true;
	  }

	  /**
	  * 数値かどうかチェック(Integer)
	  **/
	  public boolean isNumeric(Integer num){
	    return true;
	  }

	  /**
	  * 数値かどうかチェック(float)
	  **/
	  public boolean isNumeric(float num){
	    return true;
	  }

	  /**
	  * 文字列の長さ(文字列）
	  **/
	  public long length(String str){
	    return str.length();
	  }

	  /**
	  * 文字列の長さ(char)
	  **/
	  public long length(char ch){
	    return 1;
	  }

	  /**
	  * 文字列の長さ(int)
	  **/
	  public long length(int num){
	    return length(""+num);
	  }

	  /**
	  * 文字列の長さ(long)
	  **/
	  public long length(long num){
	    return length(""+num);
	  }

	  /**
	  * 文字列の長さ(double)
	  **/
	  public long length(double num){
	    return length(""+num);
	  }

	  /**
	  * 文字列の長さ(float)
	  **/
	  public long length(float num){
	    return length(""+num);
	  }

	  /**
	  * 文字列の長さ(Integer)
	  **/
	  public long length(Integer num){
	    return length(""+num);
	  }

	  /**
	  * NULL値チェック(String)
	  **/
	  public boolean isNull(String str){
	    if(str == null){
	      return true;
	    }
	    return false;
	  }

	  /**
	  * NULL値チェック(int)
	  **/
	  public boolean isNull(int num){
	    return false;
	  }

	  /**
	  * NULL値チェック(long)
	  **/
	  public boolean isNull(long num){
	    return false;
	  }

	  /**
	  * NULL値チェック(float)
	  **/
	  public boolean isNull(float num){
	    return false;
	  }

	  /**
	  * NULL値チェック(double)
	  **/
	  public boolean isNull(double num){
	    return false;
	  }

	  /**
	  * NULL&空値チェック(String)
	  **/
	  public boolean isNullEmpty(String str){
		if(isNull(str)){
		  return true;
		}
		if(isEmpty(str)){
		  return true;
		}
		return false;
	  }

	  /**
	  * NULL&空値チェック(int)
	  **/
	  public boolean isNullEmpty(int num){
		return false;
	  }

	  /**
	  * NULL&空値チェック(long)
	  **/
	  public boolean isNullEmpty(long num){
		return false;
	  }

	  /**
	  * NULL&空値チェック(float)
	  **/
	  public boolean isNullEmpty(float num){
		return false;
	  }

	  /**
	  * NULL&空値チェック(double)
	  **/
	  public boolean isNullEmpty(double num){
		return false;
	  }

	  /**
	  * 空値チェック(String)
	  **/
	  public boolean isEmpty(String str){
	    if(!isNull(str) && str.trim().equals("")){
	      return true;
	    }
	    return false;
	  }

	  /**
	  * 空値チェック(int)
	  **/
	  public boolean isEmpty(int num){
	    return false;
	  }

	  /**
	  * 空値チェック(long)
	  **/
	  public boolean isEmpty(long num){
	    return false;
	  }

	  /**
	  * 空値チェック(float)
	  **/
	  public boolean isEmpty(float num){
	    return false;
	  }

	  /**
	  * 空値チェック(double)
	  **/
	  public boolean isEmpty(double num){
	    return false;
	  }


	/**
	* 文字列Ａに文字列Ｂが含まれるかどうかチェック
	**/
	public boolean isInner(String str,String str2){
	    if(str.indexOf(str2) > 0){
	      return true;
	    }
	    return false;
	}

	/**
	 * 全角カタカナチェック
	 * @param str
	 * @return
	 */
	public boolean isZenkakuKana(String str) {
		String zenkaku = this.ZenkakuKana;
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角カタカナチェック
	 * @param str
	 * @return
	 */
	public boolean isZenkakuKana(String str,boolean spacetrue) {
		String zenkaku = this.ZenkakuKana;
		if(spacetrue) zenkaku += " 　";
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角ひらがなチェック
	 * @param str
	 * @return
	 */
	public boolean isZenkakuHiragana(String str){
		String zenkaku = this.ZenkakuHriragana;
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角ひらがなチェック
	 * @param str
	 * @param spacetrue
	 * @return
	 */
	public boolean isZenkakuHiragana(String str,boolean spacetrue){
		String zenkaku = this.ZenkakuHriragana;
		if(spacetrue) zenkaku += " 　";
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角アルファベットチェック
	 * @param str
	 * @return
	 */
	public boolean isZenkakuAlpha(String str){
		String zenkaku = this.ZenkakuAlpha;
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角アルファベットチェック
	 */
	public boolean isZenkakuAlpha(String str,boolean spacetrue){
		String zenkaku = this.ZenkakuAlpha;
		if(spacetrue) zenkaku += " 　";
		return isZenkakuCheckMain(str,zenkaku);
	}

	/**
	 * 全角チェック（メイン）
	 * @param str
	 * @param zenkaku
	 * @return
	 */
	public boolean isZenkakuCheckMain(String str,String zenkaku){
		for (int i = 0; i < str.length(); i++) {
			char c1 = str.charAt(i);
			boolean flg = true;
			for (int j = 0; j < zenkaku.length(); j++) {
				char c2 = zenkaku.charAt(j);
				if (c1 == c2) {
					flg = false;
					break;
				}
			}
			if(flg) return false;
		}
		return true;
	}


	/**
	 * カンマトリミング
	 * @param str
	 * @return
	 */
	public String trimComma(String str){
		String rtn = str;

		if(isNullEmpty(rtn)) return rtn;

		rtn = trimCommaLeft(rtn);
		rtn = trimCommaRight(rtn);

		return rtn;
	}

	/**
	 * カンマレフトトリミング
	 * @param str
	 * @return
	 */
	public String trimCommaLeft(String str){
		String rtn = str;

		if(isNullEmpty(rtn)) return rtn;

		if(",".equals(rtn)) return "";

		if(rtn.indexOf(",") == 0) rtn = rtn.substring(1,rtn.length());

		return rtn;
	}

	/**
	 * カンマライトトリミング
	 * @param str
	 * @return
	 */
	public String trimCommaRight(String str){
		String rtn = str;

		if(isNullEmpty(rtn)) return rtn;

		if(",".equals(rtn)) return "";

		if(",".equals(rtn.substring(rtn.length()-1))) rtn = rtn.substring(0,rtn.length()-1);

		return rtn;
	}

	/**
	 * 文字列を区切り文字で分割した文字列リストを生成する
	 * @param str 文字列
	 * @param splitStr 区切り文字
	 * @return
	 */
	public String[] toStringList(String str,String splitStr){
		String tmp1[] = str.split(splitStr);

		//余計な空白配列を省く
		String tmp2[] = new String[tmp1.length];
		int trimSize = 0;
		for(int i=0;i<tmp1.length;i++){
			if( ! isEmpty(tmp1[i]) ){
				tmp2[trimSize++] = tmp1[i];
			}
		}
		String rtn[] = new String[trimSize];
		for(int i=0;i<trimSize;i++){
			rtn[i] = tmp2[i];
		}
		//結果を応答
		return rtn;
	}

}
