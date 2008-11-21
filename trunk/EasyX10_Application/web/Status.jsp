<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.util.*" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="java.util.List" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>EasyX10 - Status Screen</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="easyx10.css" />
</head>

<body>

<script type="text/javascript">
    function processFloorSelect(){
    	var selectedFloor = document.getElementById("floorSelect").value;
    	var floors = new Array("floor1", "floor2", "floor3");
		for (i=0;i<=floors.length;i++)
		{
			var floorDiv = document.getElementById(floors[i]);
			if( !floorDiv )continue;
			
			if( floors[i] == selectedFloor ) {
				floorDiv .style.display = 'block';
			} else {
				floorDiv.style.display = 'none';
			}
		}	
	}
</script>
<div id="masthead">
	<br />
	<span>EasyX10 Project</span> </div>
<div id="top_nav">
	<div style="float: left">
		<table border="0" cellpadding="0" cellspacing="0" class="nav1">
			<tr>
				<td class="nav1" style="text-align: center">
					<a href="SystemStatus.htm">System Status</a>
				</td>
				<td class="nav1" style="text-align: center">
					<a href="AddAppliance.htm">Add Appliance</a>
				</td>
				<td class="nav1" style="text-align: center">
					<a href="Default.htm">Add Motion Sensor</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="nav1" style="float: right">
		<span><a href="Default.htm">Logout</a></span> </div>
</div>
<% 
	// Retrieve config data
	String configFilePath = 
				getServletContext().getRealPath( "/WEB-INF/SysConfig.xml" );
	SystemConfiguration sysConfig = 
				ConfigurationUtilities.getSystemConfiguration(configFilePath);	
%>
<div id="page_content">
	<div style="font: large Arial, Helvetica, sans-serif; margin-left: 50px;">
		<span>Select a Floor: </span>
		<select id="floorSelect" name="floorSelect" onchange="processFloorSelect();">
		<% 
			for( int i=0; i< sysConfig.getFloorCount(); i++ ) {
		%>
		<option value="<%= "floor" + (i+1) %>"><%= "Floor #" + (i+1) %></option>
		<%
			}
		%>
		</select> </div>
	<br />
	<div id="house">
		
		<% 
			
			List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");
			for(int i=0; i< sysConfig.getFloorCount(); i++) {
		%>
	
		<div id="<%= "floor" + (i+1) %>" style="display: <%= (i>0) ? "none" : "block" %>; position: relative; width : 800px; 
				height: 400px; border: thin black solid; margin: 20px 20px 20px 20px; float: left">
			
			
			<span><%= "Floor #" + (i+1) %></span>
			
			<% 
				for(int j=0; j< devices.size(); j++) { 
					if( devices.get(j).getLocation().getFloorNumber() == (i+1) ){
			%>
			<div style="position: absolute; height: 40px; width: 40px; top: 
				<%= devices.get(j).getLocation().getY() %>px; left: <%= devices.get(j).getLocation().getY() %>px; background-color: 
				<%= (devices.get(j).getState().equals(X10Device.X10DeviceState.ON) ? "green" : "yellow") %>; border: 1px black solid; text-align:center">
				
				<span style="font-size:x-small"><a href="Room.htm"><%= devices.get(j).getName() %></a></span>
			</div>
			<%
					}
				}
			%>
		</div>
		
		<% 
			} 
		%>
		
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
