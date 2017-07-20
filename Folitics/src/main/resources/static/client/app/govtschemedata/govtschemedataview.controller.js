(function(angular) {

	'use strict';

	function GovtschemedataviewController($http, $scope, $routeParams,
			GovtschemedataService) {

		$http.get('/govtschemedata/find?id=' + $routeParams.govtId).then(
				function(response) {

					if (response.data.messages != null) {
						$scope.govtschemedataview = response.data.messages[0];
						console.log($scope.govtschemedataview);
					} else {
					}
				});

		$scope.followCount = 0;
		//$scope.isFollowing = true;
		

		$http.get(
				'/follow/getFollowCount?componentType=govtSchemeData&componentId='
						+ $routeParams.govtId).then(function(response) {
			alert('inside get follow count');

			if (response.data.messages !== null) {
				alert(response.data.messages);
				if (response.data.messages[0] !== null) {
					$scope.followCount = response.data.messages[0];
					alert(+$scope.followCount);
				}
				else{}
			}
		})

		$scope.followOrUnFollowGovtSchemeData = function() {

			//alert('inside followorunfollow function');

			if ($scope.isFollowing) {
				$scope.followGovtSchemeData();
			} else {
				$scope.unFollowGovtSchemeData();
			}

			$scope.isFollowing = !$scope.isFollowing;
		}

		$scope.followGovtSchemeData = function() {

		//	alert('inside following function');

			var follow = {
				"componentId" :$routeParams.govtId,
				"componentType" : "govtschemedata",
				"userId" : 3,
			};
			$http.post('/follow/follow', JSON.stringify(follow)).then(
					function(response) {

				//		alert('following');

						++$scope.followCount;
						$scope.followUnfollow = "Unfollow"
					});

		}

		$scope.unFollowGovtSchemeData = function() {

			var unfollow = {
				"componentId" : $routeParams.govtId,
				"componentType" : "govtschemedata",
				"userId" : 3
			};
			$http.post('/follow/unfollow', JSON.stringify(unfollow)).then(
					function(response) {

						if ($scope.followCount > 0) {
							--$scope.followCount;
						}

						else {
							$scope.followCount = 0;
						}
						$scope.followUnfollow = "Follow"
					});


			/*
			 * Request to check whether the govtscheme is followed by User or not
			 */
			$http
					.get(
							'/follow/isComponentFollowedByUser/?componentType=govtschemedata&userId=1&componentId='
									+ $routeParams.govtId).then(
							function(response) {

								if (response.data.messages !== null) {

									if (response.data.messages[0]) {
										$scope.followUnfollow = "Unfollow"
									} else {
										$scope.followUnfollow = "Follow"
									}
								}
							})
		}
	}

	GovtschemedataviewController.$inject = [ '$http', '$scope', '$routeParams',
			'GovtschemedataService', ];

	angular.module('governProject.govtschemedata').controller(
			'GovtschemedataviewController', GovtschemedataviewController);

})(window.angular);