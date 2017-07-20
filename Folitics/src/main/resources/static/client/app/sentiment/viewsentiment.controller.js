(function(angular) {

	'use strict';

	function ViewSentimentController($http, $scope, SentimentService) {
		
	/*	$http.get('/sentiment/getAll').then(function(response) {
       // alert(response.data.messages[0] );
		if (response.data.messages != null) {
				$scope.sentiments = response.data.messages;
		}	});*/
		
		
		$scope.submitPoll = function(){
			alert("inside");
		/*SentimentService.postPoll($scope.pollAnswer);*/
		};
		
		$http.get('/sentiment/findByType?type=temporary').then(function(response) {
			if (response.data.messages != null) {
				
				$scope.temporarySentiments = response.data.messages;
			}	});		
		
       $http.get('/sentiment/findByType?type=permanent').then(function(response) {
					if (response.data.messages != null) {
							$scope.permanentSentiments = response.data.messages;
					}	});

	}

	ViewSentimentController.$inject = [ '$http', '$scope','SentimentService' ];

	angular.module('governProject.sentiment').controller('ViewSentimentController', ViewSentimentController);

})(window.angular);