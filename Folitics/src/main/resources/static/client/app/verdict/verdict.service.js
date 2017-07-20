(function(angular) {

	'use strict';

	function VerdictService($http, $q) {
		this.getOverAllVerdict = function(sentimentId) {

			var promise = $http.get('/chart/getChart?ChartID=verdictChartService&ChartSecondaryID=verdictOverAllService&id='+sentimentId).then(
			function(response) {
				console.log(response);
				if (response.data != null) {
					return response.data.messages[0].verdictData;
				}
			});
			return promise;
		};

		this.getVerdict = function() {

			var promise = $http.get('/verdict/getVerdictHeadline?sentimentId=1').then(
			function(response) {

				if (response.data != null) {
					return response.data.responseMessage;

				}
			});
			return promise;
		};

		this.getNVerdicts = function() {

			var promise = $http.get(
			'/verdict/getVerdictHeadlineForNSentiments?noOfVerdictHeadlines=5').then(
			function(response) {

				if (response.data != null) {
					return response.data.messages;

				}
			});
			return promise;

		}
		this.getVerdictHeadline = function(sentimentId) {

			var promise = $http.get(
			'/verdict/getVerdictHeadline?sentimentId='+sentimentId).then(
			function(response) {

				if (response.data != null) {
					return response.data.responseMessage;

				}
			});
			return promise;

		}
	}

	VerdictService.$inject = [ '$http', '$q' ];

	angular.module('governProject.verdict').service('VerdictService', VerdictService);

})(window.angular);