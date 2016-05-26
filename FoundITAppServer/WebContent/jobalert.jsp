<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="header.html"%>
<title>FoundIT App - Job Alerts</title>
</head>
<body>
	<div id="container">
		<jsp:include page="navbar.jsp" flush="true" />
		<div id="content">	
			<div class="jobalert-form">
				<% 
					if(session.getAttribute("errorMessage") != null){
						String error = session.getAttribute("errorMessage").toString(); 
						if(error != ""){
							out.print("<div class=\"alert alert-danger\" role=\"alert\">"+error+"</div>");
							session.setAttribute("errorMessage", "");
						}
					}
				%>
				<h3>Search Jobs</h3>
				<form role="form" action=jobalert.jsp method="GET">
					<div class="form-group">
						<label for="keyword">Keyword</label> <input type="text"
							class="form-control" name="keyword" placeholder="Search by keyword"/>
					</div>
					<div class="form-group">
						<label for="sort_by">Sort by (e.g. jobtitle)</label> <input type="text"
							class="form-control" name="sort_by" placeholder="Sort by"/>
					</div>
					<button type="submit" class="btn btn-primary btn-submit">Search</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>