var myapp = angular.module('governProject');
myapp
.directive(
'globalVerdict',
[
		'$http',
		'$q',
		'$parse',
		function($http, $q, $parse) {

			var directiveData, dataPromise;
			function initChart($scope) {

				// data1 = $scope.data.messages[0].data;
				// alert('chart');
				// alert(JSON.stringify(data1));

				var chart = false;
				if (chart)
					chart.destroy();
				var config = $scope.config || {};
				chart = AmCharts
				.makeChart(
				"globalVerdictdiv",
				{

					"type" : "serial",
					"theme" : "light",
					"titles" : [
							{
								"text" : $scope.data.messages[0].chartMeta[2]
							},
							{
								"text" : "Verdict "
								+ $scope.data.messages[0].chartMeta[2],
								"bold" : false
							} ],
					"dataProvider" : $scope.data.messages[0].verdictData,
					"valueAxes" : [ {
						"gridColor" : "#FFFFFF",
						"gridAlpha" : 0.2,
						"dashLength" : 0
					} ],
					"gridAboveGraphs" : true,
					"startDuration" : 1,
					"graphs" : [ {
						"balloonText" : "[[flag]]: <b>[[value]]%</b>",
						"colorField" : "color",
						"fillAlphas" : 0.8,
						"lineAlpha" : 0.2,
						"type" : "column",
						"valueField" : "yAxisValue"
					} ],
					"chartCursor" : {
						"categoryBalloonEnabled" : false,
						"cursorAlpha" : 0,
						"zoomable" : false
					},
					"categoryField" : "xAxis",
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
			function loadData(title,id) {

				var deferred = $q.defer();
				dataPromise = deferred.promise;
				return $http.get(
				'/chart/getChart?ChartID=verdictChartService&ChartSecondaryID='
				+ title + '&id='+id).then(function(response) {

					return response;
				}, function(response) {

				});

			}

			return {
				restrict : 'EA',
				scope : {
					data : '=?',
					title : '@',
					id:'@'
				},
				template : '<div id="globalVerdictdiv" style="min-width: 100%; height: 300px; margin: 0 auto"></div>',
				link : function($scope, element, attrs) {

					var chart = false;

					i = 0;

					$scope.data1 = loadData($scope.title,$scope.id).then(
					function(data) {

						// alert(JSON.stringify(data.data.messages[0].chartMeta));
						$scope.data = data.data;
						// alert('inside
						// '+JSON.stringify($scope.data));
						initChart($scope);
						$scope.$watch($scope.data, function(newValue,
						oldValue) {

							// alert('inside
							// '+JSON.stringify($scope.data));

							initChart($scope);

						}, true);

					}, function() {

					})

				}
			}
		} ]);