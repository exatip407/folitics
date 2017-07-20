(function(angular) {

	'use strict';

	function ResponseController($http, $scope, $rootScope, $routeParams,
			ResponseService) {
		$scope.response = {
			state : "Active",
			user : $rootScope.sessionUser,
			opinion : {
				id : $routeParams.opinionId
			},
			componentType : "sentiment"
		};

		$scope.submit = function() {
			console.log($scope.response);
			ResponseService.post($scope.response);
		}

	}
	ResponseController.$inject = [ '$http', '$scope', '$rootScope',
			'$routeParams', 'ResponseService' ];

	angular.module('governProject.response').controller('ResponseController',
			ResponseController);

})(window.angular);