package servlet;



import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import dao.ProductDao;
import dao.ProductImageDao;
import pojo.Product;
import pojo.ProductImage;
import util.Page;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductImageServlet extends BaseBackServlet {

	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		
		Product product = new Product();
		List<ProductImage> productSingleImages = new ArrayList<>();
		List<ProductImage> productDetailImages = new ArrayList<>();
		
		ProductDao productDao = new ProductDao();
		ProductImageDao productImageDao = new ProductImageDao();
		product = productDao.getOne(pid);
		productSingleImages = productImageDao.listByType(pid, "Single");
		productDetailImages = productImageDao.listByType(pid, "Detail");
		
		request.setAttribute("product", product);
		request.setAttribute("productSingleImages", productSingleImages);
		request.setAttribute("productDetailImages", productDetailImages);
		
		return "admin/listProductImage.jsp";
	}
   
	
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) throws Exception{
		FileItem fileItem = null;
		Map<String,String> params = new HashMap<>();		
		
		fileItem = parseUpload(request, response, params);
		
		int pid = Integer.parseInt(params.get("pid"));
		String type = params.get("type");
		
		ProductImage productImage = new ProductImage();
		productImage.setPid(pid);
		productImage.setType(type);
		ProductImageDao productImageDao = new ProductImageDao();
		productImageDao.add(productImage);
		
		saveAsPhoto(request, fileItem, productImage);
		
		return "@admin_productImage_list?pid="+pid+"";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page){
		int piid = Integer.parseInt(request.getParameter("id"));
		
		ProductImageDao productImageDao = new ProductImageDao();
		int pid = productImageDao.getOne(piid).getPid();
		productImageDao.delete(piid);
		
		return "@admin_productImage_list?pid="+pid+"";
	}
	
	
	public void saveAsPhoto(HttpServletRequest request, FileItem item, ProductImage productImage){
		
		String originFileName = item.getName(); //获取上传文件的完整路径名
		String suffix = originFileName.substring(originFileName.lastIndexOf(".")); //获取文件后缀	
		String fileName = productImage.getId() + suffix;//以productImage的id作为图片命名
		//String photoFolder = request.getServletContext().getRealPath("/img/category");  因为项目已部署到tomcat，会把文件保存到tomcat下，而不是在项目中
		//为了在eclipse方便开发，直接存在项目中，之后发布到服务器还是要改会上面的语句，保存到服务器端
		String fileFolder = "D:\\workspace\\simpleTmall\\WebContent\\img\\product"+productImage.getType();

        File file = new File(fileFolder, fileName);
        file.getParentFile().mkdirs();
          
		try {
			item.write(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
