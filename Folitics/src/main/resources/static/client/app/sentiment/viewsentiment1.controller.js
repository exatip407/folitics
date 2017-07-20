(function(angular) {

	'use strict';

	function ViewSentimentController1($http, $scope,$rootScope ,$routeParams, SentimentService,PollService, $window) {
		$scope.sentimentId = $routeParams.sentimentId;
		
		$scope.getPrerequisite = function(){
			SentimentService.getAllIndicatorForSentiment($routeParams.sentimentId).then(function(indicators){
				console.log(indicators);
				$scope.indicators = indicators;
			});
//			alert("inside");
		/*SentimentService.postPoll($scope.pollAnswer);*/
		};
		
		$scope.answerPoll = function(pollOptionId){
			PollService.answerPoll(pollOptionId,$rootScope.sessionUser.id).then(function(status){
				alert(status);
			});
		};

		$http.get('/sentiment/find?id=' + $routeParams.sentimentId).then(
		function(response) {

			// alert("iii");
			if (response.data.messages != null) {
				// alert(response.data.messages[0]);
				$scope.sentiment = response.data.messages[0];

			} else {

			}
		});

		$scope.likeCount = 0;
		$scope.dislikeCount = 0;
		$scope.followCount = 0;
		$scope.isFollowing = false;
		$scope.airCount = 0;
		$scope.facebookShareCount = 0;
		$scope.twitterShareCount = 0;
		$scope.btnText = "hide";

		var likeCount = {
			"componentId" : $routeParams.sentimentId,
			"componentType" : "sentiment",

		};

		var like = {
			"componentId" : $routeParams.sentimentId,
			"componentType" : "sentiment",
			"userId" : $rootScope.sessionUser.id,
		};

		$http.get(
		'/like/getLikeAndDislikeCount?componentType=sentiment&componentId='
		+ $routeParams.sentimentId).then(function(response) {

			if (response.data.messages !== null) {
//				console.log(response.data.messages[0].likeCount)
				$scope.likeCount = response.data.messages[0].likeCount;
				$scope.dislikeCount = response.data.messages[0].dislikeCount;
				

			}
		})

		$http.get(
		'/like/find?componentType=sentiment&componentId=' + $routeParams.sentimentId
		+ '&userId='+$rootScope.sessionUser.id).then(
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
				+ $scope.sentiment.id + '&userId='+$rootScope.sessionUser.id).then(function(response) {

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
				+ $scope.sentiment.id + '&userId='+$rootScope.sessionUser.id).then(function(response) {

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
				'/like/undislike?componentType=sentiment&componentId='
				+ $scope.sentiment.id + '&userId='+$rootScope.sessionUser.id).then(function(response) {

							$scope.dislikeButtonValue = "Demote";
							$scope.colorDislike = "#FF0000";
							$scope.dislikeCount--;
						});
			}
		}

		$scope.followOrUnFollowSentiment = function() {

			// alert('inside followorunfollow function');

			if ($scope.isFollowing) {
				$scope.unFollowSentiment();

			} else {
				$scope.followSentiment();
			}

			$scope.isFollowing = !$scope.isFollowing;
		}

		$scope.followSentiment = function() {

			// alert('inside following function');

			var follow = {
				"componentId" : $scope.sentiment.id,
				"componentType" : "sentiment",
				"userId" : $rootScope.sessionUser.id,
			};
			$http.post('/follow/follow', JSON.stringify(follow)).then(
			function(response) {

				// alert('following');

				++$scope.followCount;
				$scope.followUnfollow = "Unfollow"
			});

		}

		$scope.unFollowSentiment = function() {

			var unfollow = {
				"componentId" : $scope.sentiment.id,
				"componentType" : "sentiment",
				"userId" : $rootScope.sessionUser.id,
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

		}
		$scope.airSentiment = function() {

			var air = {
				"componentId" : $scope.sentiment.id,
				"componentType" : "sentiment",
				"userId" : $rootScope.sessionUser.id,

			};
			$http.post('/componentair/air', JSON.stringify(air)).then(
			function(response) {

				$scope.airCount++;
			});

		}

		$scope.toggleState = function() {

			if ($scope.btnStatus) {
				$http.post(
				'/sentiment/updateStatus?id=' + $routeParams.sentimentId
				+ '&status=Hidden').then(function(response) {

					$scope.btnText = "unhide";
					$scope.btnStatus = !$scope.btnStatus;
				});
			} else {
				$http.post(
				'/sentiment/updateStatus?id=' + $routeParams.sentimentId
				+ '&status=Alive').then(function(response) {

					$scope.btnText = "hide";
					$scope.btnStatus = !$scope.btnStatus;
				});
			}
		}
		$scope.shareSentiment = function(platform) {

			var share = {
				"componentId" : $scope.sentiment.id,
				"componentType" : "sentiment",
				"userId" : $rootScope.sessionUser.id,
				"platform" : platform
			};

			if (platform === 'facebook') {
				FB.ui({
					method : 'share',
					display: 'popup',
					redirect_uri : 'http://www.aryeet.com',
					href : 'http://www.aryeet.com'
				}, function(response) {

					alert(JSON.stringify(response));

					if (response && response.post_id) {
						$scope.facebookShareCount++;
						$http.post('/componentshare/share',
						JSON.stringify(share)).then(function(response) {

						})
					}
				});
			} else if (platform === 'twitter') {

				$scope.twitterShareCount++;

				$http.post('/componentshare/share', JSON.stringify(share)).then(
				function(response) {

				});
			}

		}

		$http.get(
		'/follow/getFollowCount?componentType=sentiment&componentId='
		+ $routeParams.sentimentId).then(function(response) {

			if (response.data.messages !== null) {

				if (response.data.messages[0] !== null) {
					$scope.followCount = response.data.messages[0];
				}
			}
		})

		/*
		 * Request to check whether the sentiment is followed by User or
		 * not
		 */
		$http.get(
		'/follow/isComponentFollowedByUser/?componentType=sentiment&userId=+'+$rootScope.sessionUser.id+'&componentId='
		+ $routeParams.sentimentId).then(function(response) {

			if (response.data.messages !== null) {

				if (response.data.messages[0]) {
					$scope.followUnfollow = "Unfollow"
				} else {
					$scope.followUnfollow = "Follow"
				}
			}
		})

	}

	ViewSentimentController1.$inject = [ '$http', '$scope','$rootScope', '$routeParams', 'SentimentService','PollService',
			'$window' ];

	angular.module('governProject.sentiment').controller('ViewSentimentController1',
	ViewSentimentController1);

})(window.angular);