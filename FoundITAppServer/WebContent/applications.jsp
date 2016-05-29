<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Applications</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
	<h3>Applications</h3>
		<div id="application-results">
		
		</div>
	</div>
<script>
	$(document).ready(function() {
	    loadApplicationsRequest("<% if(session.getAttribute("userID") != null){
	  							out.print(session.getAttribute("userID"));
	  						}%>");
	});
    function loadApplicationsRequest(userID){
		$.ajax({
		      type: "get",
		      url: "/FoundITAppServer/loadApplicationList?userID="+userID,
		      success:function(data) { loadApplications(data); },
		      error: function(xhr,status,error) {}
		    });
	}
    function loadApplications(data){
		if(data === "{}"){
			//alert(data);
		}else{
			//console.log(JSON.stringify(data));
			$("#application-results").html("");
			var content = "<table class=\"table\">";
			content += "<th>Job Position</th><th>Job Detail</th><th>Salary</th><th>Job Location</th><th>Status</th><th>Review Application</th>";
			$.each(data,function(index, element) {
    			//alert('detail: ' + element.detail + ', location: ' + element.location);
    			content += '<tr><td>' + element.position + "</td><td>" + element.detail + "</td><td>" + element.salary + "</td><td>" + element.location + "</td><td>" + element.status + "</td><td>" +  "<a href=\"application?jobId="+element.jobId+"\"><button type=\"button\" class=\"btn btn-primary\" id=\"process-button\">Review Application</button></a>" + '</td></tr>';				
			});
			content += "</table>";
			$("#application-results").html(content);
		}
	}
</script>
</body>
</html>