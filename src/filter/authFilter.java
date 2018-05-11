package filter;


import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class authFilter implements Filter{

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
		
		/*
		String uri = request.getRequestURI();
		if(uri.contains("login") || uri.contains("index") || uri.contains("Product")){
			chain.doFilter(request, response);
			return;
		}
		
		String username = (String) request.getSession().getAttribute("username");
		if(username == null){
			response.sendRedirect("login.jsp");
			return;
		}
		*/
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

