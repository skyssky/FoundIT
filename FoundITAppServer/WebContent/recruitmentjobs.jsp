<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="au.edu.soacourse.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Recruitment - Jobs</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div id="job-positions">
		</div>
	</div>
	<script>
		$(document).ready(function() {
		    loadJobProfilesRequest("<% if(session.getAttribute("userID") != null){
	      							out.print(session.getAttribute("userID"));
	      						}%>");
		});
	    function loadJobProfilesRequest(userID){
			$.ajax({
			      type: "get",
			      url: "/FoundITAppServer/loadManagerJobs?userID="+userID,
			      success:function(data) { loadJobPositions(data); },
			      error: function(xhr,status,error) {}
			    });
		}
		function loadJobPositions(data){
			if(data === "{}"){
				//alert(data);
			}else{
				$("#job-positions").html("");
				var content = "<table class=\"table\">";
				content += "<th>Job Position</th><th>Job Detail</th><th>Review Job</th>";
				$.each(data,function(index, element) {
	    			//alert('detail: ' + element.detail + ', location: ' + element.location);
	    			content += '<tr><td>' + element.position + "</td><td>" + element.detail + "</td><td>" +  "<a href=\"managerJob?jobId="+element.jobId+"\"><button type=\"button\" class=\"btn btn-primary\" id=\"process-button\">Review Job</button></a>" + '</td></tr>';				
				});
				content += "</table>";
				$("#job-positions").html(content);
			}
		}
	</script>
</body>
</html>