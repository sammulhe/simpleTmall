package servlet;


import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ProductDao;
import dao.PropertyDao;
import dao.PropertyValueDao;
import dao.ReviewDao;
import dao.UserDao;
import pojo.OrderItem;
import pojo.ProductImage;
import util.Page;

import java.lang.reflect.Method;

public class BaseForeServlet extends HttpServlet {
    private static final String CLIENT_REDIRECT_PREFIX = "@";
    private static final String RESPONSE_STRING_PREFIX = "%";


    public void service(HttpServletRequest request, HttpServletResponse response) {
    	  try {
          	//因为基本都需要用到分页查询，所以直接写到了这个公共部分，不好的就是有些不需要该参数的，也得传递该参数
          	Page page = new Page();
          	int start = 0;
          	int count = 5;
          	try{
          		start = Integer.parseInt(request.getParameter("start"));
          		//count = Integer.parseInt(request.getParameter("count"));
          	}catch(Exception e){
          		start = 0;
          	}
          	page.setStart(start);
          	page.setCount(count);
          	

            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();

            // 根据方法的返回值，进行相应客户端或服务端跳转
            if (redirect.startsWith(CLIENT_REDIRECT_PREFIX)) {
                response.sendRedirect(redirect.substring(1));
            } else if (redirect.startsWith(RESPONSE_STRING_PREFIX)) {
                response.getWriter().print(redirect.substring(1));
            } else {
                request.getRequestDispatcher(redirect).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
