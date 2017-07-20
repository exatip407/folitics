var myapp = angular.module('governProject');
myapp
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
				console.log(JSON.stringify($scope.data.messages[0].chartMeta));
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
						minPeriod : $scope.data.messages[0].chartMeta[1]
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
						title : $scope.data.messages[0].chartMeta[2],
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