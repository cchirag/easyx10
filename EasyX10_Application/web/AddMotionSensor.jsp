<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.gui.GuiUtilities" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="java.util.List" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<% 	
	// Update and Retrieve the Device List
	GuiUtilities.updateSessionDeviceList(session);
	List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");
			
	// Retrieve the current floor
	String currentFloor = (String)request.getParameter("selectedFloor");
	if( currentFloor != null ){
		session.setAttribute("currentFloor", currentFloor);
	} else {
		currentFloor = (String)session.getAttribute("currentFloor");
	}	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EasyX10 - Add Motion Sensor</title>
<link rel="stylesheet" type="text/css" href="easyx10.css" />
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
</head>

<body onload="document.detailsForm.deviceName.focus()">

<script type="text/javascript">
	function processActivityWindowSelect(displayActivityWindow){
		if( displayActivityWindow == 'true' ){
			document.detailsForm.startHour.disabled = false;
			document.detailsForm.startMinute.disabled = false;
			document.detailsForm.startAmOrPm.disabled = false;
			document.detailsForm.endHour.disabled = false;
			document.detailsForm.endMinute.disabled = false;
			document.detailsForm.endAmOrPm.disabled = false;
		} else {
			document.detailsForm.startHour.disabled = true;
			document.detailsForm.startMinute.disabled = true;
			document.detailsForm.startAmOrPm.disabled = true;
			document.detailsForm.endHour.disabled = true;
			document.detailsForm.endMinute.disabled = true;
			document.detailsForm.endAmOrPm.disabled = true;
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
		document.detailsForm.top.value = document.getElementById("newMotionSensor").style.top;
		document.detailsForm.left.value = document.getElementById("newMotionSensor").style.left;
		processActivityWindowSelect("true");
		document.detailsForm.activityTimeoutPeriod = false;

		var options = document.detailsForm.associatedList.options;
		for(i=0; i<options.length; i++){
			document.detailsForm.associatedAppliances.value += (options[i].value + ",");
		}
	}
</script>
<div id="masthead">
	<img alt="EasxyX10 Logo" src="resources/EasyX10.gif" />
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
		Add New Motion Sensor
	</span> 
	<br />
	<div>
		<%
			// Retrieve the error message if one exists
			String statusMessage = (String)request.getAttribute( "statusMessage" );
			if( statusMessage == null ){
				statusMessage = "";
			}
		%>
		<span style="padding: 20px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: small; color: #FF0000">
			<%= statusMessage %>
		</span>
	</div>
	<br />
	<div id="addForm">
		<form name="detailsForm" id="detailsForm" method="post" action="/easyx10/EasyX10AppServlet?action=ADD_DEVICE&deviceType=MOTION">
			<table id="detailsFormTable">
				<tr>
					<td>
						<span>Motion Sensor Name: </span>
					</td>
					<td colspan="1">
						<input name="deviceName"  type="text" size="25" maxlength="25" tabindex="1" />
					</td>
					<td>
						<span>House Code:</span>
						<select name="houseCode" tabindex="2">
						<option>A</option>
						<option>B</option>
						<option>C</option>
						<option>D</option>
						<option>E</option>
						<option>F</option>
						<option>G</option>
						<option>H</option>
						<option>I</option>
						<option>J</option>
						<option>K</option>
						<option>L</option>
						<option>M</option>
						<option>N</option>
						<option>O</option>
						<option>P</option>
						</select> <span>Unit Code:</span>
						<select name="unitCode" tabindex="3">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
						<option>6</option>
						<option>7</option>
						<option>8</option>
						<option>9</option>
						<option>10</option>
						<option>11</option>
						<option>12</option>
						<option>13</option>
						<option>14</option>
						<option>15</option>
						<option>16</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Status:</span>
					</td>
					<td>
						<select name="deviceStatus" tabindex="4" disabled="disabled">
						<option value="ON">MOTION</option>
						<option value="OFF" selected="selected">STILL</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Activity Window:</span>
					</td>
					<td>
						<select name="activityWindow" onchange="processActivityWindowSelect(this.value);" tabindex="5">
						<option value="true">ON</option>
						<option value="false" selected="selected">OFF</option>
						</select>
					</td>
					<td>
						<span>Activity From:</span>
					    <%= GuiUtilities.generateHtmlTimeOptions("start", false, "05", "00", "pm") %>
					    <br /><br />
						<span>Activity To:&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<%= GuiUtilities.generateHtmlTimeOptions("end", false, "12", "00", "am") %>
					</td>
				</tr>
				<tr>
					<td>
						<br />
						<span>Inactivity Timeout:</span>
					</td>
					<td>
						<br />
						<select name="activityTimeout" onchange="processActivityTimeoutSelect(this.value);" tabindex="5">
						<option value="true">ON</option>
						<option value="false" selected="selected">OFF</option>
						</select>
					</td>
					<td>
						<br />
						<span>Timeout Period:</span>
						<select id="activityTimeoutPeriod" name="activityTimeoutPeriod" disabled="disabled">
                        	<option value="30">30 Seconds</option>
                            <option value="60">1 min</option>
                            <option value="120">2 min</option>
                            <option value="300">5 min</option>
                            <option value="600">10 min</option>
                            <option value="1800">30 min</option>
                            <option value="3600">60 min</option>
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
               								<%= GuiUtilities.generateHtmlApplianceOptions() %>
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
						<input name="AddButton" type="submit" value="Add" tabindex="8" onclick="preprocessForm()" />
						&nbsp;
						<input name="Reset1" type="reset" value="Reset" tabindex="9"/>
						&nbsp;
						<input name="CancelButton" type="button" value="Cancel" onclick="window.location='Status.jsp';" tabindex="10" />
					</td>
				</tr>
			</table>
			<input name="floorNumber" type="hidden" value="<%= currentFloor %>" />
			<input name="top" type="hidden" value="" />
			<input name="left" type="hidden" value="" />
			<input name="associatedAppliances" type="hidden" value="" />
		</form>
	</div>
	
		<div id="<%= currentFloor  %>" style="position: relative; width : 800px; background-image:url(resources/FloorPlan.jpg);
				height: 400px; border: thin black solid; margin: 20px 20px 20px 20px; float: left">
			
			
			<span style="top:20px; left: 700px; position: absolute"><%= currentFloor %></span>
			
			<% 
				for(int j=0; j< devices.size(); j++) { 
					if( ("floor" + devices.get(j).getLocation().getFloorNumber()).equals(currentFloor)){
			%>
			<div style="position: absolute; height: 40px; width: 40px; top: 
				<%= devices.get(j).getLocation().getY() %>px; left: <%= devices.get(j).getLocation().getX() %>px; background-color: 
				<%= (devices.get(j).getState().equals(X10Device.X10DeviceState.ON) ? "yellow" : "lightgray") %>; border: 1px black solid; text-align:center">
				
				<span style="font-size:x-small"><%= devices.get(j).getName() %></span>
			</div>
			<%
					}
				}
			%>
			<div id="newMotionSensor" style="z-index: 2; position: absolute; height: 40px; width: 40px; top: 50px; left: 50px; background-color: yellow; border: 1px black solid; text-align: center">
				<span style="font-size:x-small">New Motion Sensor</span>
			</div>
			<div class="transparent" style="position: absolute; top: 0px; left: 0px; z-index: 1; background-color: gray; height: 400px; width: 800px;">
			</div>
		</div>
	<script type="text/javascript">
		new Draggable('newMotionSensor',{snap: constrainSnap} );
    
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
