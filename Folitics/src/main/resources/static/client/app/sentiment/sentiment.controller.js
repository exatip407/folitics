﻿(function(angular) {

	'use strict';

	function SentimentController($http, $scope, $rootScope, $location, SentimentService, PollService) {

		$scope.relatedSentimentTags = [];
		$scope.categoriesTags = [];

		$scope.reload = function() {

			$location.path("");

		}

		$http.get('/sentiment/getAll').then(
				function(response) {
					// alert("hii");
					console.log('hello');
					$scope.sentiments = JSOG.parse(JSOG
							.stringify(response.data.messages));

				});

		$http.get('/category/getActiveSubCategories').then(
				function(response) {

					$scope.categories = JSOG.parse(JSOG
							.stringify(response.data.messages));

				});

		
		  $scope.loadSentiments = function($query) {
		  
		  return $scope.sentiments; };
		  
		  $scope.loadloadCategories = function($query) {
		  
		  return $scope.categories; };
		 

		var dt = new Date();
		dt.setDate(dt.getDate() - 1);
		$scope.todayDate = dt;

		$scope.maxlength = 255;

		$scope.sentiment = SentimentService.cloneSentiment;
		
		$scope.sentiment.created_By = $rootScope.sessionUser.id;
		PollService.getPolls().then(function(response) {

			$scope.polls = response.data.messages;
		});

		$scope.sentimentTypes = [ {
			name : 'Temporary'
		}, {
			name : 'Permanent'
		} ];

		$scope.submit = function(isValid) {

			if (isValid) {
				if (typeof ($scope.file) != "undefined") {
					if ($scope.file.size < 1000000) {
						$scope.sentiment.type = "Temporary";

						for (var i = 0; i < $scope.poll.length; i++) {

							$scope.sentiment.polls.push($scope.poll[i]);
							alert("poll ID : " + $scope.poll[i].id);
						}
						alert("polls : "
								+ SentimentService.cloneSentiment.polls);
						for (var i = 0; i < $scope.relatedSentimentTags.length; i++) {
							$scope.sentiment.relatedSentiments
									.push($scope.relatedSentimentTags[i]);
						}
						for (var i = 0; i < $scope.categoriesTags.length; i++) {
							$scope.sentiment.categories
									.push($scope.categoriesTags[i]);
						}

						SentimentService.post($scope.sentiment, $scope.file);
					} else {
						alert('Image should be less than 1 MB');
					}
				} else {
					alert('Image is not selected');
				}

			} else {
				alert('Validation fail');
			}
		};

	}

	SentimentController.$inject = [ '$http', '$scope','$rootScope', '$location','SentimentService', 'PollService' ];

	angular.module('governProject.sentiment', [ 'ngTagsInput' ]).controller(
			'SentimentController', SentimentController);

})(window.angular);