/**
 * @author surendra.singh
 */
angular.module('oAuth').controller('DashboardCtrl', ['$state', '$scope', '$window', 'RestService', function ($state, $scope, $window, RestService) {
	
	$scope.logout = function () {
		$window.localStorage.removeItem('authToken');
		$window.localStorage.removeItem('refreshToken');
		$window.location.href = "../user/login";
	};
	
	if (!!$window.localStorage.getItem('authToken')) {
		$state.go('listView', {}, {reload: true});
		RestService.post('../v1.0/me',	{}, 'GET',
			function(response) {
				var name = response.name || '';
				$('#name').html((name != ' ' ? name : response.emailAddress));
			}, function(response) {
				if (response && response.status === 401) {
					$scope.logout();
	            }
			}
		);
	} else {
		$scope.logout();
	}
	
	$scope.userList = function () {
		RestService.post('../v1.0/userList', {}, 'GET',
			function(response) {
				var jsonObj = JSON.parse(JSON.stringify(response));
				$('#data').html(JSON.stringify(jsonObj, null, '\t'));
			}, function(response) {
				if (response && response.status === 401) {
					$scope.logout();
	            }
			}
		);
	};
	
	$scope.user = function (email) {
		RestService.post('../v1.0/user/' + email, {}, 'GET',
			function(response) {
				var jsonObj = JSON.parse(JSON.stringify(response));
				$('#data').html(JSON.stringify(jsonObj, null, '\t'));
			}, function(response) {
				if (response && response.status === 401) {
					$scope.logout();
	            }
			}
		);
	};
}]);