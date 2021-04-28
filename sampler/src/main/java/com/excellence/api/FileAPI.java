package com.excellence.api;
/**
 * @author S.Yoshizawa
 * @category Excellence Framework 1.0
 * @version 1.0
 * File Utility Class
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileAPI {

    private String targetFile = "";   //対象ファイル
    private String eData = "";
	private boolean eFlg = false;
	private String encode = "utf-8";

    /**
     * コンストラクタ
     */
    public FileAPI (){
    }

    /**
     * コンストラクタ
     */
    public FileAPI (String filename){
      this.targetFile = filename;
    }

    /**
     * ファイルのセッター
     */
    public void setFile(String filename) {
      this.targetFile = filename;
    }

    /**
     * エンコードのセッター
     * @param encode
     */
    public void setEncode(String encode){
    	this.encode = encode;
    }

    /**
     * エラー情報の取得処理
     */
    public String getError(){
      return this.eData;
    }

    /**
     * エラー判別
     * @return
     */
    public boolean isError(){
      return this.eFlg;
    }

    /**
     * ファイル書き込み
     */
    public void fileWriter(String msg, boolean addFlg){
	  eFlg = false;
      try{
        PrintWriter p_writer  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.targetFile,addFlg),this.encode)));
        p_writer .write(msg);
        p_writer .close();
      }catch(Exception e){
      	eFlg = true;
        eData = eData + "ファイルの書き込みに失敗しました。"  + "<br>" + e + "<br>";
      }
    }

    /**
     * 読み込み処理
     */
    public String fileReader(){
      String data = ""; // 取得データ
	  eFlg = false;
      try{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.targetFile),this.encode));

        String line; // 読み込み行
        while((line = br.readLine()) != null){
          data = data + line + "\n";
        }

        br.close();
      }catch(Exception e){
      	eFlg = true;
        eData = eData + "ファイルの読み込みに失敗しました。"  + "<br>" + e + "<br>";
        return "";
      }

      return data;
    }

    /**
     * ファイル書き込み
     */
    public void fileWriterLegacy(String msg, boolean addFlg){
	  eFlg = false;
      try{
        FileWriter fout = new FileWriter(this.targetFile,addFlg);
        fout.write(msg);
        fout.close();
      }catch(Exception e){
      	eFlg = true;
        eData = eData + "ファイルの書き込みに失敗しました。"  + "<br>" + e + "<br>";
      }
    }

    /**
     * 読み込み処理
     */
    public String fileReaderLegacy(){
      String data = ""; // 取得データ
	  eFlg = false;
      try{
        FileReader fr = new FileReader(this.targetFile);

		BufferedReader br = new BufferedReader(fr);

        String line; // 読み込み行
        while((line = br.readLine()) != null){
          data = data + line + "\n";
        }

        br.close();
      }catch(Exception e){
      	eFlg = true;
        eData = eData + "ファイルの読み込みに失敗しました。"  + "<br>" + e + "<br>";
        return "";
      }

      return data;
    }

}
