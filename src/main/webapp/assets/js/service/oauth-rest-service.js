/**
 * @author surendra.singh
 */
angular.module('oAuth').factory('RestService', ['$window', function($window) {
	var factory = {};

	factory.post = function(url, data, method, success, error) {
		$.ajax({
    		url : url,
			data : data,
			type : method,
			accept : "application/json",
			dataType : "json",
			headers: {
		        "Authorization": "Bearer " + $window.localStorage.getItem('authToken')
		    },
			success : success,
			error : error
		});
	};
	
	factory.login = function(user, success, error) {
		var data = {
			'client_id': '353b302c44574f',
			'client_secret': '286924697e615a6',
			'grant_type': 'password'
		};
		angular.extend(data, user);
		this.post('../oauth/token', data, 'POST', function (response) {
			$window.localStorage.setItem('authToken', response.access_token);
			$window.localStorage.setItem('refreshToken', response.refresh_token);
			if (angular.isFunction(success)) {
				success(response);
			}
		}, error);
	};
	
	factory.logout = function (success) {
		$window.localStorage.removeItem('authToken');
		$window.localStorage.removeItem('refreshToken');
		if (angular.isFunction(success)) {
			success();
		}
	}
	
	return factory;
}]);