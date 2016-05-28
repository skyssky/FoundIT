<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%@ include file="header.html"%>
<title>FoundIT App - Login</title>
</head>
<body>
	<div id="container">
		<jsp:include page="navbar.jsp" flush="true" />
		<div id="content">	
			<div class="login-form">
				<% 
					if(session.getAttribute("errorMessage") != null){
						String error = session.getAttribute("errorMessage").toString(); 
						if(error != ""){
							out.print("<div class=\"alert alert-danger\" role=\"alert\">"+error+"</div>");
							session.removeAttribute("errorMessage");
						}
					}
				%>
				<h3>Login</h3>
				<form role="form" action="login.action" method="POST">
					<div class="form-group">
						<label for="userName">User Name</label> <input type="text"
							class="form-control" name="userName" placeholder="User name"/>
					</div>
					<div class="form-group">
						<label for="pwd">Password</label> <input type="password"
							class="form-control" name="pwd" placeholder="Password"/>
					</div>
					<button type="submit" class="btn btn-primary btn-submit">Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>