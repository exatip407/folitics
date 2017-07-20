(function(angular) {

	'use strict';

	function gaHeaderController($http, $scope, gaHeaderService, CategoryService) {

		var fooling ="";
		var unsatisfactory ="";
		var onTrack ="";
		var bestPerform ="";
		var data=[];
		var foolingIndicatorList = [];
		var unsatisfactoryIndicatorList = [];
		var onTrackIndicatorList = [];
		var performIndicatorList = [];
		
		$scope.getPrerequisite = function() {

			CategoryService.getAllIndicator().then(function(indicators) {

				$scope.indicators = indicators;
			});
		};
		
		gaHeaderService.getData().then(function(response){
			$scope.govtSchemeData = response;
		});

		$http.get('/indicatordata/getHeaderData').then(function(response) {

			// alert("GA Header");
			console.log('Inside');
			var data = JSON.parse(JSON.stringify(response.data.messages));
			
			 for(var i=0;i<4;i++){
				if(data[i].thresholdCategory === "Fooling US"){
					$scope.fooling = data[i];
				$scope.foolingIndicatorList = data[i].indicatorDataList;}
				
				if(data[i].thresholdCategory === "Best Performance"){
					$scope.bestPerform = data[i];
				$scope.performIndicatorList = data[i].indicatorDataList;}	
				
				if(data[i].thresholdCategory === "UnSatisfactory"){
					$scope.unsatisfactory = data[i];
				$scope.unsatisfactoryIndicatorList = data[i].indicatorDataList;}
				
				if(data[i].thresholdCategory === "On Track"){
					$scope.onTrack = data[i];
				$scope.onTrackIndicatorList = data[i].indicatorDataList;}
			}

		});
		/*$scope.indicators = [ {
			"id" : 6,
			"name" : "Inflation"
		}, {
			"id" : 7,
			"name" : "WPI"
		}, {
			"id" : 8,
			"name" : "Average Income"
		} ]*/
	}

	gaHeaderController.$inject = [ '$http', '$scope', 'gaHeaderService', 'CategoryService' ];

	angular.module('governProject.gaHeader').controller('gaHeaderController',
	gaHeaderController);

})(window.angular);