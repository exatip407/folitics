(function(angular) {

	'use strict';

	function ContestController($http, $scope, ContestService) {

	/*	$http.get('/luckyDraw/readAllActiveLuckyDraw').then(
				function(response) {
					$scope.luckyDraws  = JSON.parse(JSON
							.stringify(response.data.messages));
				});*/
		$http.get('/contest/readAllActiveContest').then(
				function(response) {

					$scope.activeContests = JSON.parse(JSON
							.stringify(response.data.messages));

				});
		
	}

	ContestController.$inject = [ '$http', '$scope', 'ContestService' ];

	angular.module('governProject.contest').controller('ContestController',
			ContestController);

})(window.angular);