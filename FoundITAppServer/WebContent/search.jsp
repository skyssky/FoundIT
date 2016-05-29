<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="au.edu.soacourse.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Search Jobs</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div class="search-form">
			<h3>Search For Jobs</h3>
			<form role="form" id="search">
				<div class="form-group">
					<label for="keyword">Key word</label> <input type="text"
						class="form-control" name="keyword" placeholder="Key word"/>
				</div>
				<div class="form-group">
					<label for="skill">Skill</label> <input type="text"
						class="form-control" name="skill" placeholder="Skill"/>
				</div>
				<div class="form-group">
					<label for="jobstatus">Job Status</label>
					<select class="form-control" name="jobstatus" placeholder="Job Status">
						<option value="" disabled selected>Select Jobs Status</option>
					    <option value="">All</option>
					    <option value="open">Open</option>
					    <option value="closed">Unavailable</option>
					</select>	
				</div>	
				<button type="submit" id="search-button" class="btn btn-primary btn-submit">Search</button>
			</form>
		</div>		
		<div id="search-results">
		
		</div>
	</div>
	<script>
		$("#search-button").click(function(e){       
			e.preventDefault();
		    $.ajax({
		      type: "post",
		      url: "/FoundITAppServer/search.action",
		      contentType: 'application/x-www-form-urlencoded',         
		      data: $("#search").serializeArray(),
		      success:function(data) { updateSearchResult(data); },
		      error: function(xhr,status,error) {}
		    });
		});
		function updateSearchResult(data){
			$("#search-results").html("");
			var content = "<table class=\"table\">";
			content += "<th>Job detail</th><th>Desire skill</th><th>Salary</th><th>Location</th><th>Apply</th>";
			$.each(data,function(index, element) {
    			//alert('detail: ' + element.detail + ', location: ' + element.location);
    			content += '<tr><td>' + element.detail + "</td><td>" +  element.skill + "</td><td>" +  element.salary + "</td><td>" +  element.location + "</td><td>" +  "<a href=\"application?"+ element.jobId +"\"><button type=\"button\" class=\"btn btn-primary\">Apply</button></a>" + '</td></tr>';				
			});
			content += "</table>";
			$("#search-results").html(content);
		}
	</script>
</body>
</html>