package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import pojo.User;
import util.Page;

public class UserServlet extends BaseBackServlet{
	
	private UserDao userDao = new UserDao();
	
	public String list(HttpServletRequest request, HttpServletResponse response,Page page){
		
		page.setTotal(userDao.getTotal());  //获取total，计算last
		
		List<User> users = userDao.list(page.getStart(), page.getCount());
		request.setAttribute("users", users);
		request.setAttribute("page", page);
		
		return "admin/listUser.jsp";
	}

}
