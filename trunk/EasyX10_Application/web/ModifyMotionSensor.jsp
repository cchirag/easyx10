<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.gui.GuiUtilities" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="java.util.List" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EasyX10 - Modify Motion Sensor</title>
<link rel="stylesheet" type="text/css" href="easyx10.css" />
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
</head>

<%
	// Access data needed throughout the page
	
	// Retrieve the error message if one exists
	String statusMessage = (String)request.getAttribute( "statusMessage" );
	if( statusMessage == null ){
		statusMessage = "";
	}
			
	// Retrieve the Device to Modify
	String deviceName = request.getParameter("deviceName");
	ProxyX10MotionSensor device = 
		(ProxyX10MotionSensor)GuiUtilities.getDevice(deviceName);
	
	// Retrieve the list of device
	List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");
%>

<body onload="document.detailsForm.motionSensorName.focus()">

<script type="text/javascript">
	function processActivityWindowSelect(displayActivityWindow){
		if( displayActivityWindow == 'true' ){
			document.detailsForm.startTime.disabled = false;
			document.detailsForm.endTime.disabled = false;
		} else {
			document.detailsForm.startTime.disabled = true;
			document.detailsForm.endTime.disabled = true;
		}
	}

	function processActivityTimeoutSelect(displayActivityWindow){
		if( displayActivityWindow == 'true' ){
			document.detailsForm.activityTimeoutPeriod.disabled = false;
		} else {
			document.detailsForm.activityTimeoutPeriod.disabled = true;
		}
	}

	function processApplianceAdd() {
		// Retrieve Selected Appliance
		var selectedIndex = document.detailsForm.applianceList.selectedIndex;
		var selectedApp = document.detailsForm.applianceList.options[selectedIndex].text;

		// Add appliance to Associated List
		addOption(document.detailsForm.associatedList, selectedApp, selectedApp);

		// Remove appliance from Available List
		removeOption(document.detailsForm.applianceList);
	}
	
	function processApplianceRemove() {
		// Retrieve Selected Associated Appliance
		var selectedIndex = document.detailsForm.associatedList.selectedIndex;
		var selectedApp = document.detailsForm.associatedList.options[selectedIndex].text;

		// Add appliance to Available List
		addOption(document.detailsForm.applianceList, selectedApp, selectedApp);

		// Remove appliance from Associated List
		removeOption(document.detailsForm.associatedList);
	}

	function removeOption(selectbox)
	{
		var i;
		for(i=selectbox.options.length-1;i>=0;i--)
		{
			if(selectbox.options[i].selected)
				selectbox.remove(i);
		}
	}

	function addOption(selectbox,text,value )
	{
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
	}

	function preprocessForm(){
		document.detailsForm.top.value = document.getElementById("<%= deviceName %>").style.top;
		document.detailsForm.left.value = document.getElementById("<%= deviceName %>").style.left;

		var options = document.detailsForm.associatedList.options;
		for(i=0; i<options.length; i++){
			document.detailsForm.associatedAppliances.value += (options[i].value + ",");
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
		Modify Motion Sensor
	</span> 
	<br />
	<div>
		<span style="padding: 20px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: small; color: #FF0000">
			<%= statusMessage %>
		</span>
	</div>
	<br />
	<div id="addForm">
		<form name="detailsForm" id="detailsForm" method="post" action="/easyx10/EasyX10AppServlet?action=MODIFY_DEVICE&deviceType=MOTION">
			<table id="detailsFormTable">
				<tr>
					<td>
						<span>Motion Sensor Name: </span>
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
						<select name="deviceStatus" tabindex="4" disabled="disabled">
						<option value="ON" 
							<%= device.getState().equals(X10Device.X10DeviceState.ON) ? "selected=\"selected\"" : "" %>>MOTION</option>
						<option value="OFF" 
							<%= device.getState().equals(X10Device.X10DeviceState.OFF) ? "selected=\"selected\"" : "" %>>STILL</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Activity Window:</span>
					</td>
					<td>
						<select name="activityWindow" onchange="processActivityWindowSelect(this.value);" tabindex="5">
						<option value="true"
							<%= (device.getDetectionPeriodEnabled() == true) ? "selected=\"selected\"" : "" %>>ON</option>
						<option value="false" 
							<%= (device.getDetectionPeriodEnabled() == false) ? "selected=\"selected\"" : "" %>>OFF</option>
						</select>
					</td>
					<td>
						<span>Start Time:</span>
					    <select name="startTime" <%= device.getInactivityTimeEnabled() ? "" : "disabled=\"disabled\"" %> tabindex=6">
					    	<%= GuiUtilities.generateHtmlTimeOptions(GuiUtilities.convertCalendarToString(device.getStartTime())) %>
					    </select>
						<span>End Time:</span>
						<select name="endTime" <%= device.getInactivityTimeEnabled() ? "" : "disabled=\"disabled\"" %> tabindex="7">
					    	<%= GuiUtilities.generateHtmlTimeOptions(GuiUtilities.convertCalendarToString(device.getEndTime())) %>
					    </select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Inactivity Timeout:</span>
					</td>
					<td>
						<select name="activityTimeout" onchange="processActivityTimeoutSelect(this.value);" tabindex="5">
						<option value="true"
							<%= (device.getInactivityTimeEnabled() == true) ? "selected=\"selected\"" : "" %>>ON</option>
						<option value="false"
							<%= (device.getInactivityTimeEnabled() == false) ? "selected=\"selected\"" : "" %>>OFF</option>
						</select>
					</td>
					<td>
						<span>Timeout Period:</span>
						<select id="activityTimeoutPeriod" name="activityTimeoutPeriod" 
							<%= device.getInactivityTimeEnabled() ? "" : "disabled=\"disabled\"" %>>
                        	<option value="30" <%= (device.getInactivityTime() == 30) ? "selected=\"selected\"" : "" %>>30 Seconds</option>
                            <option value="60" <%= (device.getInactivityTime() == 60) ? "selected=\"selected\"" : "" %>>1 min</option>
                            <option value="120" <%= (device.getInactivityTime() == 120) ? "selected=\"selected\"" : "" %>>2 min</option>
                            <option value="300" <%= (device.getInactivityTime() == 300) ? "selected=\"selected\"" : "" %>>5 min</option>
                            <option value="600" <%= (device.getInactivityTime() == 600) ? "selected=\"selected\"" : "" %>>10 min</option>
                            <option value="1800" <%= (device.getInactivityTime() == 1800) ? "selected=\"selected\"" : "" %>>30 min</option>
                            <option value="3600" <%= (device.getInactivityTime() == 3600) ? "selected=\"selected\"" : "" %>>60 min</option>
                       </select>	
					</td>	
				</tr>
				<tr>
					<td colspan="2">
						<br />
						<fieldset>
							<table>
								<tr>
               						<td align="center">
               							<span style="font-weight:bold">Available Appliances</span>
               							<br/>
               							<select name="applianceList" size="5" style="width: 150px">
               							<% 
               								for( X10Device d : devices ){
               									if( (d instanceof ProxyX10Appliance) && 
               											!device.getApplianceList().contains(d.getName()) ){
               							%>
               										<option value="<%=d.getName() %>"><%=d.getName() %></option>
               							<% 
               									}
               								} 
               							%>

                						</select>
                					</td>                                        
                					<td align="center">
                						<input name="AddAppButton" type="button" value="Add -&gt;" onclick="processApplianceAdd();" />
                						<br/>                                                
                						<input name="RemoveAppButton" type="button" value="&lt;- Remove" onclick="processApplianceRemove();" />
               						</td>
               						<td align="center">                                                
               							<span style="font-weight:bold">Associated Appliances</span>
               							<br/>
               							<select name="associatedList" size="5" style="width: 150px">
               							<% 
               								for( String appliance : device.getApplianceList() ){
               							%>
               									<option value="<%=appliance %>"><%=appliance %></option>
               							<%
               								}
               							%>
               							</select>
               						</td>
              					</tr>
               				</table>
               			</fieldset>
                	</td>
                </tr>
				<tr>
					<td colspan="3">
						<br />
						<input name="update" type="submit" value="update" tabindex="8" onclick="preprocessForm()" />
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
			<input name="associatedAppliances" type="hidden" value="" />
		</form>
	</div>
		<% 
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
<div id="footer" >
	<div style="text-align: right; margin-right: 10px">
		<span>Created for CS673 - Boston University <br />
		<br />
		<a href="http://code.google.com/p/easyx10" target="_blank">EasyX10 Google 
		Code Project</a> </span></div>
</div>

</body>

</html>
