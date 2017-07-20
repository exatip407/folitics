var viewSentiment = angular.module("viewSentiment", [ 'ngRoute', 'ngResource', 'ngTagsInput' ]);

viewSentiment.service("viewSentimentService", [
		"$http",
		function($http) {

			this.cloneSentiment = {
				subject : "",
				description : "",
				createdBy : "",
				startTime : "",
				endTime : "",
				state : "",
				type : "",
				polls : [],
				categories : [],
				relatedSentiments : []

			};
			this.attachedRelatedSentimentIds = [];

			this.post = function(cloneSentiment, file) {

				cloneSentiment.createdBy = 1;

				$http(
				{
					method : 'POST',
					url : '/sentiment/add',
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : function() {

						var formData = new FormData();
						formData.append('sentiment', new Blob([ JSOG
						.stringify(cloneSentiment) ], {
							type : "application/json"
						}));
						formData.append("file", file);
						return formData;
					},
					data : {
						cloneSentiment : cloneSentiment,
						file : file
					}
				}).success(function(data, status, headers, config) {

				});
			};

		} ]);

viewSentiment.directive('fileModel', [ '$parse', function($parse) {

	return {
		restrict : 'A',
		link : function(scope, element, attrs) {

			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {

				scope.$apply(function() {

					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

viewSentiment.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : '/displayAllSentiment.html',
		controller : 'displayAllSentimentController'
	}).when('/sentiment/:sentimentId', {
		templateUrl : 'viewSentiment.html',
		controller : 'viewSentimentController'
	}).when('/createOpinionOnComponent/:sentimentId', {
		templateUrl : 'createOpinionOnComponent.html',
		controller : 'createOpinionOnSentimentController'
	}).otherwise({
		redirectTo : '#'
	});
});

viewSentiment.controller("displayAllSentimentController", [
		'$scope',
		'$http',
		'viewSentimentService',
		function($scope, $http, allSentimentService) {

			$http.get('/sentiment/getAll').then(
			function(response) {

				$scope.sentiments = JSOG.parse(JSOG
				.stringify(response.data.messages));
			});

		} ]);

viewSentiment
.controller(
"viewSentimentController",
[
		'$scope',
		'$http',
		'viewSentimentService',
		'$routeParams',
		'$window',
		function($scope, $http, viewSentimentService, $routeParams, $window) {

			$scope.likeCount = 0;
			$scope.dislikeCount = 0;
			$scope.facebookShareCount = 0;
			$scope.twitterShareCount = 0;
			$scope.airCount = 0;
			$scope.followCount = 0;
			$scope.isFollowing = false;
			$scope.cloneSentiment = viewSentimentService.cloneSentiment;
			$scope.cloneSentimentTypes = [ {
				name : 'Temporary'
			}, {
				name : 'Permanent'
			} ];

			$http
			.get('/sentiment/find?id=' + $routeParams.sentimentId)
			.then(
			function(response) {

				$scope.sentiment = JSOG.parse(JSOG
				.stringify(response.data.messages[0]));
				$scope.cloneSentiment = JSOG.parse(JSOG
				.stringify(response.data.messages[0]));
				$scope.cloneSentiment.subject = "Clone_"
				+ $scope.cloneSentiment.subject;
				viewSentimentService.attachedRelatedSentimentIds = [];
				viewSentimentService.attachedRelatedSentimentIds
				.push($routeParams.sentimentId);
				for (var i = 0; i < $scope.sentiment.relatedSentiments.length; i++) {
					viewSentimentService.attachedRelatedSentimentIds
					.push($scope.sentiment.relatedSentiments[i].id);
				}

				$http.post('/sentiment/getAllRelatedSentimentSuggetion',
				JSOG.stringify(viewSentimentService.attachedRelatedSentimentIds))
				.then(
				function(response) {

					$scope.cloneRelatedsentiments = JSOG.parse(JSOG
					.stringify(response.data.messages));
				});

				if ($scope.sentiment.state != 'Hidden') {
					$scope.btnStatus = true;
					$scope.btnText = "hide";
				} else {
					$scope.btnStatus = true;
					$scope.btnText = "unhide";
				}
			});

			$http.get('/category/getActiveSubCategories').then(
			function(response) {

				$scope.cloneCategories = JSOG.parse(JSOG
				.stringify(response.data.messages));
			});
			$http.get(
			'/sentiment/getAllSentimentIndicator?id=' + $routeParams.sentimentId).then(
			function(response) {

				$scope.indicators = JSOG.parse(JSOG
				.stringify(response.data.messages[0]));

			});

			$http.get('sentiment/getAllSources?id=' + $routeParams.sentimentId).then(
			function(response) {

				$scope.sources = JSOG.parse(JSOG
				.stringify(response.data.messages[0]));
			});

			$http.get(
			'mylike/find?componentType=sentiment&componentId='
			+ $routeParams.sentimentId + '&userId=1').then(
			function(response) {

				if (response.data.messages !== undefined) {

					if (response.data.messages[0].likeFlag) {

						$scope.likeButtonValue = "Unpromote";
						$scope.dislikeButtonValue = "Demote";

					} else if (response.data.messages[0].dislikeFlag) {

						$scope.likeButtonValue = "Promote";
						$scope.dislikeButtonValue = "Undemote";

					} else if (!response.data.messages[0].likeFlag
					&& !response.data.messages[0].dislikeFlag) {
						$scope.likeButtonValue = "Promote";
						$scope.dislikeButtonValue = "Demote";
					}
				} else {
					$scope.likeButtonValue = "Promote";
					$scope.dislikeButtonValue = "Demote";
				}
			})

			$http
			.post(
			'mylike/getLikeAndDislikeCount?componentType=sentiment&componentId='
			+ $routeParams.sentimentId)
			.then(
			function(response) {

				if (response.data.messages !== null) {
					$scope.likeCount = response.data.messages[0].likeCount;
					$scope.dislikeCount = response.data.messages[0].dislikeCount;
				}
			})
			$http
			.get(
			'/componentshare/getShareCount?componentType=sentiment&componentId='
			+ $routeParams.sentimentId)
			.then(
			function(response) {

				if (response.data.messages !== null) {

					if (response.data.messages[0] !== null) {
						$scope.facebookShareCount = response.data.messages[0].facebookShareCount;
						$scope.twitterShareCount = response.data.messages[0].twitterShareCount;
					}
				}
			})

			$http.get(
			'/componentair/getAirCount?componentType=sentiment&componentId='
			+ $routeParams.sentimentId).then(function(response) {

				if (response.data.messages !== null) {

					if (response.data.messages[0] !== null) {
						$scope.airCount = response.data.messages[0];

					}
				}
			})

			$http.get(
			'/follow/getFollowCount?componentType=sentiment&componentId='
			+ $routeParams.sentimentId).then(function(response) {

				if (response.data.messages !== null) {

					if (response.data.messages[0] !== null) {
						$scope.followCount = response.data.messages[0];

					}
				}
			})

			$http.get(
			'/follow/isComponentFollowedByUser/?componentType=sentiment&userId=1&componentId='
			+ $routeParams.sentimentId).then(function(response) {

				if (response.data.messages !== null) {

					if (response.data.messages[0] == "Yes") {

						$scope.isFollowing = true
						$scope.followUnfollow = "Unfollow"
					} else {
						$scope.followUnfollow = "Follow"
					}
				}

			})

			$scope.loadCloneSentiments = function($query) {

				return $scope.cloneRelatedsentiments;
			};

			$scope.loadCloneCategories = function($query) {

				return $scope.cloneCategories;
			};

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

			$scope.submit = function(isValid) {

				if (isValid) {
					if (typeof ($scope.file) != "undefined") {
						$scope.cloneSentiment.image = "";
						viewSentimentService.post(JSOG
						.encode($scope.cloneSentiment), $scope.file);

					} else {
						$http.post('/sentiment/clone',
						JSOG.stringify(JSOG.encode($scope.cloneSentiment)))
						.then(function(response) {

						});
					}

				} else {
					alert('Validation fail');
				}
			};

			$scope.likeSentiment = function() {

				if ($scope.likeButtonValue === "Promote") {
					$http.post(
					'/mylike/like?componentType=sentiment&componentId='
					+ $scope.sentiment.id + '&userId=1').then(
					function(response) {

						$scope.likeButtonValue = "Unpromote"
						$scope.likeCount++;
						if ($scope.dislikeButtonValue === "Undemote") {
							$scope.dislikeButtonValue = "Demote"
							$scope.dislikeCount--;
						}
					});
				}
				if ($scope.likeButtonValue === "Unpromote") {
					$http.post(
					'/mylike/unlike?componentType=sentiment&componentId='
					+ $scope.sentiment.id + '&userId=1').then(
					function(response) {

						$scope.likeButtonValue = "Promote"
						$scope.likeCount--;
					});
				}
			}
			$scope.dislikeSentiment = function() {

				if ($scope.dislikeButtonValue === "Demote") {
					$http.post(
					'/mylike/dislike?componentType=sentiment&componentId='
					+ $scope.sentiment.id + '&userId=1').then(
					function(response) {

						$scope.dislikeButtonValue = "Undemote"
						$scope.dislikeCount++;
						if ($scope.likeButtonValue === "Unpromote") {
							$scope.likeButtonValue = "Promote"
							$scope.likeCount--;
						}
					});
				}
				if ($scope.dislikeButtonValue === "Undemote") {
					$http.post(
					'/mylike/undislike?componentType=sentiment&componentId='
					+ $scope.sentiment.id + '&userId=1').then(
					function(response) {

						$scope.dislikeButtonValue = "Demote"
						$scope.dislikeCount--;
					});
				}
			}

			$scope.airSentiment = function() {

				var air = {
					"componentId" : $scope.sentiment.id,
					"componentType" : "sentiment",
					"userId" : 1,

				};
				$http.post('/componentair/air', JSON.stringify(air)).then(
				function(response) {

					$scope.airCount++;
				})

			}

			$scope.followOrUnFollowSentiment = function() {

				if ($scope.isFollowing) {
					$scope.followSentiment()
				} else {
					$scope.unFollowSentiment()
				}

				$scope.isFollowing = !$scope.isFollowing
			}

			$scope.followSentiment = function() {

				var follow = {
					"componentId" : $scope.sentiment.id,
					"componentType" : "sentiment",
					"userId" : 1,
				};
				$http.post('/follow/follow', JSON.stringify(follow)).then(
				function(response) {

					++$scope.followCount;
					$scope.followUnfollow = "Unfollow"
				})

			}

			$scope.unFollowSentiment = function() {

				var unfollow = {
					"componentId" : $scope.sentiment.id,
					"componentType" : "sentiment",
					"userId" : 1,
				};
				$http.post('/follow/unfollow', JSON.stringify(unfollow)).then(
				function(response) {

					--$scope.followCount;
					$scope.followUnfollow = "Follow"
				})

			}

			$scope.shareSentiment = function(platform) {

				var share = {
					"componentId" : $scope.sentiment.id,
					"componentType" : "sentiment",
					"userId" : 1,
					"platform" : platform
				};

				if (platform === 'facebook') {
					FB
					.ui(
					{
						method : 'feed',
						name : $scope.sentiment.subject,
						link : 'http://localhost:8080/displaySentiment.html#/sentiment/1',
						caption : $scope.sentiment.subject,
						description : $scope.sentiment.description,
						message : $scope.sentiment.subject
					}, function(response) {

						if (response && response.post_id) {
							$scope.facebookShareCount++;
							$http.post('/componentshare/share',
							JSON.stringify(share)).then(
							function(response) {

							})
						}
					});
				} else if (platform === 'twitter') {

					$scope.twitterShareCount++;

					$http.post('/componentshare/share', JSON.stringify(share))
					.then(function(response) {

					})
				}

			}

			$window.twttr.ready(function(twttr) {

				twttr.events.bind('tweet', function(event) {

					$scope.shareSentiment('twitter');
				});
			});
		} ]);

viewSentiment
.directive(
'sentimentVerdict',
function() {

	return {
		restrict : 'E',
		replace : true,

		template : '<div id="chartdiv" style="min-width: 310px; height: 400px; margin: 0 auto"></div>',
		link : function(scope, element, attrs) {

			var chart = false;

			var initChart = function() {

				if (chart)
					chart.destroy();
				var config = scope.config || {};
				chart = AmCharts.makeChart("chartdiv", {
					"type" : "serial",
					"theme" : "light",
					"dataProvider" : [ {
						"vote_type" : "Favour",
						"vote_percentage" : 40,
						"color" : "#FF9933"
					}, {
						"vote_type" : "Against",
						"vote_percentage" : 60,
						"color" : "#BEF781"
					} ],
					"valueAxes" : [ {
						"gridColor" : "#FFFFFF",
						"gridAlpha" : 0.2,
						"dashLength" : 0
					} ],
					"gridAboveGraphs" : true,
					"startDuration" : 1,
					"graphs" : [ {
						"balloonText" : "[[category]]: <b>[[value]]</b>",
						"colorField" : "color",
						"fillAlphas" : 0.8,
						"lineAlpha" : 0.2,
						"type" : "column",
						"valueField" : "vote_percentage"
					} ],
					"chartCursor" : {
						"categoryBalloonEnabled" : false,
						"cursorAlpha" : 0,
						"zoomable" : false
					},
					"categoryField" : "vote_type",
					"categoryAxis" : {
						"gridPosition" : "start",
						"gridAlpha" : 0,
						"tickPosition" : "start",
						"tickLength" : 20
					},
					"export" : {
						"enabled" : true
					}

				});

			};
			initChart();

		}
	}
});

viewSentiment
.directive(
"indicators",
function() {

	var tpl = '<ul  ng-repeat="indicator in indicators">'
	+ '  <li>'
	+ '    {{indicator.name}} <input id={{indicator.id}} type ="radio" name="Indicator" value={{indicator.name}} indicator-click>'
	'  </li>' + '</ul>';

	return {
		restrict : "E",
		template : tpl

	}
});

// Directive for adding buttons on click that show an alert on click
viewSentiment.directive("indicatorClick", function($compile) {

	return function(scope, element, attrs) {

		element.bind("click", function() {

			scope.count++;
			angular.element(document.getElementById('gpi-graph')).replaceWith(
			$compile(
			"<indicator-graph id='" + attrs.id + "' data='data1' title='" + attrs.value
			+ "'></indicator-graph>")(scope));
		});
	};
});

viewSentiment
.directive(
'indicatorGraph',
[
		'$http',
		'$q',
		function($http, $q) {

			var directiveData, dataPromise;
			function initChart($scope) {

				var chart = false;
				if (chart)
					chart.destroy();
				var config = $scope.config || {};
				chart = AmCharts
				.makeChart(
				"chartdiv",
				{
					type : "stock",
					"theme" : "none",
					"dataDateFormat" : "YYYY-MM-DD",
					addClassNames : true,
					startDuration : 1,
					"precision" : 2,
					categoryAxesSettings : {
						minPeriod : $scope.data.messages[0].chartMeta.xaxislabel
					},

					dataSets : [ {
						color : "#b0de09",
						fieldMappings : [ {
							fromField : "score",
							toField : "score"
						} ],
						dataProvider : $scope.data.messages[0].data,
						categoryField : "timestamp"
					} ],

					panels : [ {
						showCategoryAxis : true,
						title : $scope.data.messages[0].chartMeta.title,
						eraseAll : false,

						"export" : {
							"enabled" : true
						},

						stockGraphs : [ {
							id : "g1",
							valueAxis : "v12",
							valueField : "score",
							"bullet" : "round",
							"bulletBorderAlpha" : 1,
							"bulletColor" : "#F4E868",
							"bulletSizeField" : "milestone",
							"hideBulletsCount" : 50,
							"lineThickness" : 2,
							"lineColor" : "#20acd4",
							"type" : "smoothedLine",
							"title" : "Scope",
							"useLineColorForBulletBorder" : true,
							"valueField" : "score",
							"urlField" : "url",
							"balloonText" : "<b style='font-size: 130%'>[[milestoneType]]</b></br>[[description]]",

							useDataSetColors : false
						} ],

						stockLegend : {
							valueTextRegular : " ",
							markerType : "none"
						},

					} ],

					chartScrollbarSettings : {
						graph : "g1"
					},
					chartCursorSettings : {
						valueBalloonsEnabled : true
					},
					periodSelector : {
						position : "top",
						periods : [ {
							period : "DD",
							count : 10,
							label : "10 days"
						}, {
							period : "MM",
							count : 1,
							label : "1 month"
						}, {
							period : "YYYY",
							count : 1,
							label : "1 year"
						}, {
							period : "YTD",
							label : "YTD"
						}, {
							period : "MAX",
							label : "MAX"
						} ]
					},

				});
			}
			function loadData(indicator, indicatorId) {

				// alert('loaddata'+indicator);
				var deferred = $q.defer();
				dataPromise = deferred.promise;
				return $http.get(
				'http://localhost:8080/chart/getChart/?ChartID=GAChartService&ChartSecondaryID='
				+ indicator + '&id=' + indicatorId).then(function(response) {

					return response;
				}, function(response) {

				});

			}

			return {
				restrict : 'EA',
				scope : {
					data : '=',
					title : '@',
					id : '@'
				},
				template : '<div id="chartdiv" style="min-width: 310px; height: 400px; margin: 0 auto"></div>',
				link : function($scope, element, attrs) {

					var chart = false;

					i = 0;
					// alert();
					loadData($scope.title, $scope.id).then(
					function(data) {

						$scope.data = data.data;
						$scope.$watch($scope.data, function(newValue,
						oldValue) {

							initChart($scope);

						}, true);

					}, function() {

					})

				}
			}
		} ]);
// Facebook SDK integration
viewSentiment
.run(function($rootScope, $window) {

	// code to initialise Facebook SDK
	$window.fbAsyncInit = function() {

		FB.init({
			appId : '680245352113384',
			xfbml : true,
			version : 'v2.6'
		});
	};

	(function(d, s, id) {

		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {
			return;
		}
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/en_US/all.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

	// for share button
	(function(d, s, id) {

		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.6&appId=680245352113384";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
});

// initialising Twitter
viewSentiment.run(function($rootScope, $window) {

	// code to initialise Twitter SDK
	$window.twttr = (function(d, s, id) {

		var t, js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "https://platform.twitter.com/widgets.js";
		fjs.parentNode.insertBefore(js, fjs);
		return window.twttr || (t = {
			_e : [],
			ready : function(f) {

				t._e.push(f)
			}
		});
	}(document, "script", "twitter-wjs"));
});
