<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<div id="job-applicants">
		
		</div>
		<button id="autocheck-button" class="btn btn-primary " style="display:none;">Process</button>	
	</div>
	<script>
		$(document).ready(function() {
		    loadJobapplicantsRequest("<% if(request.getAttribute("jobId") != null){
	      										out.print(request.getAttribute("jobId"));
	      							}%>");
		});
		function loadJobapplicantsRequest(jobId){
			$.ajax({
			      type: "get",
			      url: "/FoundITAppServer/loadCandidates?jobId="+jobId,
			      success:function(data) { loadJobApplicants(data); },
			      error: function(xhr,status,error) {}
			    });
		}
		function loadJobApplicants(data){
			if(data === "{}"){
				//alert(data);
			}else{
				$("#job-applicants").html("");
				//console.log(JSON.stringify(data));
				var content = "<table class=\"table\">";
				content += "<th>Applicant Name</th><th>Skill</th><th>Experience</th><th>Education</th><th>Current Position</th>";
				$.each(data,function(index, element) {
	    			//alert('detail: ' + element.detail + ', location: ' + element.location);
	    			content += "<tr><td>" + element.name + "</td><td>" + element.skill + "</td><td>" + element.experience + "</td><td>" + element.education + "</td><td>" + element.position + "</td></tr>";				
				});
				content += "</table>";
				$("#job-applicants").html(content);
				$("#autocheck-button").show();
			}
		}
		$("#autocheck-button").onclick(function(){
			
		})
	</script>
</body>
</html>