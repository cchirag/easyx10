<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="edu.bu.easyx10.gui.GuiUtilities" %>
<%@ page import="edu.bu.easyx10.device.*" %>
<%@ page import="java.util.List" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EasyX10 - Add Appliance</title>
<link rel="stylesheet" type="text/css" href="easyx10.css" />
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
</head>

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
	Add New Appliance</span> <br />
	<br />
	<span id="status" style="float: left; padding: 20px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: small; color: #FF0000">
	</span>
	<div id="addForm">
		<!-- span id="addFormMessage" style="font-family: Arial, Helvetica, sans-serif; font-size: large; font-weight: bold; font-variant: normal">
		Enter Details for New Item:</span> <br / -->
		<form name="detailsForm" id="detailsForm" method="post" action="/easyx10/EasyX10AppServlet?action=ADD_DEVICE">
			<table id="detailsFormTable">
				<tr>
					<td>
						<span>Appliance Name: </span>
					</td>
					<td colspan="2">
						<input name="applianceName"  type="text" size="25" maxlength="25" tabindex="1" />
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
						<select name="houseCode" tabindex="3">
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
						<select name="itemStatus" tabindex="4">
						<option value="ON">ON</option>
						<option value="OFF">OFF</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<span>Timer:</span>
					</td>
					<td>
						<select name="timer" onchange="processTimerSelect(this.value);" tabindex="5">
						<option value="ON">ON</option>
						<option value="OFF" selected="selected">OFF</option>
						</select>
					</td>
					<td>
						<span>Start Time:</span>
					</td>
					<td>
					    <select name="startTime" disabled="disabled" tabindex=6">
					    	<%= GuiUtilities.generateHtmlTimeOptions() %>
					    </select>
						<span>End Time:</span>
						<select name="endTime" disabled="disabled" tabindex="7">
					    	<%= GuiUtilities.generateHtmlTimeOptions() %>
					    </select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<br />
						<input name="AddButton" type="submit" value="Add" tabindex="8" />
						&nbsp;
						<input name="Reset1" type="reset" value="Reset" tabindex="9"/>
						&nbsp;
						<input name="CancelButton" type="button" value="Cancel" onclick="window.location='Status.jsp';" tabindex="10" />
					</td>
				</tr>
			</table>
		</form>
	</div>
		<% 
			
			List<X10Device> devices = (List<X10Device>)session.getAttribute("deviceList");
			int floorNumber = 1;
			String tempFloor = (String)request.getParameter("selectedFloor");
			if( tempFloor != null ) {
				floorNumber = Integer.parseInt(tempFloor);
			}
		%>
	
		<div id="<%= "floor" + floorNumber  %>" style="position: relative; width : 800px; 
				height: 400px; border: thin black solid; margin: 20px 20px 20px 20px; float: left">
			
			
			<span><%= "Floor " + floorNumber %></span>
			
			<% 
				for(int j=0; j< devices.size(); j++) { 
					if( devices.get(j).getLocation().getFloorNumber() == floorNumber ){
			%>
			<div style="position: absolute; height: 40px; width: 40px; top: 
				<%= devices.get(j).getLocation().getY() %>px; left: <%= devices.get(j).getLocation().getY() %>px; background-color: 
				<%= (devices.get(j).getState().equals(X10Device.X10DeviceState.ON) ? "lightgreen" : "yellow") %>; border: 1px black solid; text-align:center">
				
				<span style="font-size:x-small"><a href="Room.htm"><%= devices.get(j).getName() %></a></span>
			</div>
			<%
					}
				}
			%>
			<div id="newAppliance" style="z-index: 2; position: absolute; height: 40px; width: 40px; top: 50px; left: 50px; background-color: yellow; border: 1px black solid; text-align: center">
			</div>
			<div class="transparent" style="position: absolute; top: 0px; left: 0px; z-index: 1; background-color: gray; height: 400px; width: 800px;">
			</div>
		</div>
	<script type="text/javascript">
		new Draggable('newAppliance',{snap: constrainSnap} );
    
    	function constrainSnap(x, y){
    		return[ (x < 760) ? (x > 0 ? x : 0 ) : 760,
                    (y < 360) ? (y > 0 ? y : 0) : 360 ];
    	}	
  	</script>
</div>
<div id="footer" style="top: 200px">
	<div style="text-align: right; margin-right: 10px">
		<span>Created for CS673 - Boston University <br />
		<br />
		<a href="http://code.google.com/p/easyx10" target="_blank">EasyX10 Google 
		Code Project</a> </span></div>
</div>

</body>

</html>
