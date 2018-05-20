package servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDao;
import dao.OrderItemDao;
import pojo.Order;
import pojo.OrderItem;
import util.DateUtil;
import util.Page;

public class OrderServlet extends BaseBackServlet{

	private OrderDao orderDao = new OrderDao();
	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		
		page.setTotal(orderDao.getTotal()); //获取total，计算last
		
		//首先使用OrderDao的list查询Order所有订单，并且与User表左连接
		List<Order> orders = orderDao.list(page.getStart(),page.getCount());
		
		//因为一个订单对应多个订单项，所有遍历所有订单，找到它对应的订单项并赋值给order的orderItems
		//在OrderItemDao中，list是根据Order的id查询，并且与Product表进行左连接
		OrderItemDao orderItemDao = new OrderItemDao();
		for(Order order : orders){
			List<OrderItem> orderItems = orderItemDao.list(order.getId());
			order.setOrderItems(orderItems);
		}

		request.setAttribute("orders", orders);
		request.setAttribute("page", page);
		
		return "admin/listOrder.jsp";
	}
	
	
	//处理发货
	public String delivery(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id")); //获取订单的id		
		Date date = new Date(); //获取当前时间
				
		Order order = new Order();
		order = orderDao.getOne(id);
		order.setStatus("waitConfirm");
		order.setDeliveryDate(DateUtil.DateToString(date));
		orderDao.update(order);
		
		return "@admin_order_list";
	}
}
