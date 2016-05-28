<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Profile</title>
<%@ include file="header.html"%>
</head>
<body>
	<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div class="profile-form">
			<h3>Edit Your Profile</h3>
			<div class="alert alert-success " role="alert" id="info" style="display:none;"></div>
			<form role="form" id="profile">
			 	<input type="hidden" name="userID" id="userID" />
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
				<button type="submit" id="save-button" class="btn btn-primary btn-submit">Save</button>
			</form>			
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		    loadProfileRequest("<% if(session.getAttribute("userID") != null){
	      							out.print(session.getAttribute("userID"));
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
		
		$("#save-button").click(function(e){
			e.preventDefault();
			$.ajax({
			      type: "post",
			      url: "/FoundITAppServer/profile.action",
			      contentType: 'application/x-www-form-urlencoded',         
			      data: $("#profile").serializeArray(),
			      success:function(data) { $("#info").html(data); $("#info").show();},
			      error: function(xhr,status,error) {}
			    });
		});
	</script>
</body>
</html>