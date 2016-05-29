<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="au.edu.soacourse.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<% 
			if(session.getAttribute("infoMessage") != null){
				String info = session.getAttribute("infoMessage").toString(); 
				if(info != ""){
					out.print("<div class=\"alert alert-success \" role=\"alert\">"+info+"</div>");
					session.removeAttribute("infoMessage");
				}
			}
		%>
		<div class="welcome">
			Welcome to Found IT!
		</div>
	</div>
</body>
</html>