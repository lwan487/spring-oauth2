/**
 * @author surendra.singh
 */
angular.module('oAuth').config(['$stateProvider', function ($stateProvider) {
	$stateProvider.state('listView', {
		url : "/listView",
		templateUrl: "../assets/template/list.html",
		controller: 'ListCtrl'
	}).state('gridView', {
		url : "/gridView",
		templateUrl: "../assets/template/grid.html",
		controller: 'GridCtrl'
	});
}]);