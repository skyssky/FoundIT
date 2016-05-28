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
			<h3>Search For Jobs</h3>
			<form role="form" id="profile">
				<div class="form-group">
					<label for="name">Name</label> <input type="text"
						class="form-control" name="name" placeholder="Name"/>
				</div>
				<div class="form-group">
					<label for="position">Current Position</label> <input type="text"
						class="form-control" name="position" placeholder="Current Position"/>
				</div>
				<div class="form-group">
					<label for="experience">Past Experience</label> <input type="text"
						class="form-control" name="experience" placeholder="Past Experience"/>
				</div>
				<div class="form-group">
					<label for="skill">Professional Skill</label> <input type="text"
						class="form-control" name="skill" placeholder="Professional Skill"/>
				</div>
				<button type="submit" id="save-button" class="btn btn-primary btn-submit">Save</button>
			</form>			
		</div>
	</div>
</body>
</html>