<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

    
<!DOCTYPE html>
<html lang="en">
<head>
<title>java41bookclub</title>
<meta charset="UTF-8">
<!-- local css -->
<link rel="stylesheet" type="text/css" href="/css/style.css">
<!-- local javascript -->
<script type ="text/javascript" src="javascript/app.js"></script>
<!--  Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" /> 
<!-- For any Bootstrap that uses JS or jQuery-->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<!-- google fonts try -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">

</head>
<body>
	<div id=header class="container-fluid">
		<div id=header-top>
			<h1>java41bookclub</h1>
			<h4>${user.userName}</h4>
			<a href="/logout">Logout</a>
		</div>
		<div id=nav class="container-fluid">
			<a href="home">Home</a> 
			<a href= "/date">Date Template</a>
			<a href= "/time">Time Template</a>
			<a href= "/dojos">Dojos</a>
			<a href= "/books">Books</a>
			<a href= "/expenses">Expenses</a>
			<a href= "/expensesAndCreate">expensesAndCreate</a>
			<a href="/dojo">Dojo List</a>
			<a href= "/ninja">Ninja List</a>
			<a href= "/publication">Book Club</a>
		</div>
	</div>
	<div id=pageHeader class="container-fluid">
		<h2>Java App Home</h2>
	</div>
	
	<div id=main class="container-fluid">
		<div id=registerLogin class="container-fluid">
		<h2>Welcome, <c:out value="${user.userName}"></c:out>!</h2>
		<h3>Thanks for being part of our growing community.  Let's talk more soon.</h3>
	</div>
		
		
		
	</div>
	<div id=footer class="container-fluid">
	<p class="footerText">Powered by Coding Dojo</p>
	</div>
			
			
</body>
</html>