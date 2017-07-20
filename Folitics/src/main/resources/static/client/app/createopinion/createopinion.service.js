(function(angular) {

	'use strict';

	function CreateOpinionService($http, $q) {

		var opinion = {};

		this.opinion = {
			userId : [],
			links : []
		};

		this.saveOpinion = function(opinion, file) {
			$http(
					{
						method : 'POST',
						url : '/opinion/addOpinion',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {
							var formData = new FormData();

							formData.append('opinion', new Blob([ angular
									.toJson(opinion) ], {
								type : "application/json"
							}));
							formData.append("file", file);
							return formData;
						},
						data : {
							opinion : opinion,
							file : file
						}
					}).success(function(data, status, headers, config) {
						if(data.success){							
						alert("Successfully created opinion");	
						$window.location.href = '/#/sentiment/:sentimentId/';
							
						}
			

			})
		};

	}

	CreateOpinionService.$inject = [ '$http', '$q' ];

	angular.module('governProject.createopinion').service(
			'CreateOpinionService', CreateOpinionService);

})(window.angular);