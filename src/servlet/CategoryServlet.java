package servlet;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

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
		Map<String,String> params = new HashMap<>();
		FileItem item = this.parseUpload(request, response, params);
		
		Category category = new Category();
		category.setId(Integer.parseInt(params.get("id")));
		category.setName(params.get("name"));
		saveAsPhoto(request,item,Integer.parseInt(params.get("id")));
		categoryDao.update(category);
		
		return "@admin_category_list";
	}

	public String delete(HttpServletRequest request, HttpServletResponse response,Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		categoryDao.delete(id);
		
		return "@admin_category_list";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response,Page page){
		Map<String,String> params = new HashMap<>();
		FileItem item = this.parseUpload(request, response, params);
	
		Category category = new Category();
		category.setName(params.get("name"));
		categoryDao.add(category);		
		saveAsPhoto(request,item,category.getId());  //通过加入数据库返回了id
		
		return "@admin_category_list";
	}
	
	
	public void saveAsPhoto(HttpServletRequest request, FileItem item, int categoryId){
		String photoFolder =request.getServletContext().getRealPath("/img/category");
		String filename = categoryId + ".jpg";
		
		System.out.println("ooo" + photoFolder + "   " + filename);
		File file = new File(photoFolder,filename);
		file.getParentFile().mkdirs();
		
		try {
			item.write(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
