<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="header.html"%>
<title>FoundIT App - Register</title>
</head>
<body>
	<div id="container">
		<jsp:include page="navbar.jsp" flush="true" />
		<div id="content">	
			<div class="register-form">
				<% 
					if(session.getAttribute("errorMessage") != null){
						String error = session.getAttribute("errorMessage").toString(); 
						if(error != ""){
							out.print("<div class=\"alert alert-danger\" role=\"alert\">"+error+"</div>");
							session.setAttribute("errorMessage", "");
						}
					}
				%>
				<h3>Register</h3>
				<form role="form" action=register.action method="POST">
					<div class="form-group">
						<label for="userName">User Name</label> <input type="text"
							class="form-control" name="userName" placeholder="User name"/>
					</div>
					<div class="form-group">
						<label for="pwd">Password</label> <input type="password"
							class="form-control" name="pwd" placeholder="Password"/>
					</div>
					<div class="form-group">
						<label for="userName">User Role</label>
						<select class="form-control" name="role" placeholder="User Role">
							<option value="" disabled selected>Select Your Role</option>
						    <option value="candidate">Candidate</option>
						    <option value="manager">Manager</option>
						    <option value="reviewer">Reviewer</option>
						</select>	
					</div>	
					<button type="submit" class="btn btn-primary btn-submit">Register</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>