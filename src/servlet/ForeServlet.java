package servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;

import dao.CategoryDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ProductDao;
import dao.PropertyValueDao;
import dao.ReviewDao;
import dao.UserDao;
import pojo.Category;
import pojo.Order;
import pojo.OrderItem;
import pojo.Product;
import pojo.PropertyValue;
import pojo.Review;
import pojo.User;
import util.DBUtil;
import util.DateUtil;
import util.Page;

public class ForeServlet extends BaseForeServlet{
	
	private static CategoryDao categoryDao = new CategoryDao();
	private static ProductDao productDao = new ProductDao();
	private static PropertyValueDao propertyValueDao = new PropertyValueDao();
	private static ReviewDao reviewDao = new ReviewDao();
	private static UserDao userDao = new UserDao();
	private static OrderItemDao orderItemDao = new OrderItemDao();
	private static OrderDao orderDao = new OrderDao();
	
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
	
	
	
	//用户注册
	public String register(HttpServletRequest request, HttpServletResponse response, Page page){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		userDao.add(user);
		
		return "registerSuccess.jsp";	
	}
	
	
	//检查注册名是否已存在，已存在，不允许使用
	public String checkUserName(HttpServletRequest request, HttpServletResponse response, Page page){
		String username = request.getParameter("username");
		
		boolean result = userDao.check(username);
		
		if(result != true){
			return "%true";
		}
		
		return "%false";
	}
	
	//登陆验证
	public String login(HttpServletRequest request, HttpServletResponse response, Page page){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userDao.find(username, password);
		if(user != null){
			request.getSession().setAttribute("user", user);
			
			//获取用户的相关信息
			int cartTotalItemNumber= orderItemDao.getCartTotal(user.getId());
			request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
			return "@forehome";
		}
		
		request.setAttribute("msg", "账户密码错误");
		
		return "login.jsp";
	}
	
	
	//退出登陆账号
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page){
		//request.getSession().removeAttribute("user");//只是移除了user这个属性,cartToatlItemNumber还会存在
		request.getSession().invalidate();  //把整个session都去掉了。所有属性都没有了
		return "login.jsp";
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
	
	//在商品页，点击立即购买前，检查用户是否登陆
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		
		if(user != null){
			return "%success";
		}
		
		return "%false";
	}
	
	//如果用户还没登陆，跳出了模态登陆窗口，然后验证账号密码
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userDao.find(username, password);
		if(user != null){
			request.getSession().setAttribute("user", user);
			
			//获取用户的相关信息
			int cartTotalItemNumber= orderItemDao.getCartTotal(user.getId());
			request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
			
			return "%success";
		}
		
		return "%fail";
	}
	
	
	//在商品页，点击立即购买，跳到了填写订单信息的页面
	public String buyone(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
	    User user = (User) request.getSession().getAttribute("user");
	    int uid = user.getId();
	    
	    List<OrderItem> orderItems = new ArrayList<>();
	    OrderItem orderItem = new OrderItem();
		orderItem.setPid(pid);
		orderItem.setUid(uid);
		orderItem.setNumber(num);
		orderItem.setProduct(productDao.getOne(pid)); 
		orderItems.add(orderItem);
		
		//计算订单的总金额，正常情况下要遍历orderItems，但是该情况orderItems只有一个orderItem，所以用下面直接计算即可
		float total = orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
		
		request.setAttribute("orderItems", orderItems);
		request.setAttribute("total", total);
		
		return "buy.jsp";
	}

	
	
	//点击分类，显示所有属于该分类的产品
	public String category(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		String sort = request.getParameter("sort"); //根据sort排序
		
		Category category = new Category();
		List<Product> products = new ArrayList<>();
		
		category = categoryDao.getOne(cid);
		products = productDao.list(cid);
		category.setProducts(products);
		
		//对应以不同的关键字进行排列
		if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), (p1, p2) -> p1.getCreateDate().compareTo(p2.getCreateDate()));
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), (p1, p2) -> (int) (p1.getPromotePrice() - p2.getPromotePrice()));
                    break;
                case "all":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount());
                    break;
                default:
                    break;
            }
        }
				
		
		request.setAttribute("category", category);
		
		return "category.jsp";
	}
	
	//关键字搜索，只是简单的数据库查询，大型的会用到第三方搜索引擎
	public String search(HttpServletRequest request, HttpServletResponse response, Page page){
		String keyword = request.getParameter("keyword");
		
		List<Product> products = new ArrayList<>();		
		products = productDao.searchByKeyword(keyword);
		
		request.setAttribute("products", products);
		
		return "searchResult.jsp";
	}
	
	
	//在商品页，点击加入购物车
	public String addCart(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		
		OrderItem orderItem = new OrderItem();
		orderItem.setPid(pid);
		orderItem.setUid(uid);
		orderItem.setNumber(num);
		orderItemDao.add(orderItem);

		//购物车中数量加上num数
		Integer cartTotalItemNumber = (Integer) request.getSession().getAttribute("cartTotalItemNumber");
		cartTotalItemNumber = cartTotalItemNumber + num;
		request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
				
		return "%success";
	}
	
	
	//点击购物车，跳到购物车的页面，显示已经被加入到购物车的订单项信息
	public String cart(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		
		List<OrderItem> cartOrderItems = new ArrayList<>();
		cartOrderItems = orderItemDao.getCartOrderItems(uid);
		for(OrderItem oi : cartOrderItems){
			Product product = new Product();
			product = productDao.getOne(oi.getPid());
			oi.setProduct(product);
		}
		
		request.setAttribute("cartOrderItems", cartOrderItems);
		
		return "cart.jsp";
	}
	
	
	//删去购物车中的订单项
	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page){
		int oiid = Integer.parseInt(request.getParameter("oiid"));
				
		Integer cartTotalItemNumber = (Integer) request.getSession().getAttribute("cartTotalItemNumber");
		cartTotalItemNumber = cartTotalItemNumber - orderItemDao.getOne(oiid).getNumber() ;
		cartTotalItemNumber = cartTotalItemNumber> 0? cartTotalItemNumber: 0;
		request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		orderItemDao.delete(oiid);
		
		return "%success";
	}
	
	//购物车中改变订单项的产品数量
	public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page){
		//int pid = Integer.parseInt(request.getParameter("pid"));以后用来减掉product的stock库存
		int number = Integer.parseInt(request.getParameter("number"));
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		
		OrderItem orderItem = new OrderItem();
		orderItem.setId(oiid);
		orderItem.setNumber(number);
		
		orderItemDao.update(orderItem);
		
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		int cartTotalItemNumber = orderItemDao.getCartTotal(uid);
		request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		return "%success";		
	}
	
	
	//在购物车页面，点击结算，跳到了buy.jsp，跟立即购买（buyone)跳转到填写订单信息的页面
	public String buy(HttpServletRequest request, HttpServletResponse response, Page page){
		String[] params = request.getParameterValues("oiid");  //有多个订单项id
		
		List<OrderItem> orderItems = new ArrayList<>();
		float total = 0;//计算订单的总金额
				
		for(String s : params){
			int oiid = Integer.parseInt(s);
			OrderItem orderItem = orderItemDao.getOne(oiid);
			Product product = productDao.getOne(orderItem.getPid());
			orderItem.setProduct(product);
			total = total + orderItem.getNumber() * product.getPromotePrice();
			orderItems.add(orderItem);
		}
	
		request.getSession().setAttribute("orderItems", orderItems); //在生成订单中个需要用到这些订单项，所以存在session中
		request.setAttribute("total", total);
		return "buy.jsp";
	}
	
	
	//填完订单的详细信息后，提交订单，在数据库中生成订单
	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page){
		//从网页获取的参数
		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage= request.getParameter("userMessage");
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		//自身成的参数
		Date date = new Date();
		String createDate = DateUtil.DateToString(date);
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + RandomUtils.nextInt(10000);
		String status = "waitPay";
		
		Order order = new Order();
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setUid(uid);
		order.setOrderCode(orderCode);
		order.setCreateDate(createDate);
		order.setStatus(status);
		
		orderDao.add(order);
		
		float total = 0;  //获取订单的总金额
		int oid = order.getId();
		
		//将返回的order的id号赋值到每个订单项的oid中
		@SuppressWarnings("unchecked")
		List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("orderItems");
		for(OrderItem orderItem : orderItems){
			orderItem.setOid(oid);
			orderItemDao.updateOid(orderItem);
			total = total + orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
		}
	
		request.getSession().removeAttribute("orderItems");  //移除在session中orderItems
		

	    return "@forealipay?oid="+oid+"&total="+total+"";		
	}
	
	
	//可能提交订单后没有付款，所以需要用一个中转网页，即便这次放弃了付款
	//可以在我的未付款订单中重新跳到该页面进行付款，而不是重新createOrder
	public String alipay(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		float total = Float.parseFloat(request.getParameter("total"));
		
		request.setAttribute("total", total);
		request.setAttribute("oid", oid);
		
		return "alipay.jsp";
	}
	
	
	//点击付款
	public String payed(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		float total = Float.parseFloat(request.getParameter("total"));
		Date date = new Date();
		
		Order order = orderDao.getOne(oid);
		order.setPayDate(DateUtil.DateToString(date));
		order.setStatus("waitDelivery");
		orderDao.update(order);
		
		request.setAttribute("order", order);
		request.setAttribute("total", total);
		
		return "payed.jsp";
	}
	
	
	//显示所有订单
	public String bought(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		
		List<Order> orders = new ArrayList<>();
		
		orders = orderDao.getUserOrder(uid,null); //获取用户所有订单
		for(Order order : orders){
			List<OrderItem> orderItems = new ArrayList<>(); 
			orderItems = orderItemDao.foreList(order.getId());
			//把product注入到每个orderItem中		
			for(OrderItem orderItem : orderItems){
				Product product = new Product();
				product = productDao.getOne(orderItem.getPid());
				orderItem.setProduct(product);
			}
			order.setOrderItems(orderItems);
		}
		
		request.setAttribute("orders", orders);
		
		return "bought.jsp";
	}
	
	//在订单页点击确认收货，跳到确认付款页面
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<>();
		
		order = orderDao.getOne(oid);
		orderItems = orderItemDao.foreList(order.getId());
		for(OrderItem orderItem : orderItems){
			Product product = new Product();
			product = productDao.getOne(orderItem.getPid());
			orderItem.setProduct(product);
		}
		order.setOrderItems(orderItems);

		request.setAttribute("order", order);
		
		return "confirmPay.jsp";
	}
	
	//在确认收货页面中，点击确认支付
	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		Date date = new Date();
		
		Order order = new Order();
		
		order = orderDao.getOne(oid);
		order.setStatus("waitReview");
		order.setConfirmDate(DateUtil.DateToString(date));
		orderDao.update(order);
		
		return "orderConfirmed.jsp";
	}
	
	//完成订单后，点击评价，跳到评价页面
	public String review(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<>();
		
		order = orderDao.getOne(oid);
		orderItems = orderItemDao.foreList(order.getId());
		for(OrderItem orderItem : orderItems){
			Product product = new Product();
			product = productDao.getOne(orderItem.getPid());
			orderItem.setProduct(product);
		}
		order.setOrderItems(orderItems);
		
		request.setAttribute("order", order);
		request.setAttribute("product", order.getOrderItems().get(0).getProduct());
		
		return "review.jsp";
	}
	
	//在评价页面提交评价
	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		String content = request.getParameter("content");
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		Date date = new Date();
		
		Review review = new Review();
		List<Review> reviews = new ArrayList<>();
		Order order = new Order();
		Product product = new Product();
		
		review.setContent(content);
		review.setPid(pid);
		review.setUid(uid);
		review.setCreateDate(date);
		reviewDao.add(review);
		reviews = reviewDao.list(pid);  //获取每个用户
		for(Review r : reviews){
			User u = new User();
			u = userDao.getOne(uid);
			r.setUser(u);
		}
			
		for(Review r : reviews){
			System.out.println("ooo" + r.getContent());
		}
		
	    order = orderDao.getOne(oid);
		order.setStatus("finish");
		orderDao.update(order);
		
		product = productDao.getOne(pid);
		
		request.setAttribute("reviews", reviews);
		request.setAttribute("order", order);
		request.setAttribute("product", product);
		request.setAttribute("showOnly", true);
		
		return "review.jsp";
	}
	
	
	//删除订单
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		
		Order order = orderDao.getOne(oid);
		order.setStatus("delete");
		orderDao.update(order);
		
		return "%success";
	}
}
