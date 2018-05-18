package servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import dao.ProductDao;
import dao.PropertyValueDao;
import dao.ReviewDao;
import pojo.Category;
import pojo.Product;
import pojo.PropertyValue;
import pojo.Review;
import util.Page;

public class ForeServlet extends BaseForeServlet{
	
	private static CategoryDao categoryDao = new CategoryDao();
	private static ProductDao productDao = new ProductDao();
	private static PropertyValueDao propertyValueDao = new PropertyValueDao();
	private static ReviewDao reviewDao = new ReviewDao();
	
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
	
	
	//显示每个商品的详细信息
	public String product(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		
		Product product = new Product();
		List<PropertyValue> propertyValues = new ArrayList<>(); //获取产品的属性值
		List<Review> reviews = new ArrayList<>();  //获取评价
		
		product = productDao.getOne(pid);
		propertyValues = propertyValueDao.list(pid);
		reviews = reviewDao.list(pid);
		
		request.setAttribute("product", product);
		request.setAttribute("propertyValues", propertyValues);
		request.setAttribute("reviews", reviews);
		
		return "product.jsp";
	}

}
