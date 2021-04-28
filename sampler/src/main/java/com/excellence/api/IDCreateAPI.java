package com.excellence.api;

import java.util.Random;

public class IDCreateAPI {

	private String IDS[] = {
			"Z0","Y1","X2","W3","V4","U5","T6","S7","R8","Q9",
			"PA","OB","NC","MD","LE","KF","JG","IH","HI","GJ",
			"FK","EL","DM","CN","AO","9P","8Q","7R","6S","5T",
			"4U","3V","2W","1X","0Y","1Z","20","31","42","53",
			"64","75","86","97","A8","B9","CA","DB","EC","FD",
			"GE","HF","IG","JH","KI","LJ","MK","NL","OM","PN",
			"QO","RP","SQ","TR","US","VT","WU","XV","YW","ZX",
			"YY","XZ","W0","V1","U2","T3","S4","R5","Q6","P7",
			"O8","N9","MA","LB","KC","JD","IE","EF","HG","DH",
			"CI","BJ","AK","9L","8M","7N","6O","5P","4Q","3R"};

	/**
	 * デフォルト文字列配列を使用するコンストラクタ
	 */
	public IDCreateAPI(){

	}

	/**
	 * ランダム文字配列指定コンストラクタ
	 * @param ids
	 */
	public IDCreateAPI(String ids[]){
		this.IDS = ids;
	}

	/**
	 * ランダム文字列配列の変更処理
	 * @param ids
	 */
	public void setIDS(String ids[]){
		this.IDS = ids;
	}

	/**
	 * ８ケタコード生成
	 * ※ランダム値を利用しないため、一意となる
	 * @return
	 */
	public String createStringCode8(){
		DateAPI da = new DateAPI();

		Random rnd = new Random();
		int index = rnd.nextInt(IDS.length);

		String daytime = da.getToday("yyyyMMddHHmmss");

		String id = "";
		for(int i=0;i<14;i+=2){
			String parts = daytime.substring(i,i+2);
			int part = Integer.parseInt(parts);
			id = id + IDS[part];
		}

		id = id + IDS[index];

		return id;
	}

	/**
	 * ランダム値を利用した８ケタコード生成
	 * ※重複の可能性があり
	 * @return
	 */
	public String createStringCode8Random(){
		DateAPI da = new DateAPI();

		Random rnd = new Random();
		int index = rnd.nextInt(IDS.length);

		String daytime = da.getToday("yyyyMMddHHmmss");

		String id = "";
		for(int i=0;i<14;i+=2){
			String parts = daytime.substring(i,i+2);
			int part = Integer.parseInt(parts);
			part = (part + index) % IDS.length;
			id = id + IDS[part];
		}

		id = id + IDS[index];

		return id;
	}

	/**
	 * ランダム値を利用した指定ケタコード生成
	 * ※重複の可能性があり
	 * @return
	 */
	public String createStringCode(int len){

		Random rnd = new Random();

		String id = "";
		for(int i=0;i<len;i++){
			int index = rnd.nextInt(IDS.length);
			id = id + IDS[index];
		}

		return id;
	}

}
