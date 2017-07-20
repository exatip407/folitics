(function(angular) {

	'use strict';

	function ProblemService($http, $rootScope, $q, $window,
			ElasticsearchService) {

		var task = {};
		var peopleMet = {};
		this.peopleMet = {
			name : "",
			departmentName : "",
			location : "",
			actionTaken : ""

		};

		this.task = {
			subject : "",
			description : "",
			createdBy : "",
			participants : "",
			departments : [],
			peopleMet : [],
			city : "",
			address : "",
			email : "",
			phone : "",
			category : [],
			isDept : "",
			isNGO : "",
			isLeader : "",
			showMobileNo : "",
			showEmail : "",
			taskPersonality : [],
			taskParticipants : []
		};

		/* console.log(this.task.peopleMet) */

		this.getTask = function() {

			var promise = $http.get(
					'/task/paginationApi?start=' + $rootScope.id).then(
					function(response) {

						if (response.data != null) {
							return response.data.messages;
						}
					});

			return promise;

		};

		this.post = function(task, file, file1, userId) {

			task.createdBy = {
				"id" : userId
			};
			console.log(task);
			/*
			 * task.participants = [ { "id" : userId } ];
			 */

			// task.city = "INDORE", task.address = "23 A Vaishali
			// Nagar",
			// task.email = "kshitij.mandloi@gmail.com",
			// task.phone = "7748065039",
			var promise = $http(
					{
						method : 'POST',
						url : '/task/addTask',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('task', new Blob([ JSON
									.stringify(task) ], {
								type : "application/json"
							}));
							formData.append("file", file);
							formData.append("image", file1);
							return formData;
						},
						data : {
							task : task,
							file : file,
							image : file1

						}

					}).success(function(data, status, headers, config) {

				if (data.success) {
					alert('Successfully added problem');

				}
				/* $rootScope.authenticated = false; */

			}).error(function(data) {
				/* delete $rootScope.sessionUser; */
				/* $rootScope.authenticated = false; */
			});
			return promise;
		};

		this.searchUser = function(query) {
			var promise = ElasticsearchService.search({
				index : 'module',
				type : 'user',
				body : {
					query : {
						query_string : {
							query : '*' + query + '*',
							fields : [ '*name' ]
						}
					}
				}
			}).then(function(resp) {
				// console.log(JSON.stringify(resp.hits.hits));
				var users = [];
				for (var i = 0; i < resp.hits.hits.length; i++) {
					users.push(resp.hits.hits[i]._source);
				}
				return users;
			}, function(err) {
				console.trace(err.message);
			});

			return promise;

		};

	}

	ProblemService.$inject = [ '$http', '$rootScope', '$q', '$window',
			'ElasticsearchService' ];

	angular.module('governProject.problem').service('ProblemService',
			ProblemService);

})(window.angular);