(function(angular) {
	'use strict';

	function FactController($scope, $http, $interval, $rootScope, $routeParams,
			FactService) {

		$scope.fact = FactService.fact;
		$scope.fact.user = $rootScope.sessionUser;
		$scope.fact.parentId = $routeParams.componentId;

		$scope.submit
		= function() {

			FactService.post($scope.fact, $scope.file);

			if (isValid) {
				if (typeof ($scope.file) != "undefined") {
					if ($scope.file.size < 1000000) {
						FactService.post($scope.fact, $scope.file);
					} else {
						alert('Image should be less than 1 MB');
					}
				} else {
					alert('Image is not selected');
				}
			} else {
				alert('Validation fail');
			}

		}

	}
	;

	FactController.$inject = [ '$scope', '$http', '$interval', '$rootScope',
			'$routeParams', 'FactService' ];

	angular.module('governProject.fact').controller('FactController',
			FactController);

})(window.angular);