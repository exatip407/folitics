(function(angular) {

	'use strict';

	function VerdictController($http, $scope, Pagination, $routeParams, VerdictService,
	SentimentService, PollService) {

		$scope.sentimentId = $routeParams.sentimentId;
		$scope.pagination = Pagination.getNew();
		$scope.currentPage = 0;
		$scope.pageSize = 1;
		/* $scope.data = []; */
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

		}

		$scope.numberOfPages = function() {

			return Math.ceil($scope.sentiment.polls.length / $scope.pageSize);
		}
		$scope.getPollResult = function(poll, filter) {

			/* alert(" poll "+filter); */
			poll.by = filter;
			PollService.getPollResult(poll.id, 'poll' + filter + 'ResultService').then(
			function(response) {

				// poll.options[0].percentage=response[0];
				for (var i = 0; i < response.length; i++) {
					poll.options[i].percentage = response[i];

					// poll.options[i].percentage=response[i];
				}
				;
			});

		};

		$scope.test = function() {

			alert('sdf');
		};
		$scope.getSentiment = function() {

			SentimentService.findById($routeParams.sentimentId).then(
			function(sentiment) {

				$scope.sentiment = sentiment;
				console.log($scope.sentiment);
				/*
				 * for(var i=0;i<$scope.sentiment.polls.length;i++){
				 * $scope.getPollResult($scope.sentiment.polls[i]); };
				 */
			});

		};
		$scope.indicators = [ {
			'name' : 'Sex',
			value : 'verdictSexService'
		}, {
			'name' : 'Age',
			value : 'verdictAgeGroupService'
		}, {
			'name' : 'Marital status',
			value : 'verdictMaritalStatusService'
		}, {
			'name' : 'Region',
			value : 'verdictRegionService'
		}, {
			'name' : 'Religion',
			value : 'verdictReligionService'
		}, {
			'name' : 'Qualification',
			value : 'verdictQualificationService'
		} ]
		$scope.getVerdictHeadline = function() {

		}

	}

	VerdictController.$inject = [ '$http', '$scope', 'Pagination', '$routeParams',
			'VerdictService', 'SentimentService', 'PollService' ];

	angular.module('governProject.verdict').controller('VerdictController', VerdictController);

})(window.angular);