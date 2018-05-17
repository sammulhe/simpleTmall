package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import dao.ProductDao;
import dao.PropertyValueDao;
import pojo.Category;
import pojo.Product;
import pojo.ProductImage;
import pojo.PropertyValue;
import util.Page;

//product相关的servlet
public class ProductServlet extends BaseBackServlet{
	
	private ProductDao productDao = new ProductDao();
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		List<Product> products = productDao.list(cid, page.getStart(), page.getCount());
		page.setTotal(productDao.getTotal(cid)); //获取total，得到last
		CategoryDao categoryDao = new CategoryDao();
		Category category = categoryDao.getOne(cid);

		request.setAttribute("products", products);
		request.setAttribute("category", category);
		request.setAttribute("page", page);
		
		return "admin/listProduct.jsp";
 	}

	public String edit(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		Product product = productDao.getOne(id);
		request.setAttribute("product", product);
		
		return "admin/editProduct.jsp";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setSubTitle(subTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(stock);
		product.setCid(cid);
		
		productDao.update(product);
		
		return "@admin_product_list?cid="+cid+"";		
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		
		int cid = productDao.getOne(id).getCid();
		productDao.delete(id);
		
		return "@admin_product_list?cid="+cid+"";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response, Page page){
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		Product product = new Product();
		product.setName(name);
		product.setSubTitle(subTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(stock);
		product.setCid(cid);
		
		productDao.add(product);
		
		return "@admin_product_list?cid="+cid+"";
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("id"));//获取product的id，即是property_value的pid
		
		PropertyValueDao pvd = new PropertyValueDao();
		List<PropertyValue> propertyValues = pvd.list(pid);
		Product product = productDao.getOne(pid);
		
		request.setAttribute("propertyValues", propertyValues);
		request.setAttribute("product", product);
		
		return "admin/editProductValue.jsp";
	}
	
	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page){
		int pvid = Integer.parseInt(request.getParameter("pvid"));//获取property_value的id
		String value = request.getParameter("value"); //获取属性值
		
		PropertyValue propertyValue = new PropertyValue();
		propertyValue.setId(pvid);
		propertyValue.setValue(value);
		
		PropertyValueDao propertyValueDao = new PropertyValueDao();
		propertyValueDao.update(propertyValue);
		
		return "%success";
	}
	
}
