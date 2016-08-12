package DependenceRecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import SpatialRelationship.Word;

public class CorpusPretreatment {
	 public List<Word> getWordProperty(String sentence) throws IOException {

		 deleteFile("out_xml.xml");
		 
       String api_key = "U1H0S1Z1CkcUtrLouJvyHVNSOWkY9ycmAVahcduW";
       String pattern = "all";
       String format  = "xml";
       String text    = sentence;
       text = URLEncoder.encode(text, "utf-8");

       URL url     = new URL("http://api.ltp-cloud.com/analysis/?"
                             + "api_key=" + api_key + "&"
                             + "text="    + text    + "&"
                             + "format="  + format  + "&"
                             + "pattern=" + pattern);
       URLConnection conn = url.openConnection();
       conn.connect();//����

       BufferedReader innet = new BufferedReader(new InputStreamReader(
                               conn.getInputStream(),
                               "utf-8"));
       String line;
       while ((line = innet.readLine())!= null) {
         Write(line,"out_xml.xml");
       }
       innet.close();
       List<Word> DependenceList=getWordPropertyList("out_xml.xml");
//       Iterator<Word> it=DependenceList.iterator();
//       while(it.hasNext())
//       {
//       	Word word=(Word) it.next();
//       	System.out.println(word.getId()+" "+word.getCont()+" "+word.getPos()+" "+word.getNe()+" "+word.getParent()
//       	+" "+word.getRelate()+" "+word.getSemparent()+" "+word.getSemrelate());
//       }
       return DependenceList;
   }
   public static void Write(String cc,String str)
	  {
		 String path = str;
		 try 
		 {		 
			 FileOutputStream out = new FileOutputStream(path, true);
		     StringBuffer sb = new StringBuffer();
		     sb.append(cc+"\n");
		     out.write(sb.toString().getBytes("utf-8"));
		     out.close();	 
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	  }
   
   public static List<Word> getWordPropertyList(String filename)
   {
   	Element element=null;
   	File f=new File(filename);
   	
   	DocumentBuilder db = null;
   	DocumentBuilderFactory dbf = null;
   	List<Word> DependenceList=new ArrayList<Word>();
   	try {
   	   // ����documentBuilderFactory����
   	   dbf = DocumentBuilderFactory.newInstance();
   	   // ����db������documentBuilderFatory�����÷���documentBuildr����
   	   db = dbf.newDocumentBuilder();
   	   // �õ�һ��DOM�����ظ�document����
   	   Document dt = db.parse(f);
   	   // �õ�һ��elment��Ԫ��
   	   element = dt.getDocumentElement();
   	   // ��ø��ڵ�
   	   //System.out.println("��Ԫ�أ�" + element.getNodeName());
   	   // ��ø�Ԫ���µ��ӽڵ�
   	   NodeList childNodes = element.getChildNodes();
   	   for(int i=0;i<childNodes.getLength();i++)
   	   {
   		// ���ÿ����Ӧλ��i�Ľ��
   		    Node node1 = childNodes.item(i);
   		    if(node1.getNodeName().equals("doc"))
   		    {
   		    	  NodeList docChilds=node1.getChildNodes();
   		    	  for(int j=0;j<docChilds.getLength();j++)
   		    	  {
   		    		  Node node2=docChilds.item(j);
   		    		  if(node2.getNodeName().equals("para"))
   		    		  {
   		    			  NodeList paraChilds=node2.getChildNodes();
   		    			  for(int k=0;k<paraChilds.getLength();k++)
   		    			  {
   		    				  Node node3=paraChilds.item(k);
   		    				  if(node3.getNodeName().equals("sent"))
   		    				  {
   		    					  NodeList sentChilds=node3.getChildNodes();
   		    					  for(int q=0;q<sentChilds.getLength();q++)
   		    					  {
   		    						  Node node4=sentChilds.item(q);
   		    						  if(node4.getNodeName().equals("word"))
   		    						  {
   		    							  String id=node4.getAttributes().getNamedItem("id").getNodeValue();
   		    							  String cont=node4.getAttributes().getNamedItem("cont").getNodeValue();
   		    							  String pos=node4.getAttributes().getNamedItem("pos").getNodeValue();
   		    							  String ne=node4.getAttributes().getNamedItem("ne").getNodeValue();
   		    							  String parent=node4.getAttributes().getNamedItem("parent").getNodeValue();
   		    							  String relate=node4.getAttributes().getNamedItem("relate").getNodeValue();
   		    							  String semparent=node4.getAttributes().getNamedItem("semparent").getNodeValue();
   		    							  String semrelate=node4.getAttributes().getNamedItem("semrelate").getNodeValue();
   		    							  Word word=new Word(id,cont,pos,ne,parent,relate,semparent,semrelate);
   		    							  DependenceList.add(word);
   		    						  }
   		    					  }
   		    				  }
   		    			  }
   		    		  }
   		    	  }
   		    }
   	   }
   }catch (Exception e) {
	   e.printStackTrace();
	   }
   return DependenceList;
  }
   
   /**
    * ɾ�������ļ�
    * @param sPath
    * @return
    */
   public static boolean deleteFile(String sPath) {
       Boolean flag = false;
       File file = new File(sPath);
       // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
       if (file.isFile() && file.exists()) {
           file.delete();
           flag = true;
       }
       return flag;
   }
}
