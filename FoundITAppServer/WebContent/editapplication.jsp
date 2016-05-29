<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Application</title>
<%@ include file="header.html"%>
</head>
<body>
	<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">
		<div class="profile-form">
			<h3>Edit your application</h3>
			<div class="alert alert-success " role="alert" id="info" style="display:none;"></div>
			<div class="alert alert-danger " role="alert" id="error" style="display:none;"></div>
			<div id="job"></div>
			<form role="form" id="application">
			 	<input type="hidden" name="userID" id="userID" />
			 	<input type="hidden" name="profileId" id="profileId"/>
				<fieldset disabled>
					<div class="form-group">
						<label for="name">Name</label> <input type="text"
							class="form-control" name="name" id="name" placeholder="Name"/>
					</div>
					<div class="form-group">
						<label for="position">Current Position</label> <input type="text"
							class="form-control" name="position" id="position" placeholder="Current Position"/>
					</div>
					<div class="form-group">
						<label for="experience">Past Experience</label> <input type="text"
							class="form-control" name="experience" id="experience" placeholder="Past Experience"/>
					</div>
					<div class="form-group">
						<label for="education">Education</label> <input type="text"
							class="form-control" name="education" id="education" placeholder="Education"/>
					</div>
					<div class="form-group">
						<label for="skill">Professional Skill</label> <input type="text"
							class="form-control" name="skill" id="skill" placeholder="Professional Skill"/>
					</div>
				</fieldset>
				<div class="form-group">
					<label for="cover">Cover Letter</label> <textarea type=""
						class="form-control" name="cover" id="cover" placeholder="Cover Letter"/></textarea>
				</div>
				<button type="submit" id="save-button" class="btn btn-primary btn-submit">Save & Apply</button>
			</form>	
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			loadJobRequest("<%if(session.getAttribute("userID") != null){
								out.print(session.getAttribute("userID"));
							}%>","<%if(request.getAttribute("jobId") != null){
								out.print(request.getAttribute("jobId"));
							}%>");
		    loadProfileRequest("<% if(session.getAttribute("userID") != null){
	      							out.print(session.getAttribute("userID"));
	      						}%>");
		    loadApplicationRequest("<%if(session.getAttribute("userID") != null){
						out.print(session.getAttribute("userID"));
					}%>","<%if(request.getAttribute("jobId") != null){
						out.print(request.getAttribute("jobId"));
					}%>");
		});
		function loadProfileRequest(userID){
			$.ajax({
			      type: "get",
			      url: "/FoundITAppServer/loadProfile?userID="+userID,
			      success:function(data) { loadProfile(data); },
			      error: function(xhr,status,error) {}
			    });
		}
		function loadProfile(data){
			if(data === "{}"){
				//alert(data);
			}else{
				//alert(JSON.stringify(data));
				$("#userID").val(data.userId);
				$("#name").val(data.name);
				$("#position").val(data.position);
				$("#education").val(data.education);
				$("#experience").val(data.experience);
				$("#skill").val(data.skill);
			}
		}
		function loadApplicationRequest(userID,jobId){
			//alert(jobId);
		}
		function loadJobRequest(jobId){
			$.ajax({
			      type: "get",
			      url: "/FoundITAppServer/loadJob?jobId="+jobId,
			      success:function(data) { loadJob(data); },
			      error: function(xhr,status,error) {}
			    });
		}
		function loadJob(data){
			var content = "<table class=\"table\">";
			content += "<tr><td>Applying For</td><td>"+data.position+"</td></tr>";
			content += "<tr><td>Salary</td><td>"+data.salary+"</td></tr>";
			content += "<tr><td>Location</td><td>"+data.location+"</td></tr>";
			content += "<tr><td>Require Skill</td><td>"+data.skill+"</td></tr>";
			content += "<tr><td>Detail</td><td>"+data.detail+"</td></tr>";
			content += "<tr><td>Link</td><td>"+data.link+"</td></tr>";
			content += "<tr><td>Company</td><td>XX</td></tr>";
			content += "</table>";
			$("#job").html(content);
		}
	</script>
</body>
</html>