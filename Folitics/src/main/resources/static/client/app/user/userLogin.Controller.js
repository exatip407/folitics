(function(angular) {

	'use strict';

	function userLoginController($http, $rootScope, $scope, userService) {

		$scope.loginSuccess = false;
		$scope.loginResponse;
		var userId = "";
		$scope.login = function() {

			$http(
					{
						method : 'POST',
						url : '/login',
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded'
						},
						transformRequest : function(obj) {

							var str = [];
							for ( var p in obj)
								str.push(encodeURIComponent(p) + "="
										+ encodeURIComponent(obj[p]));
							return str.join("&");
						},
						data : {
							username : $scope.username,
							password : $scope.password
						}
					})
					.success(
							function(data) {

								$scope.authMessage = data.responseMessage;
								$scope.authResponse = true;
								$rootScope.authenticated = true;
								userService
										.getUserByUsername(
												data.messages[0].username)
										.then(
												function(response) {

													$rootScope.sessionUser = response;
													userId = $rootScope.sessionUser.id;
													userService
															.subscribeUser(
																	userId)
															.then(
																	function(
																			response) {

																		var response = response;

																	});

													userService
															.getUnreadNotificationsGeneral(
																	userId)
															.then(
																	function(
																			response) {

																		$rootScope.generalNotifications = response;

																	});
													/*
													 * userService
													 * .getUnreadNotificationsConnection(
													 * userId) .then( function(
													 * response) {
													 * $rootScope.connectionNotifications =
													 * response;
													 * 
													 * console
													 * .log($rootScope.connectionNotifications);
													 * });
													 */

													userService
															.getUnreadNotificationsOpinion(
																	userId)
															.then(
																	function(
																			response) {
																		$rootScope.opinionNotifications = response;

																	});
													userService
															.getUnreadNotificationsTask(
																	userId)
															.then(
																	function(
																			response) {
																		$rootScope.taskNotifications = response;

																	});
													userService
															.getUnreadNotificationsConnection(
																	userId)
															.then(
																	function(
																			response) {
																		$rootScope.connectionNotifications = response;

																	});

													// / Notification code
													// start
									/*				APP.asyncPoll = function(
															sessionUserId) {

														return $
																.ajax({
																	url : "/appMsgsAsync?userId="
																			+ sessionUserId,
																	accepts : {
																		text : "application/json"
																	}
																});

													};*/

													APP.recurseAsync = function() {

														$('#asyncSpinner')
																.show();
														APP
																.asyncPoll(
																		userId)

																.done(
																		function(
																				result) {
																			console
																					.log(result);
																			if (typeof result !== "undefined"
																					&& result) {

																				var jsonObj = JSON
																						.parse(result);

																				if (jsonObj.notificationType == "connection") {
																					$rootScope.redisConnectionNotification = jsonObj.message;
																				}
																				if (jsonObj.notificationType == "general") {
																					$rootScope.redisGeneralNotification = jsonObj.message;
																				}
																				if (jsonObj.notificationType == "connection") {
																					$rootScope.redisOpinionNotification = jsonObj.message;
																				}

																			}

																			$rootScope.redisGeneralNotification = result.message;
																			/*
																			 * alert("redis
																			 * notification
																			 * is =" +
																			 * $rootScope.redisGeneralNotification);
																			 */

																			// APP.updateAsync(result);
																			APP
																					.recurseAsync();
																		})
																.error(
																		function(
																				xhr,
																				errorStr,
																				statusTxt) {

																			console
																					.dir(arguments);
																			console
																					.log("error making async call - waiting 60 seconds until next try");
																			// proxy
																			// timeout
																			// -
																			// let's
																			// just
																			// start
																			// again
																			error(function(
																					xhr,
																					errorStr,
																					errorMsg) {

																				console
																						.dir(arguments);
																				if (xhr.status === 504) {
																					console
																							.log("Proxy Timeout - making new call immediately.");
																					APP
																							.recurseAsync();
																				}
																				// some
																				// other
																				// error
																				// -
																				// possibly
																				// the
																				// server
																				// went
																				// down
																				else {
																					console
																							.log("error making async call (server might be down)- waiting 60 seconds until next try.");
																					setTimeout(
																							APP.recurseAsync,
																							60000);
																				}
																			});
																		});
													};
													APP.recurseAsync();

													// Notification code
													// complete

												});

							})
					.error(
							function(data, status, headers, config) {

								$scope.authMessage = 'Username or password is incorrect';
								$scope.authResponse = true;
							});
		};

		$scope.logout = function() {

			$http.post('logout', {}).success(function() {

				$scope.authMessage = 'Succesfully logout';
				$scope.authResponse = true;
				$rootScope.authenticated = false;
				var userId = $rootScope.sessionUser.id;
				userService.unSubscribeUser(userId).then(function(response) {

					var response = response;

				});
				delete $rootScope.sessionUser;
				$rootScope.notifications = [];
				// $location.path("/");
			}).error(function(data) {

				delete $rootScope.sessionUser;
				$rootScope.authenticated = false;
			});
		};
		/*
		 * if (typeof $rootScope.sessionUser !== "undefined" &&
		 * $rootScope.sessionUser){ var userId=$rootScope.sessionUser.id;
		 * userService.getUser(userId).then(function(response){
		 * $scope.personality=response; $scope.personality = [ { "imageUrl" :
		 * response.userImage.image, "userName" : response.username, "emailId" :
		 * response.emailId, "badge" :response.badge, "points" :response.points,
		 * "pagination" : "1", "feedList" : [ { "feed" : "" }, { "feed" : " " }, {
		 * "feed" : "" }, { "feed" : "" }, { "feed" : "" } ] } ];
		 * 
		 * }); }
		 */

	}

	userLoginController.$inject = [ '$http', '$rootScope', '$scope',
			'userService' ];

	angular.module('governProject.user').controller('userLoginController',
			userLoginController);

})(window.angular);