package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import dao.PropertyDao;
import pojo.Category;
import pojo.Property;
import util.Page;

//Property相关的servlet
public class PropertyServlet extends BaseBackServlet{
	
	private PropertyDao propertyDao = new PropertyDao();
	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		List<Property> properties = propertyDao.list(cid, page.getStart(), page.getCount());
		CategoryDao categoryDao = new CategoryDao();
		Category category = categoryDao.getOne(cid);
		page.setTotal(propertyDao.getTotal(cid));  //获取总数
		
		request.setAttribute("properties", properties);
		request.setAttribute("page", page);
		request.setAttribute("category", category);
		
		return "admin/listProperty.jsp";
	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		Property property = propertyDao.getOne(id);
		request.setAttribute("property", property);
		
		return "admin/editProperty.jsp";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name = request.getParameter("name");
		
		Property property = new Property();
		property.setId(id);
		property.setCid(cid);
		property.setName(name);
		propertyDao.update(property);
		
		return "@admin_property_list?cid="+cid+"";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		int cid = propertyDao.getOne(id).getCid();
		propertyDao.delete(id);
		
		return "@admin_property_list?cid="+cid+"";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name= request.getParameter("name");
		
		Property property = new Property();
		property.setCid(cid);
		property.setName(name);
		propertyDao.add(property);
		
		return "@admin_property_list?cid="+cid+"";
	}
}
