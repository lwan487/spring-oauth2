<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="oAuth">
    <head>
        <title>oAuth Home</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script src="assets/js/plugins/jquery/jquery-1.8.2.min.js"></script>
        <script src="assets/js/plugins/angular/angular.min.js"></script>
    </head>
    <body ng-controller="oAuthCtrl">
        <form action="v1.0/dashboard" id="form" method="POST">
        	<input type="hidden" name="access_token" id="token"/>
        </form>
        <script type="text/javascript" charset="utf-8">
            angular.module('oAuth', []).controller('oAuthCtrl', ['$window', 'RestService', function($window, RestService) {
            	RestService.post('v1.0/me',	{}, 'GET',
           			function(response) {
	            		$("#token").val($window.localStorage.getItem('authToken'));
	                    $("#form").submit();
           			}, function(response) {
           				if (response && response.status === 401) {
           					$window.location.href = "user/login";
           	            }
           			}
           		);
            }]);
        </script>
        <script src="assets/js/service/oauth-rest-service.js"></script>
    </body>
</html>