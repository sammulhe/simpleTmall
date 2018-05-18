package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ForeServletFilter implements Filter{

    private static String PREFIX = "/fore";
    private static String SERVLET_PATH = "/foreServlet";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath", contextPath);

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if (uri.startsWith(PREFIX) && !uri.startsWith(SERVLET_PATH)) {
            String method = StringUtils.substringAfterLast(uri, PREFIX);
            request.setAttribute("method", method);
            servletRequest.getRequestDispatcher("/foreServlet").forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	

}
