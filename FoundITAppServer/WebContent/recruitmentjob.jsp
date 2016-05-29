<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="au.edu.soacourse.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Recruitment - Job</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div class="job-form">
			<h3>Job Posting</h3>
			<div class="alert alert-success " role="alert" id="info" style="display:none;"></div>
			<form role="form" id="posting">
			 	<input type="hidden" name="managerId" id="managerId" />
			 	<input type="hidden" name="jobId" id="jobId" />
				<div class="form-group">
					<label for="position">Position</label> <input type="text"
						class="form-control" name="position" id="position" placeholder="Position"/>
				</div>
				<div class="form-group">
					<label for="salary">Salary</label> <input type="text"
						class="form-control" name="salary" id="salary" placeholder="Salary"/>
				</div>
				<div class="form-group">
					<label for="location">Location</label> <input type="text"
						class="form-control" name="location" id="location" placeholder="Location"/>
				</div>
				<div class="form-group">
					<label for="skill">Require Skill</label> <input type="text"
						class="form-control" name="skill" id="skill" placeholder="Require Skill"/>
				</div>
				<div class="form-group">
					<label for="link">Link</label> <input type="text"
						class="form-control" name="link" id="link" placeholder="Link"/>
				</div>
				<div class="form-group">
					<label for="detail">Job Description</label> <textarea type=""
						class="form-control" name="detail" id="detail" placeholder="Job Description"/></textarea>
				</div>
				<div class="form form-inline" style="text-align: center;">
					<div class="form-group">
						<button type="submit" id="save-button" class="btn btn-primary">Save</button>
					</div>
					<div class="form-group">
						<button id="process-button" class="btn btn-primary ">Process</button>
					</div>
				</div>
			</form>			
		</div>
	</div>
	<script>
		$(document).ready(function() {
			loadJobRequest("<% if(request.getAttribute("jobId") != null){
	      							out.print(request.getAttribute("jobId"));
	      						}%>");
		});
		
		function loadJobRequest(jobId){
			$.ajax({
			      type: "get",
			      url: "/FoundITAppServer/loadJob?jobId="+jobId,
			      success:function(data) { loadJob(data); },
			      error: function(xhr,status,error) {}
			    });
		}
			
		function loadJob(data){
			if(data === "{}"){
				//alert(data);
			}else{
				//alert(JSON.stringify(data));
				$("#managerId").val(data.managerId);
				$("#jobId").val(data.jobId);
				$("#position").val(data.position);
				$("#salary").val(data.salary);
				$("#location").val(data.location);
				$("#skill").val(data.skill);
				$("#link").val(data.link);
				$("#detail").val(data.detail);
			}
		}
		$("#save-button").click(function(e){
			e.preventDefault();
			$.ajax({
			      type: "post",
			      url: "/FoundITAppServer/managerJob.action",
			      contentType: 'application/x-www-form-urlencoded',         
			      data: $("#profile").serializeArray(),
			      success:function(data) { $("#info").html(data); $("#info").show();},
			      error: function(xhr,status,error) {}
			    });
		});
	</script>
</body>
</html>