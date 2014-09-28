<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>


<head>
	<title>Hello World - Table</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.3/smoothness/jquery-ui-1.10.3.custom.css">
	<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="js/script.js"></script>	
</head>


<body>
<form id="form" action="table.do">
	<input type="hidden" id="action" name="action"/>

	<div class="page">
		<div id="title" class="title"><h2>Hello World</h2></div>
		<div id="subtitle" class="subtitle"><h3>Page: <b>Table</b></h3></div>
		<div class="content">
		
			<div class="errorBlock">
				<c:if test="${not empty requestScope.errorMessages}">
					<div id="errorMessage" class="errorMessage">${requestScope.errorMessages.selectedUsersIds}</div>
				</c:if>
			</div>
			
			<div class="listHeader">
				<ul>
					<li class="listHeaderTitle">User List</li>
				</ul>
			</div>
		
			<div class="listActions">
				<ul>
					<li>
						<a href="javascript:send('Create');" id="create">Create</a>
					</li>
					<li>
						<a href="javascript:send('View');" id="view">View</a>
					</li>
					<li>
						<a href="javascript:send('Edit');" id="edit">Edit</a>
					</li>
					<li>
						<a href="javascript:send('Delete');" id="delete">Delete</a>
					</li>
				</ul>		
			</div>
			
			<div class="listItems">
				<c:choose>
					<c:when test="${requestScope.users == null || 
									empty requestScope.users}">
						<div id="noData">No Data</div>			
					</c:when>
					<c:otherwise>
						<c:forEach items="${requestScope.users}" var="user">
							<div>
								<input id="selectedUsersIds${user.id}" name="selectedUsersIds" type="checkbox" value="${user.id}">
								<label for="selectedUsersIds${user.id}">${user.name}</label>
							</div>						
						</c:forEach>						
					</c:otherwise>
				</c:choose>	
			</div>
			
			
		</div>
		
	</div>		

</form>
</body>
</html>