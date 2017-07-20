(function(angular) {
	'use strict';

	function viewFactController($scope, $http, $rootScope, FactService) {

		
		//	FactService.post($scope.fact, $scope.file);

			$http.get('/fact/getAllFacts').then(
					function(response) {

						$scope.facts = JSON.parse(JSON
								.stringify(response.data.messages));

					});

		}

	

	viewFactController.$inject = [ '$scope', '$http', '$rootScope', 'FactService' ];

	angular.module('governProject.fact').controller('viewFactController',
			viewFactController);

})(window.angular);