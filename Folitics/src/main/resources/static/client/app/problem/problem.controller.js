(function(angular) {

	'use strict';

	function ProblemController($http, $scope, $rootScope, $location, $window,
			ProblemService, ElasticsearchService) {

		/* df */
		$scope.task = {
			taskParticipants : [],
			taskPersonality : [],
			category : []
		};
		$scope.personalities1 = [];
		$scope.roles = [ 'guest', 'user', 'customer', 'admin' ];
		$scope.user = {
			roles : [ 'user' ]
		};
		$scope.checkAll = function() {
			$scope.user.roles = angular.copy($scope.roles);
		};
		$scope.uncheckAll = function() {
			$scope.user.roles = [];
		};
		$scope.checkFirst = function() {
			$scope.user.roles.splice(0, $scope.user.roles.length);
			$scope.user.roles.push('guest');
		};
		/**
		 * 
		 */

		$scope.peopleMets = [];
		$http.get('/task/getAllPersonality').then(function(response) {

			if (response.data.messages != null) {
				$scope.personalities = response.data.messages[0];
				// console.log($scope.personalities);
			}
		});

		$http.get('/task/getAll').then(function(response) {
			console.log('hello');
			$scope.tasks = response.data.messages[0];
		});

		$scope.truevalue = true;
		$scope.problemTypeSelected;
		$scope.testFunction = function() {

		};

		$scope.prerequisites = {
			departments : [ "Health", "Fire", "Sports", "MCD", "Transport" ]
		};

		$scope.problem = {
			departmentSelected : []
		};

		$scope.searchTaskByStatus = function() {

			return ElasticsearchService.search({
				index : 'module',
				type : 'task',
				body : {
					fields : [ 'subject', 'attachment', 'id' ],
					query : {
						match : {
							status : '"' + $scope.problemTypeSelected + '"'
						}
					}
				}
			}).then(function(resp) {

				$scope.tasks = resp.hits.hits;
				/* return resp.hits.hits; */
			}, function(err) {

				console.trace(err.message);
			});
		};
		$scope.searchTaskByProblemType = function() {

			return ElasticsearchService.search({
				index : 'module',
				type : 'task',
				body : {
					fields : [ 'subject', 'attachment', 'id' ],
					query : {

						query_string : {
							query : '*' + $scope.problemTypeSelected + '*',
							fields : [ 'departments.*' ]
						}
					}
				}
			}).then(function(resp) {

				$scope.tasks = resp.hits.hits;
				/* return resp.hits.hits; */
			}, function(err) {

				console.trace(err.message);
			});
		};

		$scope.searchTaskByProblemType = function() {

			return ElasticsearchService.search({
				index : 'module',
				type : 'task',
				body : {
					fields : [ 'subject', 'attachment', 'id' ],
					query : {

						query_string : {
							query : '*' + $scope.problemTypeSelected + '*',
							fields : [ 'category.*' ]
						}
					}
				}
			}).then(function(resp) {

				$scope.tasks = resp.hits.hits;
				/* return resp.hits.hits; */
			}, function(err) {

				console.trace(err.message);
			});
		};
		$scope.task = ProblemService.task;
		// $scope.task.createdBy = $rootScope.sessionUser.id;
		$scope.submit = function() {
			/* console.log($scope.task); */
			/* var taskPersonality =[]; */

			if (typeof ($scope.pinUsers) != 'undefined') {
				for (var i = 0; i < $scope.pinUsers.length; i++) {

					delete $scope.pinUsers[i]['@id'];
					delete $scope.pinUsers[i].userImage['@id'];
					var taskParticipant = {
						user : $scope.pinUsers[i]
					};
					$scope.task.taskParticipants.push(taskParticipant);
				}
			}

			for (var i = 0; i < $scope.personalities1.length; i++) {
				var taskPersonalities1 = {
					personality : $scope.personalities[i]
				};
				$scope.task.taskPersonality.push(taskPersonalities1);
			}
			;
			console.log($scope.task);
			ProblemService.post($scope.task, $scope.file, $scope.file1,
					$rootScope.sessionUser.id).then(function(resp) {
				alert("object=" + resp.data);
				console.log(resp);
				if (resp.data.success == true) {
					delete $scope.task;
					$scope.task = "";
					$scope.problemForm = "";
					$window.location.href = '/#solveProblem/';
				}

			});

		};

		$scope.searchTask = function(val) {

			return ElasticsearchService.search({
				index : 'module',
				type : 'task',
				body : {
					fields : [ 'subject', 'attachment', 'id' ],
					query : {
						query_string : {
							query : '*' + val + '*',
							fields : [ 'address', 'city' ]
						}
					}
				}
			}).then(function(resp) {

				$scope.tasks = resp.hits.hits;
				/* return resp.hits.hits; */
			}, function(err) {

				console.trace(err.message);
			});
		};

		$scope.addAnotherPerson = function() {

			$scope.peopleMets.push($scope.peopleMet);
			$scope.peopleMet = {};
		};

		$scope.addPeopleMets = function() {

			$scope.peopleMets.push($scope.peopleMet);
			angular.copy($scope.peopleMets, $scope.task.peopleMet);
			$scope.peopleMet = {};
			$scope.peopleMets = [];
		}

		$http.get('/task/getTaskCategories').then(function(response) {

			if (response.data.messages != null) {
				$scope.categories = response.data.messages[0];
			}
		})

		$http.get('/task/getAllDepartments').then(function(response) {

			if (response.data.messages != null) {
				$scope.departments = response.data.messages[0];
			}
		})

		$scope.reload = function() {

			$location.path("");

		}

		$scope.loadPinUser = function(query) {
			return ProblemService.searchUser(query);
		};

	}

	ProblemController.$inject = [ '$http', '$scope', '$rootScope', '$location',
			'$window', 'ProblemService', 'ElasticsearchService' ];

	angular.module('governProject.problem').controller('ProblemController',
			ProblemController).directive('myEnter', function() {

		return function(scope, element, attrs) {

			element.bind("keydown keypress", function(event) {

				if (event.which === 13) {
					if (!scope.$$phase) {
						scope.$apply(function() {

							scope.$eval(attrs.myEnter);
						});
					}
					event.preventDefault();
				}
			});
		};
	});

})(window.angular);