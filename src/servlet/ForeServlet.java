package servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import dao.ProductDao;
import pojo.Category;
import pojo.Product;
import util.Page;

public class ForeServlet extends BaseForeServlet{
	
	private static CategoryDao categoryDao = new CategoryDao();
	private static ProductDao productDao = new ProductDao();
	
	public String home(HttpServletRequest request, HttpServletResponse response, Page page){
		
		//各种需要获取的值
		List<Category> categorys = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<List<Product>>  productsByRow = new ArrayList<>();
		
		
		//查询所有的种类
		List<Category> categorysTmp = categoryDao.list();
		for(Category category : categorysTmp){
		    products = productDao.list(category.getId());
		    productsByRow = productDao.getProductsByRow(products);
			category.setProducts(products);
		    category.setProductsByRow(productsByRow);

		    categorys.add(category);
		}
		
		request.setAttribute("categorys", categorys);
		
		return "home.jsp";
	}

}
