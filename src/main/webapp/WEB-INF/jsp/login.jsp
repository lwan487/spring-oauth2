<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="oAuth">
	<head>
		<title>oAuth Web Portal</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<link href="../assets/css/styles.css" rel="stylesheet">		
		<script src="../assets/js/plugins/jquery/jquery-1.8.2.min.js"></script>
		<script src="../assets/js/plugins/angular/angular.min.js"></script>
	</head>
	<body ng-controller="oAuthCtrl">
		<form novalidate class="css-form" name="loginForm" id="sinup">
		    <div id="container">
		        <input id="email" type="email" placeholder="Email" ng-model="user.username" ng-required="true">
		        <input id="password" type="password" placeholder="Password" ng-model="user.password" ng-required="true">
		        <div id="error_message">&nbsp;</div>
		        <button class="small_button" type="button" id="login_button" ng-click="login()">Login</button>
		        <button type="button" id="signup_button" class="small_button" ng-click="signup()">Sign Up</button>
		    </div>
		</form>
		<form action="../v1.0/dashboard" id="form" style="display: none;" method="POST">
			<input type="hidden" name="access_token" id="token"/>
		</form>
		<script>
			angular.module('oAuth', []).controller('oAuthCtrl', ['$window', '$scope', 'RestService', function ($window, $scope, RestService) {
				$scope.user = {};
				$scope.login = function() {
					RestService.login($scope.user,
						function(response) {
							$("#token").val(response.access_token);
		                    $("#form").submit();
						}, function(response) {
							$('#error_message').html('Email and/or password did not match a user account.').show();
						}
					);
				};
				$scope.signup = function () {
					$window.location.href = "../user/signup";
				}
			}]);
		</script>
		<script src="../assets/js/service/oauth-rest-service.js"></script>	
	</body>
</html>