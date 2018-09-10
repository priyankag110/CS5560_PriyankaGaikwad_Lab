package pubmedabstract;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReaderForAbstracts4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			for(int i=1;i<2;i++)
			{
			 File file = new File("new_data_alzimers//abstracts//" +(i)+ ".xml");
				PrintWriter writer = new PrintWriter("new_data_alzimers/abstracts/" + (i) + ".txt", "UTF-8");//ADDED
		         if(file.exists())
			  {
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			  NodeList nodeLst = doc.getElementsByTagName("PubmedArticleSet");

			  for (int s = 0; s < nodeLst.getLength(); s++) {

			    Node fstNode = nodeLst.item(s);
			  //  System.out.println(fstNode.getNodeValue()); 
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			  
			           Element fstElmnt = (Element) fstNode;
			        // System.out.println(fstElmnt.getNodeValue());
			      //NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("MedlineCitation");
			      //Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			      NodeList articleNodeList = fstElmnt.getElementsByTagName("BookDocument");
			      if(articleNodeList.getLength()==0){
					  articleNodeList = fstElmnt.getElementsByTagName("MedlineCitation");
				  }
			      String IDs = null;
			      for(int t=0;t<articleNodeList.getLength();t++)
			      {
			       	 Element articleElement = (Element)articleNodeList.item(t);
			      	  NodeList abstractNodeList=articleElement.getElementsByTagName("Abstract");
			      	  if (abstractNodeList.item(0).getNodeType()==Node.ELEMENT_NODE) {
			      		 Element abstractElement = (Element)abstractNodeList.item(t);
				      	  NodeList abstract2=abstractElement.getElementsByTagName("AbstractText");
				      	 for(int t1=0;t1<abstract2.getLength();t1++)
					      {
					       	 Element abstrElement = (Element) abstract2.item(t1);
					      	  NodeList fstNm = abstrElement.getChildNodes();
								String temp = (((Node) fstNm.item(0)).getNodeValue());
								String delimiter = "\\. ";
							  String[] lines = temp.split(delimiter);
							  for (String line : lines) {
								  writer.println(line + ".");

							//	  System.out.println("\n");
							  }
					      }
					}
			      }
					writer.close();
			      System.out.println("Done" + i);
			    }
			  }
			  
			}
			}
			
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
