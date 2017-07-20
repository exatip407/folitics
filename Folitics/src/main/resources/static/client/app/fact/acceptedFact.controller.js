(function(angular) {

	'use strict';

	function acceptedFactController($http, $scope, $routeParams, FactService) {

		
		$http.get('/fact/getFact?id='+ $routeParams.factId).then(
				function(response) {

					if (response.data.messages != null) {
						$scope.fact = response.data.messages[0];
					} else {

					}
				});
		
		$scope.approveFact = function() {
			$http.post(
					'/fact/approveFact?id='+ $routeParams.factId).then(function(response) {				
				console.log('button');
				if (response.data.messages != null) {
					$scope.acceptedFact = response.data.messages[0];
					//$scope.success=response.data.messages[0].responseMessage;
				}
			});

		}
		
		$scope.rejectFact = function() {
			$http.post(
					'/fact/rejectFact?factId='+ $routeParams.factId).then(function(response) {				
				console.log('button');
				if (response.data.messages != null) {
					$scope.acceptedFact = response.data.messages[0];
					//$scope.success=response.data.messages[0].responseMessage;
				}
			});

		}
	

	}

	acceptedFactController.$inject = [ '$http', '$scope', '$routeParams',
			'FactService' ];

	angular.module('governProject.fact').controller('acceptedFactController',
			acceptedFactController);

})(window.angular);