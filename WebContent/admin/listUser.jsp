<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
</script>

<title>用户管理</title>


<div class="workingArea">
	<h1 class="label label-info" >用户管理</h1>

	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>用户名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="u">
				<tr>
					<td>${u.id}</td>
					<td>${u.username}</td>
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
	<%--分页用的，下一页，上一个导航栏
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>  
	</div> --%>
	
	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
