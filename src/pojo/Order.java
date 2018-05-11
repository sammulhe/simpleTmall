package pojo;

import java.util.List;

public class Order {
	private int id;
	private String orderCode;  //订单号
	private String address;   //收货地址
	private String post;       //邮编
	private String receiver;  //收货人信息
	private String mobile;   //手机号码
	private String userMessage; //用户备注信息
	private String createDate; //订单创建日期
	private String payDate;   //支付日期
	private String deliveryDate; //发货日期
	private String confirmDate;  //确认收货日期
	private String status;   //订单状态
	
	private List<OrderItem> orderItems; //获取OrderItems,计算总金额,OrderItem本身有数量又有Product，Product有商品价格
	private User user; //获取下该订单的用户
	
	private float total = 0; //一个订单计算总金额
	private int totalNumber = 0; //一个订单的商品数量
	private String statusZH; //各种状态对应的中文
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getOrderCode(){
		return this.orderCode;
	}
	public void setOrderCode(String orderCode){
		this.orderCode = orderCode;
	}

	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getPost(){
		return this.post;
	}
	public void setPost(String post){
		this.post = post;
	}
	
	public String getReceiver(){
		return this.receiver;
	}
	public void setReceiver(String receiver){
		this.receiver = receiver;
	}
	
	public String getMobile(){
		return this.mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public String getUserMessage(){
		return this.userMessage;
	}
	public void setUserMessage(String userMessage){
		this.userMessage = userMessage;
	}
	
	public String getCreateDate(){
		return this.createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}
	
	public String getPayDate(){
		return this.payDate;
	}
	public void setPayDate(String payDate){
		this.payDate = payDate;
	}
	
	public String getDeliveryDate(){
		return this.deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}
	
	public String getConfirmDate(){
		return this.confirmDate;
	}
	public void setConfirmDate(String confirmDate){
		this.confirmDate = confirmDate;
	}
	
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	
	public User getUser(){
		return this.user;
	}
	public void setUser(User user){
		this.user = user;
	}
	
	public List<OrderItem> getOrderItems(){
		return this.orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems){
		this.orderItems = orderItems;
	}
	
	//根据orderitems计算总金额
	public float getTotal(){
		for(OrderItem oi:orderItems ){
			total = total + oi.getNumber() * oi.getProduct().getPromotePrice();
		}
		return total;
	}
	public int getTotalNumber(){
		for(OrderItem oi:orderItems ){
			totalNumber = totalNumber + oi.getNumber();
		}
		return totalNumber;
	}
	
	//根据status对应的中文，因为数据库有中文不太好
	public String getStatusZH(){
		
		if(status.equals("waitPay")){
			statusZH = "待付款";
		}else if(status.equals("waitDelivery")){
			statusZH = "待发货";
		}else if(status.equals("waitConfirm")){
			statusZH = "待收货";
		}else if(status.equals("waitReview")){
			statusZH = "待评价";
		}else if(status.equals("finish")){
			statusZH = "完成";
		}else if(status.equals("delete")){
			statusZH = "删除";
		}else{
			statusZH = "未知";
		}
		
		return statusZH;
	}
}
