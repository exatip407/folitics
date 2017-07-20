(function(angular) {

	'use strict';

	function ContestViewController($http, $scope, $routeParams, ContestService) {

		var data = {
			"luckyDrawId" : 2,
			"userId" : 1
		};
		
		$http.get('/contest/find?id=' + $routeParams.contestId).then(
				function(response) {

					if (response.data.messages != null) {
						$scope.contest = response.data.messages[0];
					} else {

					}
				});
		
		$scope.addParticipants = function() {
			$http.post(
					'/contest/addParticipants?luckyDrawId=2&userId=3').then(function(response) {
				alert('connection established');
				console.log('button');
				if (response.data.messages != null) {
					$scope.LuckyDraw = response.data.messages[0];
				}

			});

		}
	

	}

	ContestViewController.$inject = [ '$http', '$scope', '$routeParams',
			'ContestService' ];

	angular.module('governProject.contest').controller('ContestViewController',
			ContestViewController);

})(window.angular);