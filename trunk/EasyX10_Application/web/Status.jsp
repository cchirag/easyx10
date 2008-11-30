<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.util.*" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="edu.bu.easyx10.gui.*" %>
<%@ page import="java.util.List" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EasyX10 - Status Screen</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--  meta http-equiv="refresh" content="5" 
	  url="http://localhost:8080/easyx10/status.jsp"/ -->
<link rel="stylesheet" type="text/css" href="easyx10.css" />
</head>

<% 
	// Retrieve config data
	SystemConfiguration sysConfig = 
				ConfigurationUtilities.getSystemConfiguration();	
	
	// Get the current floor
	String currentFloor = request.getParameter("floor");
	if( currentFloor == null ){
		currentFloor = (String)session.getAttribute("currentFloor");
	}
%>

<body>

<script type="text/javascript">

	// Set the currentFloor
	var currentFloor = "<%= currentFloor %>";

	// Code to refresh the status page
	var to = setTimeout(
			"location.replace('Status.jsp?floor=" + currentFloor + "')",10000);

	// Function used to process the selection of a new floor
    function processFloorSelect(){
    	currentFloor = document.getElementById("floorSelect").value;
    	var numFloors = <%= sysConfig.getFloorCount() %>;
		for (var i=0; i<= numFloors; i++)
		{
			var floor = "floor" + (i+1);
			var floorDiv = document.getElementById(floor);
			if( !floorDiv )continue;
			
			if( currentFloor == floor ) {
				floorDiv .style.display = 'block';
			} else {
				floorDiv.style.display = 'none';
			}
		}	

		// Update the add device buttons to pass current floor info.
		var addAppLink = document.getElementById("addApplianceLink");
		addAppLink.href = "AddAppliance.jsp?selectedFloor=" + currentFloor;
		var addMotionLink = document.getElementById("addMotionLink");
		addMotionLink.href = "AddMotionSensor.jsp?selectedFloor=" + currentFloor;

		// Reset the page refresh to use the new floor
		clearTimeout(to);
		to = setTimeout(
				"location.replace('Status.jsp?floor=" + currentFloor + "')",10000);
	}
</script>
<div id="masthead">
	<img alt="EasxyX10 Logo" src="resources/EasyX10.gif" />
<div id="top_nav">
	<div style="float: left">
		<table border="0" cellpadding="0" cellspacing="0" class="nav1">
			<tr>
				<td class="nav1" style="text-align: center">
					<a href="Status.jsp">System Status</a>
				</td>
				<td class="nav1" style="text-align: center">
					<a id="addApplianceLink" href="AddAppliance.jsp?selectedFloor=<%= currentFloor %>">Add Appliance</a>
				</td>
				<td class="nav1" style="text-align: center">
					<a id="addMotionLink" href="AddMotionSensor.jsp?selectedFloor=<%= currentFloor %>">Add Motion Sensor</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="nav1" style="float: right">
		<span><a href="Login.jsp">Logout</a></span> </div>
</div>

<div id="page_content">
	<div style="font: large Arial, Helvetica, sans-serif; margin-left: 50px;">
		<span>Select a Floor: </span>
		<select id="floorSelect" name="floorSelect" onchange="processFloorSelect();">
		<% 
			for( int i=0; i< sysConfig.getFloorCount(); i++ ) {
				String thisFloor = "floor" + (i+1);
		%>
		<option value="<%= thisFloor %>" <%= currentFloor.equals(thisFloor) ? " selected=\"selected\" " : "" %> >
			<%= "Floor #" + (i+1) %>
		</option>
		<%
			}
		%>
		</select> 
	</div>
	<br />
	<div style="margin-left: 50px">
		<%
			// Retrieve the error message if one exists
			String statusMessage = (String)request.getAttribute( "statusMessage" );
			if( statusMessage == null ){
				statusMessage = "";
			}
		%>
		<span style="color: red; font-weight: bold"><%= statusMessage %></span>
	</div>
	<div id="house">
		
		<% 
			// Retrieve the Device List and the current floor
			GuiUtilities.updateSessionDeviceList(session);
			List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");
			
			// Create each floor
			for(int i=0; i< sysConfig.getFloorCount(); i++) {
		%>
	
		<div id="<%= "floor" + (i+1) %>" style="display: <%= ("floor" + (i+1)).equals(currentFloor) ? "block" : "none" %>; position: relative; width : 800px; 
				height: 400px; border: thin black solid; margin: 20px 20px 20px 20px; float: left">
			
			
			<span><%= "Floor #" + (i+1) %></span>
			
			<%
				// Create each device in each floor
				for(int j=0; j< devices.size(); j++) { 
					if( devices.get(j).getLocation().getFloorNumber() == (i+1) ){
			%>
			<div style="position: absolute; height: 40px; width: 40px; top: 
				<%= devices.get(j).getLocation().getY() %>px; left: <%= devices.get(j).getLocation().getX() %>px; background-color: 
				<%= (devices.get(j).getState().equals(X10Device.X10DeviceState.ON) ? "yellow" : "lightgray") %>; border: 1px black solid; text-align:center">
				
				<span style="font-size:x-small">
					<a href="<%= ((devices.get(j) instanceof ProxyX10Appliance) ? "ModifyAppliance.jsp" : "ModifyMotionSensor.jsp") 
							+ "?deviceName=" + devices.get(j).getName() %>">
					<%= devices.get(j).getName() %></a>
				</span>
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
