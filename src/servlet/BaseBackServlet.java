package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import util.Page;

//后台的servlet都会继承这个servlet
public abstract class BaseBackServlet extends HttpServlet {
    private static final String CLIENT_REDIRECT_PREFIX = "@";  //客户端跳转,默认是服务端跳转
    private static final String RESPONSE_STRING_PREFIX = "%";


    @Override
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
        	

            // 通过反射，调用对应的方法
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class,Page.class);
            String redirect = m.invoke(this, request, response,page).toString();

            // 根据方法的返回值，进行相应客户端或服务端跳转
            if (redirect.startsWith(CLIENT_REDIRECT_PREFIX)) {
                response.sendRedirect(redirect.substring(1));
            } else if (redirect.startsWith(RESPONSE_STRING_PREFIX)) {
                response.getWriter().print(redirect.substring(1));
            } else {
                request.getRequestDispatcher(redirect).forward(request, response);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } 
    }


    }
