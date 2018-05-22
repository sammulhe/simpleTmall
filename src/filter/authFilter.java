package filter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import pojo.Administrator;
import pojo.User;



public class authFilter implements Filter{

	private static String PREFIX = "/fore";
    private static String SERVLET_PATH = "/foreServlet";
    private static String BACK ="/admin";  //后台管理员验证
	 
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		request.setCharacterEncoding("UTF-8"); //同时把所有请求的字符编码设置为UTF-8
		
		
		

	    String[] noNeedAuthPage = new String[]{
	                "home",
	                "checkLogin",
	                "register",
	                "loginAjax",
	                "login",
	                "product",
	                "category",
	                "search"
	    };
	    
	    String contextPath = request.getServletContext().getContextPath();
	    String uri = request.getRequestURI();
	    uri = StringUtils.remove(uri, contextPath);
	    if (uri.startsWith(PREFIX) && !uri.startsWith(SERVLET_PATH)) {
	    	String method = StringUtils.substringAfterLast(uri, PREFIX);
	        if (!Arrays.asList(noNeedAuthPage).contains(method)) {
	        	User user = (User) request.getSession().getAttribute("user");
	            if (null == user) {
	            	response.sendRedirect("login.jsp");
	                return;
	            }
	        }
	    }
	    
	    //用于后台的验证	    
	    if(uri.startsWith(BACK)){
	    	Administrator admin = (Administrator) request.getSession().getAttribute("admin");
	    	if(null == admin){
	    		response.sendRedirect("login_admin.jsp");
	    		return ;
	    	}
	    }
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

