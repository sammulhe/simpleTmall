<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<%--在页面中使用JSTL需要在jsp中 通过指令进行设置
    prefix="c" 表示后续的标签使用都会以<c: 开头 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--fmt 标签常用来进行格式化，其中fmt:formatNumber用于格式化数字,使用之前要加下面的语句 --%>
<%--fmt 标签常用来进行格式化，其中fmt:formatDate 用于格式化日期,使用之前也是加下面的语句 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix='fmt' %>  

<%--fn标签提供各种实用功能，首先使用之前使用加入如下指令 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:if test="${empty param.categorycount}">
	<c:set var="categorycount" scope="page" value="100"/>
</c:if>

<c:if test="${!empty param.categorycount}">
	<c:set var="categorycount" scope="page" value="${param.categorycount}"/>
</c:if>
	
<div class="categoryProducts">
	<c:forEach items="${category.products}" var="p" varStatus="stc">
		<c:if test="${stc.count<=categorycount}">
		<div class="productUnit" price="${p.promotePrice}">
			<div class="productUnitFrame">
				<a href="foreproduct?pid=${p.id}">
					<img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
				</a>
				<span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span>
				<a class="productLink" href="foreproduct?pid=${p.id}">
				 ${fn:substring(p.name, 0, 50)}
				</a>
				
				<a  class="tmallLink" href="foreproduct?pid=${p.id}">天猫专卖</a>	
				<div class="show1 productInfo">
				<span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
					<span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>	 
					<span class="wangwang">
					<a class="wangwanglink" href="#nowhere">
						<img src="img/site/wangwang.png">
					</a>
					
					</span>
				</div>
			</div>
		</div>
		</c:if>
	</c:forEach>
		<div style="clear:both"></div>
</div>