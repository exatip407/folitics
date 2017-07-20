(function(angular) {

	'use strict';

	function HeaderController($scope, $window, $rootScope, SearchService,
			userService) {
		$scope.updateNotificationStatus = function(notificationId, index) {

			$scope.index = index;

			userService.updateNotification(notificationId).then(
					function(status) {
						alert("status:-" + status);
						if (status == true)

							delete $rootScope.notifications[$scope.index];
					});
		}

		$scope.headerPrerequsite = function() {
			userService
					.getUserFromSession()
					.then(
							function(user) {
								if (user == false) {

								} else {
									$rootScope.authenticated = true;
									$rootScope.sessionUser = user;
									console.log($rootScope.sessionUser);
									var userId = $rootScope.sessionUser.id;
									userService
											.getUnreadNotificationsGeneral(
													userId)
											.then(
													function(response) {
														$rootScope.generalNotifications = response;
														console
																.log($rootScope.generalNotifications);
													});
									userService
											.getUnreadNotificationsConnection(
													userId)
											.then(
													function(response) {
														$rootScope.connectionNotifications = response;

														console
																.log($rootScope.connectionNotifications);
													});
									userService
											.getUnreadNotificationsOpinion(
													userId)
											.then(
													function(response) {
														$rootScope.opinionNotifications = response;

														console
																.log($rootScope.opinionNotifications);
													});
									userService
											.getUnreadNotificationsTask(userId)
											.then(
													function(response) {
														$rootScope.taskNotifications = response;

														console
																.log($rootScope.taskNotifications);
													});
								}
							});
		};

		// ---------------search start------------------

		$scope.search = function() {

			/*
			 * var index = $scope.module; var text = $scope.text;
			 * SearchService.searchText(index,text).then(function(resp){
			 * $window.location.href = '/#/' + index + ''; $rootScope.users =
			 * resp; });
			 */

			var index = $scope.module;

			SearchService.searchText($scope.module, $scope.text).then(
					function(response) {
						$scope.module = "";
						$window.location.href = '/#/' + index + '';
						$scope.text = "";
						if (response == "") {

							$rootScope.check1 = 'images/smiley.gif';
							$rootScope.note = "SEARCH AGAIN";
						} else {
							$rootScope.check1 = "";
							$rootScope.note = "";
						}

						if (index === "user")
							$rootScope.users = response;
						if (index === "sentiment")
							$rootScope.sentiments = response;
						if (index === "task")
							$rootScope.tasks = response;

					});

		}

		// ---------------search end------------------

		// --------------- change --------------------
		/*
		 * $scope.change = function() { $scope.resultsArr = [];
		 * 
		 * results = statusRequest .query(MatchQuery('_all', $scope.module))
		 * .fields(['name', 'username', 'emailId']) .doSearch();
		 * $scope.resultsArr.push(results); };
		 */

	}

	HeaderController.$inject = [ '$scope', '$window', '$rootScope',
			'SearchService', 'userService' ];

	angular.module('governProject.header').controller('HeaderController',
			HeaderController);

})(window.angular);