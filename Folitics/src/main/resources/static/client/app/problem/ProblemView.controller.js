(function(angular) {

	'use strict';

	function ProblemViewController($http, $scope, $window, $routeParams,
			$rootScope, ProblemService, ElasticsearchService) {

		$scope.likeCount = 0;
		$scope.dislikeCount = 0;

		var likeCount = {
			"componentId" : $routeParams.sentimentId,
			"componentType" : "taskComment",

		};

		var like = {
			"componentId" : $routeParams.sentimentId,
			"componentType" : "taskComment",
			"userId" : $rootScope.sessionUser.id,
		};
		$http.get(
				'/like/find?componentType=sentiment&componentId='
						+ $routeParams.sentimentId + '&userId='
						+ $rootScope.sessionUser.id).then(
				function(response) {

					// alert('to get promate and demote');

					if (response.data.messages !== null) {

						if (response.data.messages[0].likeFlag) {

							$scope.likeButtonValue = "Unpromote";
							$scope.colorLike = "#0000ff";
							$scope.dislikeButtonValue = "Demote";
							$scope.colorDislike = "#FF0000";
						} else if (response.data.messages[0].dislikeFlag) {

							$scope.likeButtonValue = "Promote";
							$scope.colorLike = "#FF0000";
							$scope.dislikeButtonValue = "Undemote";
							$scope.colorDislike = "#0000ff";
						} else if (!response.data.messages[0].likeFlag
								&& !response.data.messages[0].dislikeFlag) {
							$scope.likeButtonValue = "Promote";
							$scope.colorLike = "#FF0000";
							$scope.dislikeButtonValue = "Demote";
							$scope.colorDislike = "#FF0000";
						}
					} else {
						$scope.likeButtonValue = "Promote";
						$scope.colorLike = "#FF0000";
						$scope.dislikeButtonValue = "Demote";
						$scope.colorDislike = "#FF0000";
					}
				})

		$http.get(
				'/like/getLikeAndDislikeCount?componentType=taskComment&componentId='
						+ $routeParams.sentimentId).then(function(response) {

			if (response.data.messages !== null) {
				// console.log(response.data.messages[0].likeCount)
				$scope.likeCount = response.data.messages[0].likeCount;
				$scope.dislikeCount = response.data.messages[0].dislikeCount;

			}
		})

		$scope.commentBean = {
			componentId : $routeParams.problemId,
			componentType : "task",
			userId : $rootScope.sessionUser.id,
			comment : "",

		};

		$scope.addComment = function() {

			/* var commentBean = {}; */

			console.log($scope.commentBean);
			alert($scope.commentBean.comment);
			$http.post('/comment/comment', JSON.stringify($scope.commentBean))
					.then(function(response) {
						if (response.data.success == true) {
							alert('comment success');
							$scope.commentBean.comment = null;
							alert("nullable successfullly");
						}

					});

		}

		$scope.userId = $rootScope.sessionUser.id;
		$scope.task = ProblemService.task;
		$http
				.get(
						'/task/search?id=' + $routeParams.problemId
								+ '&userId=' + $rootScope.sessionUser.id)
				.then(
						function(response) {

							if (response.data.messages != null) {
								console
										.log('Inside search method of problemviewcontroller');
								$scope.task = response.data.messages[0];

								$scope.departs = response.data.messages[0].task.departments;

								if (response.data.messages[0].taskparticipant == null) {
									$scope.participatevalue = true;
									$scope.solveProblem = true;
									$scope.acceptvalue = "";
									$scope.rejectvalue = "";
								} else {
									if (response.data.messages[0].taskparticipant.status == "Waiting") {

										$scope.participatevalue = "";
										$scope.acceptvalue = true;
										$scope.rejectvalue = true;
										$scope.solveProblem = "";
									} else {
										$scope.participatevalue = "";
										$scope.acceptvalue = "";
										$scope.rejectvalue = "";
										$scope.solveProblem = true;
									}
								}

							}
							if (response.data.messages[0].task.departments == "") {
								$scope.message = "No department";
							}
						});

		$scope.addParticipants = function() {
			// alert("btn clicked");
			$http.get(
					'/task/addParticipant?taskId=' + $routeParams.problemId
							+ '&userId=' + $rootScope.sessionUser.id).then(
					function(response) {
						if (response.data.success == true) {
							alert('Participation success');
							$scope.task1 = response.data.messages;

							+$rootScope.sessionUser
							$scope.participatevalue = "";
							$scope.acceptvalue = true;
							$scope.rejectvalue = true;

						} else {
							alert('Participation failure');
						}
					});
		}
		$scope.setStatus = function() {
			// alert("btn clicked");
			$http.get(
					'/task/setStatus?id=' + $routeParams.problemId
							+ '&status=Completed').then(function(response) {

				if (response.data.success == true) {
					alert(' successfully update status');
					$scope.task1 = response.data.messages;

					+$rootScope.sessionUser
					$window.location.href = '/#problemLanding/';

				} else {
					alert('unsuccessful updated');
				}
			});
		}

		$scope.addSolver = function() {

			$http.get(
					'/task/addParticipant?taskId=' + $routeParams.problemId
							+ '&userId=' + $rootScope.sessionUser.id).then(
					function(response) {
						if (response.data.success == true) {

						} else {
							alert('Participation failure');
						}
					});

			$http.get(
					'/task/addSolver?taskId=' + $routeParams.problemId
							+ '&userId=' + $rootScope.sessionUser.id).then(
					function(response) {

						if (response.data.success == true) {

							$scope.participatevalue = "";
							$scope.solveProblem = "";
							$scope.acceptvalue = "";
							$scope.rejectvalue = "";

							$scope.task2 = response.data.messages;
							alert('add as a solver');

						} else {
							alert('solving failure');
						}
					});
		}

		/*
		 * http.get('/task/taskId='+$routeParams.id+'userId='+$rootScope.sessionUser.id).then(function(response){
		 * 
		 * if(response.data.success == true){
		 * if(response.data.messages[0].status == 'Pending'){ $scope.acceptvalue =
		 * true; $scope.rejectvalue = true; $scope.participatevalue = ""; }else{
		 * $scope.acceptvalue = ""; $scope.rejectvalue = "";
		 * $scope.participatevalue = ""; }}else{ $scope.acceptvalue = "";
		 * $scope.rejectvalue = ""; $scope.participatevalue = true; }
		 * 
		 * });
		 */

		$scope.accept = function() {

			$http.get(
					'/task/participateStatus?id=' + $routeParams.problemId
							+ '&userId=' + $rootScope.sessionUser.id).then(
					function(response) {

						if (response.data.success == true) {
							$scope.acceptvalue = "";
							$scope.rejectvalue = "";
							$scope.participatevalue = "";
						}

					});

		}

		$scope.reject = function() {
			$http.get(
					'/task/removeParticipant?id=' + $routeParams.problemId
							+ '&userId=' + $rootScope.sessionUser.id).then(
					function(response) {

						if (response.data.success == true) {
							$scope.acceptvalue = "";
							$scope.rejectvalue = "";
							$scope.participatevalue = true;
						}

					});
		}
		$scope.likeSentiment = function() {

			// alert('like function call' + $scope.likeButtonValue);

			if ($scope.likeButtonValue === "Promote") {
				$http.post('/like/like', JSON.stringify(like)).then(
						function(response) {

							$scope.likeButtonValue = "Unpromote";
							$scope.colorLike = "#0000ff";

							++$scope.likeCount;

							if ($scope.dislikeButtonValue === "Undemote") {
								$scope.dislikeButtonValue = "Demote";
								$scope.colorDislike = "#FF0000";
								$scope.dislikeCount--;
							}
						});
			}
			if ($scope.likeButtonValue === "Unpromote") {
				$http.get(
						'/like/unlike?componentType=sentiment&componentId='
								+ $scope.sentiment.id + '&userId='
								+ $rootScope.sessionUser.id).then(
						function(response) {

							$scope.likeButtonValue = "Promote";
							$scope.colorLike = "#FF0000";
							$scope.likeCount--;
						});
			}
		}
		$scope.dislikeSentiment = function() {

			if ($scope.dislikeButtonValue === "Demote") {
				$http.post(
						'/like/dislike?componentType=sentiment&componentId='
								+ $scope.sentiment.id + '&userId='
								+ $rootScope.sessionUser.id).then(
						function(response) {

							$scope.dislikeButtonValue = "Undemote";
							$scope.colorDislike = "#0000ff";
							$scope.dislikeCount++;
							if ($scope.likeButtonValue === "Unpromote") {
								$scope.likeButtonValue = "Promote";
								$scope.colorLike = "#FF0000";
								$scope.likeCount--;
							}
						});
			}
			if ($scope.dislikeButtonValue === "Undemote") {
				$http.post(
						'/like/undislike?componentType=taskComment&componentId='
								+ $scope.sentiment.id + '&userId='
								+ $rootScope.sessionUser.id).then(
						function(response) {

							$scope.dislikeButtonValue = "Demote";
							$scope.colorDislike = "#FF0000";
							$scope.dislikeCount--;
						});
			}
		}

	}

	ProblemViewController.$inject = [ '$http', '$scope', '$window',
			'$routeParams', '$rootScope', 'ProblemService',
			'ElasticsearchService' ];

	angular.module('governProject.problem').controller('ProblemViewController',
			ProblemViewController);

})(window.angular);