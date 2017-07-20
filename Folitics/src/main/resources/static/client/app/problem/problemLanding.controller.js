(function(angular) {

	'use strict';

	function ProblemLandingController($http, $scope, ProblemService, ElasticsearchService) {
		
	/*	 ElasticsearchService.search({
			index : 'module',
			type : 'task',
			body : {
				query : {
					match : {
						status: 'Created'
					}
				}
			}
		}).then(function(resp) {

			console.log(JSON.stringify(resp.hits.hits));
			$scope.tasks = resp.hits.hits;
			 return resp.hits.hits; 
		}, function(err) {

			console.trace(err.message);
		});*/
		
		
		 $http.get('task/getByStatus?status=Completed').then(function(response) {

			   // alert("GA Header");
			   console.log('Inside');
			   $scope.tasks = response.data.messages[0]; 
			   
				   /*JSON.parse(JSON.stringify(response.data.messages));*/
			

			  });

		/*$scope.searchTaskByProblemType = function() {

			return ElasticsearchService.search({
				index : 'module',
				type : 'task',
				body : {
					fields : [ 'subject','attachment','id' ],
					query : {
						match : {
							status: 'Created'
						}
					}
				}
			}).then(function(resp) {

				console.log(JSON.stringify(resp.hits.hits));
				$scope.tasks = resp.hits.hits;
				 return resp.hits.hits; 
			}, function(err) {

				console.trace(err.message);
			});
		};
		$scope.searchTaskByProblemType();*/
	}

	ProblemLandingController.$inject = [ '$http', '$scope', 'ProblemService',
			'ElasticsearchService' ];

	angular.module('governProject.problem').controller('ProblemLandingController',
	ProblemLandingController);

})(window.angular);