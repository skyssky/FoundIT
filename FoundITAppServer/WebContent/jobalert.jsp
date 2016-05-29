<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="au.edu.soacourse.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FoundIT App - Job Alerts</title>
<%@ include file="header.html"%>
</head>
<body>
<jsp:include page="navbar.jsp" flush="true" />
	<div id="content">	
		<div class="search-form">
			<h3>Create Job Alerts</h3>
			<form role="form" id="search">
				<div class="form-group">
					<label for="keyword">Key word</label> <input type="text"
						class="form-control" name="keyword" placeholder="Key word"/>
				</div>
<!-- 				<div class="form-group">
					<label for="sort_by">Sort by</label> <input type="text"
						class="form-control" name="sort_by" placeholder="Sort"/>
				</div> -->
				<div class="form-group">
					<label for="sort_by">Sort by</label>
					<select class="form-control" name="sort_by" placeholder="Sort by">
						<option value="" disabled selected>Select sort method</option>
						<option value="">none</option>
					    <option value="jobtitle">job title</option>
					</select>	
				</div>	
				<button type="submit" id="search-button" class="btn btn-primary btn-submit">Send to email</button>
			</form>
		</div>		
		<div id="search-results">
		
		</div>
	</div>
	<script>
		
		$("#search-button").click(function(e){       
			e.preventDefault();
		    $.ajax({
		      type: "get",
		      url: "/FoundITAppServer/jobAlert",
		      /* contentType: 'application/x-www-form-urlencoded', */         
		      data: $("#search").serializeArray(),
		      success:function(data) { updateSearchResult(data); },
		      error: function(xhr,status,error) {}
		    });
		});

		function updateSearchResult(data){
/* 			$("#search-results").html("");
			var content = "<table class=\"table\">";
			content += "<th>Job detail</th><th>Desire skill</th><th>Salary</th><th>Location</th><th>Apply</th>";
			$.each(data,function(index, element) {
    			//alert('detail: ' + element.detail + ', location: ' + element.location);
    			content += '<tr><td>' + element.detail + "</td><td>" +  element.skill + "</td><td>" +  element.salary + "</td><td>" +  element.location + "</td><td>" +  "<a href=\"application?jobId="+ element.jobId +"\"><button type=\"button\" class=\"btn btn-primary\">Apply</button></a>" + '</td></tr>';				
			});
			content += "</table>"; */
			$("#search-results").html(data);
		}
	</script>
</body>
</html>