﻿(function(angular) {

	'use strict';

	function HomeController($http, $scope, $rootScope, SentimentService,
			userService, VerdictService, ProblemService) {
				$rootScope.id = 0,

				$scope.newsFlash = "Gulberg massacre verdict: 11 awarded life, 12 others to be jailed";

		/*----------------- start personality scope--------------*/
		$scope.updateNotificationStatus = function(){
			
		};
				
				
		$scope.goLeft = function(e) {
			var cruntPagination = e.target.id;
			alert(cruntPagination);
		};
		$scope.goRight = function(e) {
			var cruntPagination = e.target.id;
			alert(cruntPagination);
		};

		/*----------------- start  trends  scope--------------*/

		$scope.trendsList = [ {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		}, {
			"trendName" : "KashmirSiege",
			"trendColun" : "112K People talking about it"
		} ];

		/*----------------- start  current Sentiment --------------*/

		/*
		 * [{ "imgUrl": "images/01/170x98.jpg", "title": "JNU", "id": "1",
		 * "class": "greeBG" },{ "imgUrl": "images/01/170x98.jpg", "title":
		 * "JNU", "id": "1", "class": "orangeBG" },{ "imgUrl":
		 * "images/01/170x98.jpg", "title": "JNU", "id": "1", "class":
		 * "orangeBG" }];
		 */
		SentimentService.findByTypePermanent().then(function(response) {
			console.log('data after findbytypepermanent' + response);

			$scope.permanentSentiment = response;
		});
		
$scope.test = function(id) {
			
			
			$( ".show_onclick" ).show();
			  $( ".narrow_show_onclick" ).show();
			  $( ".how_to_play" ).hide();
			  $( "#carousel-sentiment .item ul li .box_sentiment" ).removeClass('active');
			  $(this).parent(".box_sentiment").addClass('active');
			  $( ".collapse_sec" ).hide();
			  $( ".top_sec_govt" ).hide();
			  
				$http.get('/sentiment/find?id='+id).then(function(response) {

					$scope.sentiment = response.data.messages[0];
					console.log("data is =");
					console.log($scope.sentiment);

				});
			  
			}
		
		
		SentimentService.findByTypeTemporary().then(function(response) {
			console.log('data after findbytypetemporary' + response);

			$scope.temporarySentiment = response;
		});
		/*
		 * SentimentService.getAll().then(function(response) {
		 * $scope.currentSentiment = response; $scope.permanentSentiment =
		 * response; });
		 */
		/*
		 * if (typeof $rootScope.sessionUser !== "undefined" &&
		 * $rootScope.sessionUser){ var userId=$rootScope.sessionUser.id;
		 * userService.getUser(userId).then(function(response){
		 * $scope.personality=response; $scope.personality = [ { "imageUrl" :
		 * response.userImage.image, "userName" : response.username, "emailId" :
		 * response.emailId, "badge" :response.badge, "points" :response.points,
		 * "pagination" : "1", "feedList" : [ { "feed" : "" }, { "feed" : " " }, {
		 * "feed" : "" }, { "feed" : "" }, { "feed" : "" } ] } ];
		 * 
		 * }); }
		 */
		/*----------------- start  Permanent  Sentiment --------------*/

		/*
		 * $scope.permanentSentiment = [ { "imgUrl" : "images/01/170x98.jpg",
		 * "title" : "JNU", "id" : "1", "class" : "greeBG" }, { "imgUrl" :
		 * "images/01/170x98.jpg", "title" : "JNU", "id" : "1", "class" :
		 * "orangeBG" }, { "imgUrl" : "images/01/170x98.jpg", "title" : "JNU",
		 * "id" : "1", "class" : "orangeBG" } ];
		 */

		/*----------------- start  verdict --------------*/

		VerdictService.getNVerdicts().then(function(response) {

			$scope.headlines = response;
			
			
		});

		/*
		 * $scope.verdictList = [{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "greeBG" },{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "orangeBG" },{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "greeBG" },{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "orangeBG" },{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "greeBG" },{ "title": "Johnson & Johnson hit with $502M
		 * verdict for failed hip implants", "date": "16 April 2016", "id": "1",
		 * "class": "greeBG" }];
		 */

		/*----------------- start  task  --------------*/

		ProblemService.getTask().then(function(response) {

			/*
			 * alert(response.data.length); $scope.id = response.data.length;
			 */
			$scope.taskList = response;

		});

		/*
		 * $scope .$watch( 'currentPage + numPerPage', function() { var begin =
		 * (($scope.currentPage - 1) * $scope.numPerPage), end = begin +
		 * $scope.numPerPage;
		 * 
		 * $scope.taskLists = $scope.taskList .slice(0, 3); });
		 */
		/*
		 * $scope.taskList = [{ "sort": "AJ", "name": "Ashwin", "id": "1",
		 * "text": "commented on your post", "class": "G" },{ "sort": "AJ",
		 * "name": "Ashwin", "id": "1", "text": "commented on your post",
		 * "class": "R" },{ "sort": "AJ", "name": "Ashwin", "id": "1", "text":
		 * "commented on your post", "class": "B" },{ "sort": "AJ", "name":
		 * "Ashwin", "id": "1", "text": "commented on your post", "class": "LB"
		 * }];
		 */

	}

	HomeController.$inject = [ '$http', '$scope', '$rootScope',
			'SentimentService', 'userService', 'VerdictService',
			'ProblemService' ];

	angular.module('governProject.home').controller('HomeController',
			HomeController);

})(window.angular);