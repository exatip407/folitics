hello.controller(
		'navigation',

		function($rootScope, $scope, $http, $location, $route) {
			$scope.chartData = {};
			$scope.tab = function(route) {
				return $route.current
						&& route === $route.current.controller;
			};

			var authenticate = function(credentials, callback) {
				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('/entry/user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						$rootScope.authenticated = true;
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}).error(function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}

			if ($rootScope.authenticated) {
				authenticate();
			}

			$scope.credentials = {};
			$scope.login = function() {
				 var parameters = {
				          userName : $scope.credentials.username,
				          password : $scope.credentials.password
				            };

				            $http.post('/user/login', parameters)
				            .success(function (data, status, header) {
				             
				             if(data==true){
				               $location.path("/ankush");
				             } else {
				              $scope.authFlag = true;
				               $location.path("/login");
				             }
				              
				            })
				            .error(function (data, status, header, config) {
				                $scope.ServerResponse = "Data: " + data +
				                    "<hr />status: " + status +
				                    "<hr />headers: " + header +
				                    "<hr />config: " + config;
				            });
				/*authenticate($scope.credentials,
						function(authenticated) {
							if (authenticated) {
								console.log("Login succeeded")
								$location.path("/");
								$scope.error = false;
								$rootScope.authenticated = true;
							} else {
								console.log("Login failed")
								$location.path("/login");
								$scope.error = true;
								$rootScope.authenticated = false;
							}
						})*/
			};

			$scope.logout = function() {
				$http.post('/entry/logout', {}).success(function() {
					$rootScope.authenticated = false;
					$location.path("/");
				}).error(function(data) {
					console.log("Logout failed")
					$rootScope.authenticated = false;
				});
			}
		})