package com.lzz.order.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class LzzXmlUtil {
	/**
     * 将Map转成xml结构
     * @param param
     * @return
     */
    public static String mapToXML(Map<String,String> param){  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        for (Map.Entry<String,String> entry : param.entrySet()) {   
               sb.append("<"+ entry.getKey() +">");  
               sb.append(entry.getValue());  
               sb.append("</"+ entry.getKey() +">");  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    }
    
    /**
	 * XML解析成Map
	 * @param strxml
	 * @return
	 * @throws Exception
	 */
    public static Map<String, String> xmlToMap(String strxml) throws Exception {    
          strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");    
          if(null == strxml || "".equals(strxml)) {
              return null;    
          }    
          
          Map<String,String> m = new HashMap<String,String>();     
          InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));    
          SAXBuilder builder = new SAXBuilder();    
          Document doc = builder.build(in);
          Element root = doc.getRootElement(); 
          List list = root.getChildren();  
          Iterator it = list.iterator();    
          while(it.hasNext()) {    
              Element e = (Element) it.next();    
              String k = e.getName();    
              String v = "";    
              List children = e.getChildren();    
              if(children.isEmpty()) {    
                  v = e.getTextNormalize();    
              } else {    
                  v = getChildrenText(children);
              }    
                  
              m.put(k, v);    
          }    
              
          //关闭流    
          in.close();     
          return m;    
    }
    
    private static String getChildrenText(List children) {    
        StringBuffer sb = new StringBuffer();    
        if(!children.isEmpty()) {    
            Iterator it = children.iterator();    
            while(it.hasNext()) {    
                Element e = (Element) it.next();    
                String name = e.getName();    
                String value = e.getTextNormalize();    
                List list = e.getChildren();    
                sb.append("<" + name + ">");    
                if(!list.isEmpty()) {    
                    sb.append(getChildrenText(list));    
                }    
                sb.append(value);    
                sb.append("</" + name + ">");    
            }    
        }     
        return sb.toString();    
    }
}
