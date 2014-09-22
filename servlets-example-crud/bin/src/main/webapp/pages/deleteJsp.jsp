<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>


<head>
	<title>Hello World</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/jquery-ui-1.10.3/smoothness/jquery-ui-1.10.3.custom.css">
	<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="js/script.js"></script>	
</head>


<body>
<form id="form" action="delete.do">
	<input type="hidden" id="action" name="action"/>
	<input type="hidden" id="userId" name="userId" value="${requestScope.user.id}"/>

	<div class="page">
		<div id="title" class="title"><h2>Hello World</h2></div>
		<div id="subtitle" class="subtitle"><h3>Page: <b>Delete</b></h3></div>
		<div class="content">
			<div class="contentElement">
				<br/>
				<div id="confirmationText" class="text">Do you really want delete user: ${requestScope.user.name}?</div>
			</div>
			
		</div>
		<div class="buttons">
			<input type="button" id="delete" name="delete" value="Delete" onclick="send('Delete');" title="Delete user"/>
			<input type="button" id="back" name="back" value="Back" onclick="send('Back');" title="Go back to list of users"/>
		</div>
	</div>		

</form>
</body>


</html>