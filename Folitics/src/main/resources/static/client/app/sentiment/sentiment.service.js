﻿(function(angular) {

	'use strict';

	function SentimentService($http, $q, $window ) {
		this.cloneSentiment = {
			subject : "",
			description : "",
			created_By : "",
			startTime : "",
			endTime : "",
			state : "",
			type : "",
			polls : [],
			categories : [],
			relatedSentiments : []

		};
		this.attachedRelatedSentimentIds = [];


		this.getAll = function() {

			var promise = $http.get('/sentiment/getAll').then(
					function(response) {

						if (response.data != null) {
							return response.data.messages;
						}
					});

			return promise;
		};
		
		this.getAllIndicatorForSentiment = function(sentimentId) {

			var promise = $http.get('/sentiment/getAllSentimentIndicator?id='+sentimentId).then(
					function(response) {

						if (response.data != null) {
							return response.data.messages[0];
						}
					});

			return promise;
		};
		
		
		this.findByTypePermanent = function() {

			var permanent = $http.get('/sentiment/findByType?type=Permanent').then(
					function(response) {

						if (response.data != null) {
							return response.data.messages;
						}
					});

			return permanent;
		};
		
		this.findByTypeTemporary = function() {

			var temporary = $http.get('/sentiment/findByType?type=Temporary').then(
					function(response) {

						if (response.data != null) {
							return response.data.messages;
						}
					});

			return temporary;
		};
		this.findById = function(sentimentId) {

			var promise = $http.get('/sentiment/find?id='+sentimentId).then(
					function(response) {

						if (response.data != null) {
							return response.data.messages[0];
						}
					});

			return promise;
		};

		this.post = function(cloneSentiment, file) {
			console.log(cloneSentiment);

			$http(
					{
						method : 'POST',
						url : '/sentiment/add',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('sentiment', new Blob([ JSOG
									.stringify(cloneSentiment) ], {
								type : "application/json"
							}));
							formData.append("file", file);
							return formData;
						},
						data : {
							cloneSentiment : cloneSentiment,
							file : file
						}
					}).success(function(data, status, headers, config) {
						
						alert("Sentiment created successfully");
						$window.location.href = '/#/';

			});
		};
		
		this.postPoll = function(pollAnswer) {

			cloneSentiment.createdBy = 2;

			$http(
					{
						method : 'POST',
						url : '/poll/answerPoll',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('pollOptionAnswer', new Blob([ JSOG
									.stringify(pollAnswer) ], {
								type : "application/json"
							}));
							return formData;
						},
						data : {
							pollOptionAnswer : pollAnswer,
							
						}
					}).success(function(data, status, headers, config) {
						

			});
		};
		
		this.getSentimentSupport = function(sentimentId,i) {

			var promise = $http.get('/sentiment/getSentimentSupport?sentimentId='+sentimentId).then(
					function(response) {

						if (response.data != null) {
							response.data.messages.id =i;
							return response.data.messages;
						}
					});

			return promise;
		};

	}

	SentimentService.$inject = [ '$http','$q','$window'];

	angular.module('governProject.sentiment').service('SentimentService',
			SentimentService);

})(window.angular);