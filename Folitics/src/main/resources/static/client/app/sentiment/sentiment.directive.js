var myapp = angular.module('governProject');
myapp
		.directive(
				'sentimentVerdict',
				[
						'$http',
						'$q',
						'$routeParams',
						function($http, $q, $routeParams) {
							var directiveData, dataPromise;
							// alert('chart123');
							function initChart($scope) {
								var chart = false;

								if (chart)
									chart.destroy();
								var config = $scope.config || {};
								var favourPercentage = 0, againstPercentage = 0;

								favourPercentage = ($scope.data.favorPoints / ($scope.data.favorPoints + $scope.data.againstPoints)) * 100;
								againstPercentage = ($scope.data.againstPoints / ($scope.data.favorPoints + $scope.data.againstPoints)) * 100
								console.log(favourPercentage);
								console.log(againstPercentage);
								chart = AmCharts
										.makeChart(
												"sentimentdiv",
												{
													"type" : "serial",
													"theme" : "light",
													"dataProvider" : [
															{
																"vote_type" : "Favour",
																"vote_percentage" : favourPercentage,
																"color" : "#FF9933"
															},
															{
																"vote_type" : "Against",
																"vote_percentage" : againstPercentage,
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
							}
							function loadData(anchorId) {
								// alert('ji');
								var deferred = $q.defer();
								dataPromise = deferred.promise;
								return $http.get(
										'/sentimentOpinionStat/find?id='
												+ anchorId).then(
										function(response) {
											// alert('hi');
											return response;
										}, function(response) {
										});

							}

							return {
								restrict : 'EA',
								scope : {
									data : '=?',
									title : '@'
								},
								template : '<div id="sentimentdiv" style="min-width:100%;height: 400px; margin: 0 auto"></div>',
								link : function($scope, element, attrs) {
									// alert('call');
									var chart = false;
									i = 0;
									// alert('call2');
									$scope.data1 = loadData(
											$routeParams.sentimentId)

											.then(
													function(response) {
														console
																.log(JSON
																		.stringify(response.data));
														// alert('hello');
														if (response.data.messages !== null) {
															console
																	.log(JSON
																			.stringify(response.data.messages));
															$scope.data = response.data.messages[0];
															// alert('data');
															$scope
																	.$watch(
																			$scope.data,
																			function(
																					newValue,
																					oldValue) {
																				initChart($scope);

																			},
																			true);
														}
													}, function() {

													})
								}
							}
						} ]);