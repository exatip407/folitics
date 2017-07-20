(function(angular) {

	'use strict';

	function PollViewController($http, $scope, $routeParams, PollService) {

		alert($routeParams.pollId);
	
		$http.get('/poll/find?id=' + $routeParams.pollId).then(function(response) {

			if (response.data.messages != null) {
				$scope.viewpoll = response.data.messages[0];
			} else {
				
			}
		});


	}

	PollViewController.$inject = [ '$http', '$scope', '$routeParams',
			'PollService' ];

	angular.module('governProject.poll').controller('PollViewController',
			PollViewController);

})(window.angular);