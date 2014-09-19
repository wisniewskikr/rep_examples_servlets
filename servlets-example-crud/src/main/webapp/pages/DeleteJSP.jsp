<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello</title>
</head>

<body>
<form action="delete.do">
<div style="width: 400px; font-family: Arial; font-size: 9pt;">
	<input type="hidden" id="id" name="id" value="${requestScope.id}"/>	
	
	<table>
		<tr>
			<td colspan="2" style="padding-bottom: 10px;">
				<h2>Hello World Servlets JPA</h2>
				<h3>Site: <b>Delete</b></h3>
			</td>
		</tr>
		<tr>
			<td colspan="2">Do you really want delete name: ${requestScope.userName}?</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" id="submit" name="submit" value="OK"/>
				<input type="submit" id="back" name="submit" value="Back"/>
			</td>
		</tr>		
	</table>
</div>
</form>
</body>

</html>