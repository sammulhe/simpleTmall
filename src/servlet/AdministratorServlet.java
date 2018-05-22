package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdministratorDao;
import pojo.Administrator;

public class AdministratorServlet extends HttpServlet{

	private static AdministratorDao adminDao = new AdministratorDao();
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		Administrator admin = adminDao.find(name, password);
		if(null != admin){
			request.getSession().setAttribute("admin", admin);
			response.sendRedirect("admin_category_list");
			return;
		}
		
		request.setAttribute("msg", "账号密码错误");
		request.getRequestDispatcher("login_admin.jsp").forward(request, response);
	
	}
}
