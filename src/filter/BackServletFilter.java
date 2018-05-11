package filter;


import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {
    private static String SEPARATOR = "_";
    private static String SERVLET_PREFIX = "/admin";
    private static String SERVLET_SUFFIX = "Servlet";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取/simple_tmall
        String contextPath = request.getServletContext().getContextPath();
        //获取/simple_tmall/admin_category_list
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);//获取/admin_category_list

        if (uri.startsWith(SERVLET_PREFIX)) {
        	//获取categoryServlet
            String servletPath = StringUtils.substringBetween(uri, SEPARATOR, SEPARATOR) + SERVLET_SUFFIX;
            //获取list
            String method = StringUtils.substringAfterLast(uri, SEPARATOR);

            request.setAttribute("method", method);
            servletRequest.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
