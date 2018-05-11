<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function(){
	$("button.orderPageCheckOrderItems").click(function(){
		var oid = $(this).attr("oid");  
		$("tr.orderPageOrderItemTR[oid="+oid+"]").toggle(); //因为每个订单都会有一个显示详情的按钮和div，是通过oid这个属性区别不同的div和button
	});
});

</script>

<title>订单管理</title>


<div class="workingArea">
	<h1 class="label label-info" >订单管理</h1>
	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover1  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>状态</th>
					<th>金额</th>
					<th width="100px">商品数量</th>
					<th width="100px">买家名称</th>
					<th>创建时间</th>
					<th>支付时间</th>
					<th>发货时间</th>
					<th>确认收货时间</th>
					<th width="120px">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="o">
					<tr>
						<td>${o.id}</td>
						<td>${o.statusZH}</td>
						<td>￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2"/></td>
						<td align="center">${o.totalNumber}</td>
						<td align="center">${o.user.username}</td>
						
						<td>${o.createDate}</td>
						<td>${o.payDate}</td>
						<td>${o.deliveryDate}</td>
						<td>${o.confirmDate}</td>

						<td>     <!-- 控制下面orderPageOrderItemTR的显示和隐藏 -->
							<button oid=${o.id } class="orderPageCheckOrderItems btn btn-primary btn-xs">查看详情</button>
							
							<c:if test="${o.status=='waitDelivery'}">
								<a href="admin_order_delivery?id=${o.id}">
									<button class="btn btn-primary btn-xs">发货</button>
								</a>							
							</c:if>
						</td>
					</tr>
					<tr class="orderPageOrderItemTR"  oid=${o.id }>
						<td colspan="10" align="center">
							<div  class="orderPageOrderItem"> 
								<table width="800px" align="center" class="orderPageOrderItemTable">
									<c:forEach items="${o.orderItems}" var="oi">
										<tr>
									
											<td align="left">
												<img width="40px" height="40px" src="img/productSingle/${oi.product.id}.jpg">
											</td>	
											
											<td>
												<a href="foreproduct?pid=${oi.product.id}">
													<span>${oi.product.name}</span>
												</a>											
											</td>
											<td align="right">
											
												<span class="text-muted">${oi.number}个</span>												
											</td>
											<td align="right">
											
												<span class="text-muted">单价：￥${oi.product.promotePrice}</span>												
											</td>

										</tr>
									</c:forEach>
								
								</table>

							</div>
						
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div style="text-align:right">
            <a href="?start=0">首页</a>
            <c:if test="${page.start-page.count>=0}">
               <a href="?start=${page.start-page.count }">上一页</a>
            </c:if>
            <c:if test="${page.start-page.count<0}">  <!--防止越界  -->
               <a href="?start=0">上一页</a>        
            </c:if>
            <c:if test="${page.start+page.count<=page.last}">
              <a href="?start=${page.start+page.count }">下一页</a>
            </c:if>
            <c:if test="${page.start+page.count>page.last}">
              <a href="?start=${page.last }">下一页</a> <!-- 防止越界 -->
            </c:if>
            <a href="?start=${page.last }">末页</a>
       </div>
	</div>
	<%-- 分页导航栏
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>--%>
	

	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
