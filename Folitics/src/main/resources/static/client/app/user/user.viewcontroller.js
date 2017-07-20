(function(angular) {

	'use strict';

	function UserViewController($http, $scope, $rootScope, $routeParams,
			$filter, userService) {

		var user = $rootScope.sessionUser;
		
/*		if(user.id=!=userPage)
			$scope.showConnect = true;
		else
			$scope.showConnect = "";*/

		$http.get('/task/mytasks?userId=' + $routeParams.userId).then(
				function(response) {
					if(response.data.success == false){
						$scope.factSize = '0';
					}else{
					if (typeof (response.data.messages[0]) != undefined)
						$scope.taskSize = response.data.messages[0].length;
					else
						$scope.taskSize = '0';
						}
					
				});

		$http.get('/opinion/myopinion?userId=' + $routeParams.userId).then(
				function(response) {
					if(response.data.success == false){
						$scope.factSize = '0';
					}else{
					if (typeof (response.data.messages[0]) != undefined)
						$scope.opinionSize = response.data.messages[0].length;
					else
						$scope.opinionSize = '0';
					}
				});

		$http.get('/fact/getAllFactsByUserId?userId=' + $routeParams.userId)
				.then(function(response) {
					if(response.data.success == false){
						$scope.factSize = '0';
					}else{
					if (typeof (response.data.messages) != undefined){
						$scope.factSize = response.data.messages.length;
					}else
						$scope.factSize = '0';
					}
					
				});

		$http.get('/response/getByUserId?id=' + $routeParams.userId).then(
				function(response) {
					if(response.data.success == false){
						$scope.factSize = '0';
					}else{
					if (typeof (response.data.messages[0]) != undefined){
						$scope.responseSize = response.data.messages.length;
					}
					else
						$scope.responseSize = '0';
					
						}
				});


		$http.get('/user/find?id=' + $routeParams.userId).then(
				function(response) {

					if (response.data.messages != null) {
						$scope.user = response.data.messages[0];
						$scope.date = $filter('date')($scope.user.dob,
								"dd/MM/yyyy");
					} else {

					}
				});

		$http.get('/user/getUserConnection?userId=' + $routeParams.userId)
				.then(function(response) {

					if (response.data.messages != null) {
						$scope.user = response.data.messages[0];
					} else {

					}
				});

		$http
				.get(
						'/user/checkconnection?userId='
								+ $rootScope.sessionUser.id + '&connectionId='
								+ $routeParams.userId)
				.then(
						function(response) {

							if (response.data.success === true) {
								$scope.add = "";
								$scope.delete1 = true;

								if (response.data.messages[0].connectionStatus != "Pending") {

									$scope.add = "";
								} else {
									$scope.add = true;
								}
							} else {
								$scope.sendRequest = true;
								$scope.delete1 = "";
							}
						});

		$scope.addConnection = function() {
			$http.get(
					'/user/addConnection?userId=' + $scope.sessionUser.id
							+ '&connectionId=' + $routeParams.userId).then(
					function(response) {
						$scope.add = "";
						$scope.delete1 = true;
						$scope.sendRequest = "";

						if (response.data.messages != null) {

							$scope.user = response.data.messages[0];
							console.log($scope.user);

						}

					});

		}

		$scope.accept = function() {
			$http.get(
					'/user/updateConnectionStatus?userId=' + $routeParams.userId
							+ '&connectionId=' + $scope.sessionUser.id + '&connectionStatus=Accepted').then(
					function(response) {
						$scope.add = "";
						$scope.delete1 = true;
						$scope.sendRequest = "";

						if (response.data.messages != null) {

							$scope.user = response.data.messages[0];
							console.log($scope.user);

						}

					});

		}
		
		$scope.deleteConnection = function() {
			$http.get(
					'/user/deleteConnection?userId='
							+ $routeParams.userId + '&connectionId='
							+ $rootScope.sessionUser.id).then(function(response) {
				$scope.sendRequest = true;
				$scope.delete1 = "";
				$scope.add = "";

				if (response.data.messages != null) {

					$scope.user = response.data.messages[0];
					console.log($scope.user);
				}

			});
		}

		$scope.getUserConnection = function() {

			$http.get(
					'/user/getUserConnection?userId='
							+ $rootScope.sessionuser.id).then(
					function(response) {

						if (response.data.messages != null) {
							$scope.connectedUser = response.data.messages;
							console.log($scope.connectedUser);
						} else {
							alert('u have no connection');

						}

					});

		}

	}

	UserViewController.$inject = [ '$http', '$scope', '$rootScope',
			'$routeParams', '$filter', 'userService' ];

	angular.module('governProject.user').controller('UserViewController',
			UserViewController);

})(window.angular);