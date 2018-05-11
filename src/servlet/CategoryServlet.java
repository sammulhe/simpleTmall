package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import pojo.Category;
import util.Page;
//关于category的增删改查servlet

@SuppressWarnings("serial")
public class CategoryServlet extends BaseBackServlet{

	private CategoryDao categoryDao = new CategoryDao();
	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){

		List<Category> categorys = categoryDao.list(page.getStart(), page.getCount());
		page.setTotal(categoryDao.getTotal());
		
		request.setAttribute("categorys", categorys);
		request.setAttribute("page", page);
		
		return "admin/listCategory.jsp"; //服务端跳转
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		Category category = categoryDao.getOne(id);
		request.setAttribute("category", category);
		
		return "admin/editCategory.jsp";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response,Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		categoryDao.update(category);
		
		return "@admin_category_list";
	}

	public String delete(HttpServletRequest request, HttpServletResponse response,Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		categoryDao.delete(id);
		
		return "@admin_category_list";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response,Page page){
		String name = request.getParameter("name");
	
		Category category = new Category();
		category.setName(name);
		categoryDao.add(category);
		
		return "@admin_category_list";
	}
}
