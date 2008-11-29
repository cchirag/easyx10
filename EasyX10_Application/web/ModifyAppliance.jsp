<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.gui.GuiUtilities" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="java.util.List" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EasyX10 - Modify Appliance</title>
<link rel="stylesheet" type="text/css" href="easyx10.css" />
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
</head>

<%
	//Access data needed throughout the page

	// Retrieve the error message if one exists
	String statusMessage = (String)request.getAttribute( "statusMessage" );
	if( statusMessage == null ){
		statusMessage = "";
	}
		
	// Retrieve the Device to Modify
	String deviceName = request.getParameter("deviceName");
	ProxyX10Appliance device = 
		(ProxyX10Appliance)GuiUtilities.getDevice(deviceName);			
%>

<body onload="document.detailsForm.applianceName.focus()">

<script type="text/javascript">
	function processTimerSelect(displayTimer){
		if( displayTimer == 'ON' ){
			document.detailsForm.startTime.disabled = false;
			document.detailsForm.endTime.disabled = false;
		} else {
			document.detailsForm.startTime.disabled = true;
			document.detailsForm.endTime.disabled = true;
		}
	}

	function updateLocation(){
		document.detailsForm.top.value = document.getElementById("<%= deviceName %>").style.top;
		document.detailsForm.left.value = document.getElementById("<%= deviceName %>").style.left;
		document.detailsForm.startTime.disabled = false;
		document.detailsForm.endTime.disabled = false;
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
					<a id="statusLink" href="Status.jsp">System Status</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="nav1" style="float: right">
		<span><a href="Login.jsp">Logout</a></span> </div>
</div>
<div id="page_content">
	<span style="font-family: Arial, Helvetica, sans-serif; font-size: x-large; font-weight: bold">
	Modify Appliance</span> <br />
	<div>
		<span style="padding: 20px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: small; color: #FF0000">
			<%= statusMessage %>
		</span>
	</div>
	<br />
	<div id="addForm">
		<!-- span id="addFormMessage" style="font-family: Arial, Helvetica, sans-serif; font-size: large; font-weight: bold; font-variant: normal">
		Enter Details for New Item:</span> <br / -->
		<form name="detailsForm" id="detailsForm" method="post" action="/easyx10/EasyX10AppServlet?action=MODIFY_DEVICE&deviceType=APPLIANCE">
			<table id="detailsFormTable">
				<tr>
					<td>
						<span>Appliance Name: </span>
					</td>
					<td colspan="1">
						<input name="deviceNameDisplayed"  type="text" size="25" maxlength="25" tabindex="1" 
						value="<%= device.getName() %>" disabled="disabled" />
					</td>
					<td>
						<span>House Code:</span>
						<select name="houseCode" tabindex="2">
						<option <%= (device.getHouseCode() == 'A') ? "selected=\"selected\"" : "" %>>A</option>
						<option <%= (device.getHouseCode() == 'B') ? "selected=\"selected\"" : "" %>>B</option>
						<option <%= (device.getHouseCode() == 'C') ? "selected=\"selected\"" : "" %>>C</option>
						<option <%= (device.getHouseCode() == 'D') ? "selected=\"selected\"" : "" %>>D</option>
						<option <%= (device.getHouseCode() == 'E') ? "selected=\"selected\"" : "" %>>E</option>
						<option <%= (device.getHouseCode() == 'F') ? "selected=\"selected\"" : "" %>>F</option>
						<option <%= (device.getHouseCode() == 'G') ? "selected=\"selected\"" : "" %>>G</option>
						<option <%= (device.getHouseCode() == 'H') ? "selected=\"selected\"" : "" %>>H</option>
						<option <%= (device.getHouseCode() == 'I') ? "selected=\"selected\"" : "" %>>I</option>
						<option <%= (device.getHouseCode() == 'J') ? "selected=\"selected\"" : "" %>>J</option>
						<option <%= (device.getHouseCode() == 'K') ? "selected=\"selected\"" : "" %>>K</option>
						<option <%= (device.getHouseCode() == 'L') ? "selected=\"selected\"" : "" %>>L</option>
						<option <%= (device.getHouseCode() == 'M') ? "selected=\"selected\"" : "" %>>M</option>
						<option <%= (device.getHouseCode() == 'N') ? "selected=\"selected\"" : "" %>>N</option>
						<option <%= (device.getHouseCode() == 'O') ? "selected=\"selected\"" : "" %>>O</option>
						<option <%= (device.getHouseCode() == 'P') ? "selected=\"selected\"" : "" %>>P</option>
						</select> <span>Unit Code:</span>
						<select name="unitCode" tabindex="3">
						<option <%= (device.getDeviceCode() == 1) ? "selected=\"selected\"" : "" %>>1</option>
						<option <%= (device.getDeviceCode() == 2) ? "selected=\"selected\"" : "" %>>2</option>
						<option <%= (device.getDeviceCode() == 3) ? "selected=\"selected\"" : "" %>>3</option>
						<option <%= (device.getDeviceCode() == 4) ? "selected=\"selected\"" : "" %>>4</option>
						<option <%= (device.getDeviceCode() == 5) ? "selected=\"selected\"" : "" %>>5</option>
						<option <%= (device.getDeviceCode() == 6) ? "selected=\"selected\"" : "" %>>6</option>
						<option <%= (device.getDeviceCode() == 7) ? "selected=\"selected\"" : "" %>>7</option>
						<option <%= (device.getDeviceCode() == 8) ? "selected=\"selected\"" : "" %>>8</option>
						<option <%= (device.getDeviceCode() == 9) ? "selected=\"selected\"" : "" %>>9</option>
						<option <%= (device.getDeviceCode() == 10) ? "selected=\"selected\"" : "" %>>10</option>
						<option <%= (device.getDeviceCode() == 11) ? "selected=\"selected\"" : "" %>>11</option>
						<option <%= (device.getDeviceCode() == 12) ? "selected=\"selected\"" : "" %>>12</option>
						<option <%= (device.getDeviceCode() == 13) ? "selected=\"selected\"" : "" %>>13</option>
						<option <%= (device.getDeviceCode() == 14) ? "selected=\"selected\"" : "" %>>14</option>
						<option <%= (device.getDeviceCode() == 15) ? "selected=\"selected\"" : "" %>>15</option>
						<option <%= (device.getDeviceCode() == 16) ? "selected=\"selected\"" : "" %>>16</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Status:</span>
					</td>
					<td>
						<select name="deviceStatus" tabindex="4">
						<option value="ON" <%= device.getState() == X10Device.X10DeviceState.ON ? "selected=\"selected\"" : "" %>>
							ON
						</option>
						<option value="OFF"	<%= device.getState() == X10Device.X10DeviceState.OFF ? "selected=\"selected\"" : "" %>>
							OFF
						</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Timer:</span>
					</td>
					<td>
						<select name="timer" onchange="processTimerSelect(this.value);" tabindex="5">
						<option value="ON" 
							<%= device.getTriggerTimerEnabled() == true ? "selected=\"selected\"" : "" %>>ON</option>
						<option value="OFF" 
							<%= device.getTriggerTimerEnabled() == false ? "selected=\"selected\"" : "" %>>OFF</option>
						</select>
					</td>
					<td>
						<span>Start Time:</span>
					    <select name="startTime" <%= device.getTriggerTimerEnabled() ? "" : "disabled=\"disabled\"" %> tabindex=6">
					    	<%= GuiUtilities.generateHtmlTimeOptions(GuiUtilities.convertCalendarToString(device.getOnTime())) %>
					    </select>
						<span>End Time:</span>
						<select name="endTime" <%= device.getTriggerTimerEnabled() ? "" : "disabled=\"disabled\"" %> tabindex="7">
					    	<%= GuiUtilities.generateHtmlTimeOptions(GuiUtilities.convertCalendarToString(device.getOffTime())) %>
					    </select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br />
						<input name="update" type="submit" value="update" tabindex="8" onclick="updateLocation()" />
						&nbsp;
						<input name="delete" type="submit" value="delete" tabindex="9"/>
						&nbsp;
						<input name="CancelButton" type="button" value="Cancel" onclick="window.location='Status.jsp';" tabindex="10" />
					</td>
				</tr>
			</table>
			<input name="floorNumber" type="hidden" value="<%= "floor" + device.getLocation().getFloorNumber() %>" />
			<input name="deviceName" type="hidden" value="<%= device.getName() %>" />
			<input name="top" type="hidden" value="" />
			<input name="left" type="hidden" value="" />
		</form>
	</div>
		<% 
			
			List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");

			String currentFloor = (String)request.getParameter("selectedFloor");
			if( currentFloor != null ){
				session.setAttribute("currentFloor", currentFloor);
			} else {
				currentFloor = (String)session.getAttribute("currentFloor");
			}
			
		%>
	
		<div id="<%= currentFloor  %>" style="position: relative; width : 800px; 
				height: 400px; border: thin black solid; margin: 20px 20px 20px 20px; float: left">
			
			
			<span><%= currentFloor %></span>
			
			<% 
				for(int j=0; j< devices.size(); j++) { 
					if( ("floor" + devices.get(j).getLocation().getFloorNumber()).equals(currentFloor)){
			%>
			<div id="<%= devices.get(j).getName() %>" style="position: absolute; height: 40px; width: 40px; 
				<%= devices.get(j).getName().equals(deviceName) ? "z-index: 2;" : "" %>
				top: <%= devices.get(j).getLocation().getY() %>px; 
				left: <%= devices.get(j).getLocation().getX() %>px; 
				background-color: <%= (devices.get(j).getState().equals(X10Device.X10DeviceState.ON) ? "yellow" : "lightgray") %>; 
				border: 1px black solid; text-align:center">
				
				<span style="font-size:x-small"><%= devices.get(j).getName() %></span>
			</div>
			<%
					}
				}
			%>
			<div class="transparent" style="position: absolute; top: 0px; left: 0px; z-index: 1; background-color: gray; height: 400px; width: 800px;">
			</div>
		</div>
	<script type="text/javascript">
		new Draggable('<%= deviceName %>',{snap: constrainSnap} );
    
    	function constrainSnap(x, y){
    		return[ (x < 760) ? (x > 0 ? x : 0 ) : 760,
                    (y < 360) ? (y > 0 ? y : 0) : 360 ];
    	}	
  	</script>
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
