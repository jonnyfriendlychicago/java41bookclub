<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


    
<!DOCTYPE html>
<html lang="en">
<head>
<title>java41bookclub - ${publication.pubTitle}</title>
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

</head>
<body>
	<div id=header class="container-fluid">
		<h1>java41bookclub</h1>
		<a href="/home">Home</a> 
		<a href="/publication">Publication List</a>
		<h2>Publication Profile</h2>
	</div>
	<div id=main class="container-fluid">
		<div id=about class="container-fluid">
			<%-- <h4>id: <c:out value="${publication.id}"></c:out></h4> --%>
			<h4>pubTitle: <c:out value="${publication.pubTitle}"></c:out></h4>
			<h4>pubAuthor: <c:out value="${publication.pubAuthor}"></c:out></h4>
			
			<p>Here are
			<c:choose>
				<c:when test="${user.id == publication.userMdl.id }">your</c:when>
				<c:otherwise>
				${publication.userMdl.userName}'s
				</c:otherwise>
			</c:choose> 
			thoughtsOnPub: </p>
			
			<pre class="textAreaReadOut"><c:out value="${publication.thoughtsOnPub}"></c:out></pre>

			
			
			
			
			<%-- 
			<h4>publication.dojoMdl.dojoName: <c:out value="${publication.dojoMdl.dojoName}"></c:out></h4> 
			--%> 
			<%-- <a href= "/publication/${publication.id}/edit">OrigEdit</a>  --%>
			
			<c:choose>
				<c:when test="${user.id == publication.userMdl.id }">
					<%-- <a href= "/publication/${publication.id}/edit">Edit</a>  --%>
					<a href= "/publication/${publication.id}/edit"><button class="btn btn-secondary">Edit</button></a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose> 
			
			<form action="/publication/${publication.id}" method="post">
			    <input type="hidden" name="_method" value="delete">
			    <!-- <input type="submit" value="Delete this publication"> -->
			    <button class="btn btn-danger">Delete this publication</button>
			</form>
		</div>
	</div>
 
 	<div id=footer class="container-fluid">
	<p class="footerText">Powered by Coding Dojo</p>
	</div>
	
</body>
</html>