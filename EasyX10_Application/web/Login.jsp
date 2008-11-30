<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html dir="ltr" xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Welcome to the EasyX10 Application - Please Log In</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="easyx10.css" />
</head>

<body onload="document.loginform.username.focus()">

<div id="masthead">
	<img alt="EasxyX10 Logo" src="resources/EasyX10.gif" />
</div>
<div id="top_nav">
</div>
<div id="page_content" style="text-align: center">
    <br /><br />
        <div id="loginDiv">
                <br /><br />
                <br /><br />
                <br />
                <form name="loginform" method="post" action="/easyx10/LoginServlet">
                        Username:&nbsp;&nbsp;
                        <input name="username" type="text" /><br /><br/>
                        Password:&nbsp;&nbsp;&nbsp;
                        <input name="password" type="password" /><br /><br/>
                        <input name="loginButton" type="image" alt="LoginButton" src="resources/login.gif"
                        style="position: relative; top: 0px; left: 95px" />
                        <%
							// Retrieve the error message if one exists
							String errorMessage = (String)session.getAttribute( "errorMessage" );
							if( errorMessage == null ){
								errorMessage = "";
							}
						%>
						<br />
                        <span style="color: red; font-weight: bold"><%= errorMessage %></span>
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
