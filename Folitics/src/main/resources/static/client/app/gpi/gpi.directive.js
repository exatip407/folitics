var myapp = angular.module('governProject.gpi');
myapp
.directive(
'myelem',
[
		'$http',
		'$q',
		function($http, $q) {

			var directiveData, dataPromise;
			function initChart($scope) {

				data1 = $scope.data.messages[0].data;
				// alert('chart');
				// alert(JSON.stringify(data1));

				var chart = false;
				if (chart)
					chart.destroy();
				var config = $scope.config || {};
				chart = AmCharts
				.makeChart(
				"gpidiv",
				{

					type : "stock",
					"theme" : "none",
					"dataDateFormat" : "YYYY-MM-DD",
					addClassNames : true,
					startDuration : 1,
					"precision" : 2,
					categoryAxesSettings : {
						minPeriod : "DD"
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
						title : "GPIs",
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
							"balloonText" : "<div class='col-md-4'><img style='width: 30px; height: 40px;' src='data:image/JPEG;base64,[[image]]' alt='milestone image'> </div> <div class='col-md-8'><b style='font-size: 130%'>[[milestoneType]]</b></br>[[description]]</div>",

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
			function loadData() {

				// alert('das');

				var deferred = $q.defer();
				dataPromise = deferred.promise;
				return $http
				.get(
				'/chart/getChart/?ChartID=GPIChartService&ChartSecondaryID=GPI&id=1')
				.then(function(response) {

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
				template : '<div id="gpidiv" style="min-width: 100%; height: 300px; margin: 0 auto"></div>',
				link : function($scope, element, attrs) {

					var chart = false;

					i = 0;

					$scope.data1 = loadData().then(
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