<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="navbar">
<nav class="navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <a class="navbar-brand" href="index">FoundIT App</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      <!--
        <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
        -->
        <%
        //For candidates
        if(session.getAttribute("role") != null && session.getAttribute("role").equals("candidate")){
        	out.print("<li><a href=\"search\">Search Jobs</a></li>");
        	out.print("<li><a href=\"profile\">Edit Profile</a></li>");
        	out.print("<li><a href=\"jobalert\">Job Alert</a></li>");
        } else if(session.getAttribute("role") != null && session.getAttribute("role").equals("manager")){
        	out.print("<li><a href=\"companyProfile\">Company Profile</a></li>");
        	out.print("<li><a href=\"managerJobs\">Recruitment</a></li>");
        	out.print("");
        }
        %>        
      
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	<%
	      	if(session.getAttribute("role") != null && session.getAttribute("role").equals("manager")){
	      		out.print("<li><a href=\"newJob\"><span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span> New Job</a></li>");	        	
	        }      	
      	%>            
        <li class="dropdown" >
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
	      	<%
	      		if(session.getAttribute("userName") != null){
	      			out.print(session.getAttribute("userName"));
	      		}else{
	      			out.print("Account");
	      		}
	      	%>
		  <span class="caret"></span></a>
          <ul class="dropdown-menu" style="text-align: center; min-width: auto;">
          	<%
	      		if(session.getAttribute("userName") != null){
	      			out.print("<li><a href=\"#\">"+((String)(session.getAttribute("role"))).toUpperCase()+"</a></li>");
	      			out.print("<li role=\"separator\" class=\"divider\"></li>");
	      			out.print("<li><a href=\"logout\">Logout</a></li>");
	      		}else{
	      			out.print("<li><a href=\"register\">Register</a></li><li><a href=\"login\">Login</a></li>");
	      		}
	      	%>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</div>