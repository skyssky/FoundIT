<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Company</title>
<%@ include file="header.html"%>
</head>
<body>
	<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div class="profile-form">
			<h3>Edit Company Profile</h3>
			<div class="alert alert-success " role="alert" id="info" style="display:none;"></div>
			<div class="alert alert-danger " role="alert" id="error" style="display:none;"></div>
			<form role="form" id="profile">
			 	<input type="hidden" name="userID" id="userID" />
			 	<input type="hidden" name="profileId" id="profileId"/>
				<div class="form-group">
					<label for="name">Company Name</label> <input type="text"
						class="form-control" name="name" id="name" placeholder="Company Name"/>
				</div>
				<div class="form-group">
					<label for="site">Company Site</label> <input type="text"
						class="form-control" name="site" id="site" placeholder="Company Site"/>
				</div>
				<div class="form-group">
					<label for="type">Industry Type</label> <input type="text"
						class="form-control" name="type" id="type" placeholder="Industry Type"/>
				</div>
				<div class="form-group">
					<label for="hq">HQ Address</label> <input type="text"
						class="form-control" name="hq" id="hq" placeholder="HQ Address"/>
				</div>
				<div class="form-group">
					<label for="detail">Brief Description</label> <input type="text"
						class="form-control" name="detail" id="detail" placeholder="Brief Description"/>
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
			      url: "/FoundITAppServer/loadCompanyProfile?userID="+userID,
			      success:function(data) { loadProfile(data); },
			      error: function(xhr,status,error) {}
			    });
		}
		function loadProfile(data){
			if(data === "{}"){
				//alert(data);
			}else{
				//alert(JSON.stringify(data));
				$("#userID").val(data.managerId);
				$("#name").val(data.name);
				$("#type").val(data.type);
				$("#hq").val(data.hq);
				$("#detail").val(data.detail);
				$("#site").val(data.site);
				$("#profileId").val(data.profileId);
			}
		}
		$("#save-button").click(function(e){
			e.preventDefault();
			$.ajax({
			      type: "post",
			      url: "/FoundITAppServer/companyProfile.action",
			      contentType: 'application/x-www-form-urlencoded',         
			      data: $("#profile").serializeArray(),
			      success:function(data) { $("#info").html(data); $("#info").show();},
			      error: function(xhr,status,error) {$("#error").html(error); $("#error").show(); }
			    });
		});
	</script>
</body>
</html>