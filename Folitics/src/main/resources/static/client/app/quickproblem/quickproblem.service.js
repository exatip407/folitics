(function(angular) {

	'use strict';

	function QuickProblemService($http, $q) {

		this.quickProblem = {
			description : "",
			createdBy : " ",
			quickProblemPersonality : []

		};

		this.post = function(quickProblem, file, userId) {

			quickProblem.createdBy = {
				"id" : userId
			};

			$http(
					{
						method : 'POST',
						url : '/quickProblem/addQuickProblem',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('quickProblem', new Blob([ JSON
									.stringify(quickProblem) ], {
								type : "application/json"
							}));
							formData.append("file", file);
							return formData;
						},
						data : {
							quickProblem : quickProblem,
							file : file

						}

					}).success(function(data, status, headers, config) {

				if (data.success) {
					// alert('Form Submit');
				}

			}).error(function(data) {

			});
		};

	}

	QuickProblemService.$inject = [ '$http', '$q' ];

	angular.module('governProject.quickproblem').service('QuickProblemService',
			QuickProblemService);

})(window.angular);