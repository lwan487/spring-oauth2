<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="oAuth">
	<head>
		<title>oAuth Signup</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<link href="../assets/css/styles.css" rel="stylesheet">		
		<script src="../assets/js/plugins/jquery/jquery-1.8.2.min.js"></script>
		<script src="../assets/js/plugins/angular/angular.min.js"></script>
	</head>
	<body ng-controller="oAuthCtrl">
		<form class="css-form" name="signupForm">
		    <div id="container">
		        <input id="email" type="email" placeholder="Email" ng-model="user.email">
		        <input type="password" id="password" placeholder="Password" ng-model="user.password">
		        <input type="password" id="password2" placeholder="Confirm Password">
		        <input type="text" id="firstName" placeholder="Full Name" ng-model="user.name">
		        <input type="checkbox" value="true" name="agree" id="agree">
		        <label for="agree" id="terms_label">I agree to the <b><a target="_blank">terms of service</a></b>.</label>
		        <br><br>
		        <div id="error_message"></div>
		        <button type="button" id="signup_button" class="small_button" ng-click="signup()">Sign Up</button>
		        <button type="button" id="cancel_button" class="small_button" ng-click="backToLogin()">Cancel</button>
		    </div>
		</form>
		<form action="../v1.0/dashboard" id="form" style="display: none;" method="POST">
			<input type="hidden" name="access_token" id="token"/>
		</form>
		<script>
			angular.module('oAuth', []).controller('oAuthCtrl', ['$window', '$scope', 'RestService', function ($window, $scope, RestService) {
				$scope.user = {};	
				$scope.backToLogin = function () {
					$window.location.href = "../user/login";
				};
				$scope.signup = function () {
					RestService.post('../user/createUser', $scope.user, 'POST',
						function(response) {
							var auth = { username : $scope.user.email, password : $scope.user.password };
							RestService.login(auth, function (response) {
								$("#token").val(response.access_token); $("#form").submit();
							});
						}, function(response) {
							$('#error_message').html('Error occured, try after some time.').show();
						}
					);
				}
			}]);
		</script>
		<script src="../assets/js/service/oauth-rest-service.js"></script>
	</body>
</html>