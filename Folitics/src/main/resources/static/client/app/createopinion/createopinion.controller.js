﻿(function(angular) {

	'use strict';

	function CreateopinionController($http, $scope, $rootScope,
			CreateOpinionService, $routeParams) {

		{
			$scope.anchor = {
				id : $routeParams.sentimentId
			};
			// alert("inside create opinion controller");

			$http.get('/sentiment/find?id=' + $routeParams.sentimentId1).then(
					function(response) {
						
						$scope.sentiment = response.data.messages;
						console.log($scope.sentiment);
						$scope.sentiment = JSOG
								.decode(response.data.messages[0]);
						$scope.anchor = {
							id : $scope.sentiment.id,
							subject : $scope.sentiment.subject,
							subcategories : $scope.sentiment.categories
						}
						

					});

			$scope.opinion = CreateOpinionService.opinion;
			alert("user id"+$rootScope.sessionUser.id);
			$scope.opinion.user = $rootScope.sessionUser;

			$scope.submit = function() {
				$scope.opinion.component = {
					id : $scope.anchor.id,
					componentType : "Sentiment"
				}
				$scope.opinion.sentiment = {
					id : $scope.anchor.id,
					componentType : "Sentiment"
				}
				var table = document.getElementById("createOpinionFormTable");
				CreateOpinionService.saveOpinion($scope.opinion, $scope.file);
			};
		}

	}

	CreateopinionController.$inject = [ '$http', '$scope', '$rootScope',
			'CreateOpinionService', '$routeParams' ];

	angular.module('governProject.createopinion').controller(
			'CreateopinionController', CreateopinionController);

})(window.angular);