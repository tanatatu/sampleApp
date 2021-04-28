package com.excellence.dqube.base;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
//import org.xml.sax.ErrorHandler;
//import org.xml.sax.SAXException;
//import org.xml.sax.SAXParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * XML Parser
 * @author S.Yoshizawa
 * @category Utility
 * @version 1.0
 * @since 1.0
 *
 */
public class XMLParserAPI {


	/**
	 * Get Document
	 * @param xmlFile
	 * @return
	 */
	public static Document parseXml4Doc(File xmlFile){

		try{
			DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
			documentbuilderfactory.setIgnoringComments(true); //コメントを無視する
			documentbuilderfactory.setNamespaceAware(true); //名前空間を認識する
			documentbuilderfactory.setXIncludeAware(true); //XIcludeを認識する
			documentbuilderfactory.setValidating(false); //妥当性検証をおこなう
			documentbuilderfactory.setIgnoringElementContentWhitespace(false); //無視できる空白を無視する
			DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();

			Document doc = documentbuilder.parse(xmlFile);
			return doc;

		}catch(Exception e){return null;}
	}

	/**
	 * Get Xml use Model (elements set)
	 * @param xmlFile
	 * @return
	 */
	public static IModel parseXml4Model(File xmlFile,String iKeyTag,String elements[]){

		try{
			DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
			documentbuilderfactory.setIgnoringComments(true); //コメントを無視する
			documentbuilderfactory.setNamespaceAware(true); //名前空間を認識する
			documentbuilderfactory.setXIncludeAware(true); //XIcludeを認識する
			documentbuilderfactory.setValidating(false); //妥当性検証をおこなう
			documentbuilderfactory.setIgnoringElementContentWhitespace(false); //無視できる空白を無視する
			DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();

			Document doc = documentbuilder.parse(xmlFile);

			// ルート要素を取得
			Element root = doc.getDocumentElement();

			// page要素のリストを取得
			NodeList list = root.getElementsByTagName(iKeyTag);

			IModel xmlData = new PModel();

			for (int i=0; i < list.getLength() ; i++) {

				// 要素を取得
				Element element = (Element)list.item(i);
				// id属性の値を取得
				String id = element.getAttribute("id");

				IModel elementAttribute = new PModel();

				elementAttribute.setData("id",id);

				for (int j=0;j<elements.length;j++){

					NodeList valList = element.getElementsByTagName(elements[j]);

					// 要素を取得
					Element valElement = (Element)valList.item(0);

					if (valElement != null){
						// 要素の最初の子ノード（テキストノード）の値を取得
						String val = valElement.getFirstChild().getNodeValue();

						elementAttribute.setData(elements[j],val);
				    }


				}
				xmlData.setData(id,elementAttribute);

			}

			return xmlData;

		}catch(Exception e){return null;}
	}

	/**
	 * Get Xml use Model
	 * @param xmlFile
	 * @return
	 */
	public static IModel parseXml4Model(File xmlFile,String iKeyTag){

		try{
			DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
			documentbuilderfactory.setIgnoringComments(true); //コメントを無視する
			documentbuilderfactory.setNamespaceAware(true); //名前空間を認識する
			documentbuilderfactory.setXIncludeAware(true); //XIcludeを認識する
			documentbuilderfactory.setValidating(false); //妥当性検証をおこなう
			documentbuilderfactory.setIgnoringElementContentWhitespace(false); //無視できる空白を無視する
			DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();

			Document doc = documentbuilder.parse(xmlFile);

			// ルート要素を取得
			Element root = doc.getDocumentElement();

			// page要素のリストを取得
			NodeList list = root.getElementsByTagName(iKeyTag);

			IModel xmlData = new PModel();

			for (int i=0; i < list.getLength() ; i++) {

				// 要素を取得
				Element element = (Element)list.item(i);
				// id属性の値を取得
				String id = element.getAttribute("id");

				IModel elementAttribute = new PModel();

				elementAttribute.setData("id",id);

				//子ノードをリスト化
				NodeList nlist = element.getChildNodes();

				for(int j=1;j<nlist.getLength();j++){

					Node node = nlist.item(j);
					String elementName = node.getNodeName();

					if(!"#text".equals(elementName)){
						NodeList valList = element.getElementsByTagName(elementName);

						// 要素を取得
						Element valElement = (Element)valList.item(0);

						if (valElement != null){
							// 要素の最初の子ノード（テキストノード）の値を取得
							String val = valElement.getFirstChild().getNodeValue();

							elementAttribute.setData(elementName,val);
					    }

					}
				}
				xmlData.setData(id,elementAttribute);

			}

			return xmlData;

		}catch(Exception e){return null;}
	}
}
