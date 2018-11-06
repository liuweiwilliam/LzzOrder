package com.lzz.order.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.lzz.order.utils.LzzProperties;

public class LzzGotoLoginPage {
	public static void go(HttpServletResponse response) throws IOException{
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<script>");
		pw.println("window.open('/" + LzzProperties.getPROJECT_NAME() + 
				"/" + LzzProperties.getLOGIN_URL() + "','_top')");
		pw.println("</script>");
		pw.println("</html>");
		pw.flush();
	}
}
