<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="oAuth">
	<head>
		<title>oAuth Security</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<link href="../assets/css/styles.css" rel="stylesheet">		
		<script src="../assets/js/plugins/jquery/jquery-1.8.2.min.js"></script>
		<script src="../assets/js/plugins/angular/angular.min.js"></script>
		<script src="../assets/js/plugins/angular/angular-ui-router.js"></script>
	</head>
	<body ng-controller="DashboardCtrl">
		<div>
		    <div class="section" style="width: 97.5%; margin: 5px;">
		        <h4 id="name">&nbsp;</h4><a href="#" ng-click="logout()" style="position: fixed; top: 20px; right: 30px">Logout</a>
		    </div>
		    <div class="section" style="width: 20%; margin: 5px;">
		        <ul>
		            <li><a ng-click="userList()">User List</a></li>
		            <li><a ng-click="user('surendra.nmc@gmail.com')">Surendra Singh</a></li>
		            <li><a ng-click="user('vsingh.iimt@gmail.com')">Vandana Singh</a></li>
		        </ul>
		    </div>
		</div>
        <div ui-view></div>
        <script type="text/javascript" charset="utf-8">
            var oAuth = angular.module('oAuth', ['ui.router']);
        </script>
        <script src="../assets/js/config/config.js"></script>
        <script src="../assets/js/controller/dashboard-controller.js"></script>
        <script src="../assets/js/controller/list-controller.js"></script>
        <script src="../assets/js/controller/grid-controller.js"></script>
        <script src="../assets/js/service/oauth-rest-service.js"></script>
    </body>
</html>