(function(angular) {

	'use strict';

	function PermanentVerdictController($http, $scope, $routeParams, VerdictService,
	SentimentService) {
		$scope.getPrerequisite = function() {

			VerdictService.getOverAllVerdict($routeParams.sentimentId).then(
			function(response) {

				$scope.overAllVerdict = {
					'anti' : response[0].yAxisValue,
					'pro' : response[1].yAxisValue
				};
			});
			VerdictService.getVerdictHeadline($routeParams.sentimentId).then(
			function(response) {

				$scope.headline = response;
			});
			
			SentimentService.findById($routeParams.sentimentId).then(
			function(sentiment) {

				$scope.sentiment = sentiment;
				for(var i=0;$scope.sentiment.relatedSentiments.length >i;i++){
					SentimentService.getSentimentSupport($scope.sentiment.relatedSentiments[i].id,i).then(
					function(response) {
						$scope.sentiment.relatedSentiments[response.id].color= 'btn btn-default btn-'+response[0];
						console.log(response);
					});
				};
				/*
				 * for(var i=0;i<$scope.sentiment.polls.length;i++){
				 * $scope.getPollResult($scope.sentiment.polls[i]); };
				 */
			});

		}
	}

	PermanentVerdictController.$inject = [ '$http', '$scope', '$routeParams',
			'VerdictService', 'SentimentService' ];

	angular.module('governProject.verdict').controller('PermanentVerdictController', PermanentVerdictController);

})(window.angular);