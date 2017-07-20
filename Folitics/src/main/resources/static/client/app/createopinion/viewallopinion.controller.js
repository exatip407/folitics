(function(angular) {

	'use strict';

	function ViewallopinionController($http, $scope, CreateOpinionService) {

		$http.get('/opinion/getAll').then(function(response) {
			if (response.data.messages != null) {
				$scope.opinions = response.data.messages;
				console.log($scope.opinions);
			}
		});

	}

	ViewallopinionController.$inject = [ '$http', '$scope',
			'CreateOpinionService' ];

	angular.module('governProject.createopinion').controller(
			'ViewallopinionController', ViewallopinionController);

})(window.angular);