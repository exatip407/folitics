(function(angular) {

	'use strict';

	function FactService($http, $q, $window) {

		var fact = {};

		this.fact = {
			subject : " ",
			description : " ",
			parentId : "",
			userId : [],
			parentName : "sentiment ",
			parentType : "Scheme ",
			status : "Accepted"
		};

		this.post = function(fact, file) {
			$http(
					{
						method : 'POST',
						url : '/fact/submitFact',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('fact', new Blob([ JSOG
									.stringify(fact) ], {
								type : "application/json"
							}));
							formData.append("file", file);
							return formData;
						},
						data : {
							fact : fact,
							file : file
						}
					}).success(function(data, status, headers, config) {

				if (data.success) {
					alert('Successfully submitted fact');
					$window.location.href = '/#gaHeader/';

				}

			});

		}
	}

	FactService.$inject = [ '$http', '$q', '$window' ];

	angular.module('governProject.fact').service('FactService', FactService);

})(window.angular);