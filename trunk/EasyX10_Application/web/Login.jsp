<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html dir="ltr" xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Welcome to the EasyX10 Application - Please Log In</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="easyx10.css" />
</head>

<body onload="document.loginform.username.focus()">

<div id="masthead"><br/>
	<span>EasyX10 Project</span>
</div>
<div id="top_nav">
</div>
<div id="page_content" style="text-align: center">
	<span style="font-family: Arial, Helvetica, sans-serif; font-size: large; font-weight: bold;">
	Welcome to the EasyX10 Web Application </span>
	<div id="loginDiv">
		<br />
		<span>Please Log In:</span>
		<form name="loginform" method="post" action="/easyx10/LoginServlet">
			Username:&nbsp;&nbsp; <input name="username" type="text" size="25" maxlength="25" /><br /><br/>
			Password:&nbsp;&nbsp; <input name="password" type="password" size="25" maxlength="25" /><br />
			
			<%
				// Retrieve the error message if one exists
				String errorMessage = (String)session.getAttribute( "errorMessage" );
				if( errorMessage == null ){
					errorMessage = "";
				}
			%>
			<span style="color: red; font-weight: bold"><%= errorMessage %></span>
			<br />
			
			<input name="loginButton" type="submit" value="Login"/>
		</form>
	</div>
</div>
<div id="footer">
	<div style="text-align: right; margin-right: 10px">
		<span>Created for CS673 - Boston University <br />
		<br />
		<a href="http://code.google.com/p/easyx10" target="_blank">EasyX10 Google 
		Code Project</a> </span></div>
</div>

</body>

</html>
