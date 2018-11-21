<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="../../../css/profile/retailer/retailerProfile.css">

<script src="../../../js/jquery-2.2.4.min.js"></script>
<!-- <script src="js/jquery.mobile-1.4.5.min.js"></script>  -->
<script src="../../../js/profile/retailer/retailerProfile.js"></script>
<title>Retailer Profile</title>
</head>
<body>

<h1 class="form-title" align="center">Welcome <%= session.getAttribute("username") %> </h1>
<p align="right"> <button class="button" form="form2">logout</button></p>
<div class="tab">
  <button class="tablinks" onclick="openCity(event, 'Account Details')" id="defaultOpen">Account Details</button>
  <button class="tablinks" onclick="openCity(event, 'View Product Catalogue')">View Product Catalogue</button>
  <button class="tablinks" onclick="openCity(event, 'Manage Contracts')">Manage Contracts</button>
  <button class="tablinks" onclick="openCity(event, 'Change Password')">Change Password</button>
</div>


<div id="Account Details" class="tabcontent">
		<h3>Account Details</h3>
	    <p>User Name : <input type="text" id="username"></p><br/>
		<p>User ID : <input type="text" id="ID"></p><br/>
		<p>Address:<input type="text" id="address"></p><br/>
		<p>Contact Number:<input type="text" id="tel"></p><br/>
		<p>Spam:<input type="text" id="spam"></p><br/>
		
	</div>


<div id="View Product Catalogue" class="tabcontent">
  <h3>View Product Catalogue</h3>
        <p>Choose products from below drop down</p><select id="product-dropdown">
		 <option value="" disabled selected>---Select your option---</option></select>
</div>

<div id="Manage Contracts" class="tabcontent">
  <h3>Manage Contracts</h3>
 <form method="post" action="../../../RetailerProfileLoaderRequest" id="contractView">
	
		<select id="contract-dropdown"></select>		
		<input type="button" value="View Contract" class="login-button" id="submitViewContract" />
	
	</form>
</div>

<div id="Change Password" class="tabcontent">
		<h3>Change password</h3>
		<p>Enter old password:<input type="text" id="old password"></p><br/>
		<p>Enter new password:<input type="text" id="new password"></p><br/>
		<input type="button" value="Submit">
		
	</div>
	


<div id="Logout" class="tabcontent">
  <h3>Logout</h3>
  
  
</div>

  <form id="form2" action="${pageContext.request.contextPath}/" method="post">
  </form>
</body>
</html>