package com.lzz.order.action.media;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzFileMgr;
import com.lzz.order.mgr.LzzIDMgr;
import com.lzz.order.utils.LzzDateUtil;
import com.lzz.order.utils.LzzFileUtil;
import com.lzz.order.utils.LzzProperties;

public class LzzWechatImgUpload  implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("request url : " + request.getRequestURI() + ",request hash : " + request.hashCode());
    	boolean isError = false;
    	String returnMsg = "文件上传成功";
    	JSONObject rslt = new JSONObject();
    	
    	Transaction ts = LzzFactory.currentSession().beginTransaction();
    	try {
            request.setCharacterEncoding("UTF-8"); 
            //1、创建一个DiskFileItemFactory工厂  
            DiskFileItemFactory factory = new DiskFileItemFactory();  
            factory.setSizeThreshold(1024 * 1024);
            String save_path = request.getServletContext().getRealPath(LzzProperties.getFILE_UPLOAD_PATH());
            String date = File.separator + LzzDateUtil.getNow("d") + File.separator;
            save_path += date;
            File saveFile=new File(save_path);
            if (!saveFile.exists()) {
            	saveFile.mkdir();
            }
            
            factory.setRepository(saveFile);
            //2、创建一个文件上传解析器  
            ServletFileUpload upload = new ServletFileUpload(factory);

            //解决上传文件名的中文乱码  
            upload.setHeaderEncoding("UTF-8");   
            // 1. 得到 FileItem 的集合 items  
            List<FileItem> items = new ArrayList<FileItem>();
            try{
            	items = upload.parseRequest(request);
            }catch(Exception e){
            	e.printStackTrace();
            	isError = true;
            	returnMsg = "文件上传失败！";
            }
            // 2. 遍历 items:  
            for (FileItem item : items) {  
                String upload_name = item.getFieldName();
                String file_name = item.getName();
                String file_id = LzzIDMgr.self().getID();
                String name_postfix = file_name.substring(file_name.lastIndexOf("."));
                String file_path = save_path + file_id + name_postfix;
                File storeFile = new File(file_path);
                item.write(storeFile);
                
                int index = file_path.indexOf(LzzProperties.getPROJECT_NAME());
                String path_tmp = file_path.substring(index + LzzProperties.getPROJECT_NAME().length() + 1);
                String url_path = LzzProperties.getProjectUrl() + path_tmp;
                LzzFileMgr.addFileInfo(file_id, upload_name, file_path, path_tmp);
                
                rslt.put("id", file_id);
                rslt.put("filename", upload_name);
                rslt.put("path", url_path);
                rslt.put("type", LzzFileUtil.self().typeOf(upload_name));
            }
            
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
            returnMsg = "文件上传失败！";
            ts.rollback();
            LzzCacheMgr.reloadAllCache();
        } finally{
        	LzzFactory.closeSession();
        }
        PrintWriter out = response.getWriter();  
        
        rslt.put("iserror", isError);
        rslt.put("returnMsg", returnMsg);
        out.append(rslt.toString());
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}