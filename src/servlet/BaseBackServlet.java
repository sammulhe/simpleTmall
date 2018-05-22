package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
   
    
   public FileItem parseUpload(HttpServletRequest request, HttpServletResponse response, Map<String,String> params){
	   FileItem reItem = null;
	   DiskFileItemFactory factory = new DiskFileItemFactory();
       ServletFileUpload upload = new ServletFileUpload(factory);
       // 设置上传文件的大小限制为1M
       factory.setSizeThreshold(1024 * 1024);
       
       try {
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		 while (iter.hasNext()) {
             FileItem item = (FileItem) iter.next();
             if (!item.isFormField()) {
            	 reItem = item;
             } else {
            	 String paramName = item.getFieldName();
                 String paramValue = item.getString();
                 paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                 params.put(paramName, paramValue);    

             }
         }
          
	   } catch (FileUploadException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

       return reItem;
   }

}
