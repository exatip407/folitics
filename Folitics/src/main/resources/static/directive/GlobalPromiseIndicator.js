/**
 * 
 */
var myapp = angular.module('governProject');
myapp
.directive(
'globalPromiseIndicator',
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
				"globalPromiseIndicatorDiv",
				{ type: "stock",
					  "theme": "light",
						
					  "dataSets": [ {
					    "fieldMappings": [ {
					      "fromField": "score",
					      "toField": "score"
					    }, {
					      "fromField": "scoreCompare",
					      "toField": "scoreCompare"
					    }],
					    "dataProvider": $scope.data.messages[0].data,
					    "categoryField": "timestamp"
					  } ],

					  "panels": [ {
					    "stockGraphs": [ {
					      "id": "g1",
					      "title": "Graph #1",
					      "lineThickness": 2,
					      "valueField": "score",
					      "useDataSetColors": false
					    }, {
					      "id": "g2",
					      "title": "Graph #2",
					      "lineThickness": 2,
					      "valueField": "scoreCompare",
					      "useDataSetColors": false
					    } ]
					  } ],

					  "chartScrollbarSettings": {
					    "graph": "g1"
					  },

					  "chartCursorSettings": {
					    "valueBalloonsEnabled": true
					  },

					  "periodSelector": {
					    "position": "bottom",
					    "periods": [ {
					      "period": "MM",
					      "selected": true,
					      "count": 1,
					      "label": "1 month"
					    }, {
					      "period": "YYYY",
					      "count": 1,
					      "label": "1 year"
					    }, {
					      "period": "YTD",
					      "label": "YTD"
					    }, {
					      "period": "MAX",
					      "label": "MAX"
					    } ]
					  }});
			}
			function loadData(id) {

				var deferred = $q.defer();
				dataPromise = deferred.promise;
				return $http.get(
				'/chart/getChart?ChartID=globalVerdictChartService&ChartSecondaryID=globalVerdictPromiseIndicator&id='+id).then(function(response) {

					return response;
				}, function(response) {

				});

			}

			return {
				restrict : 'EA',
				scope : {
					data : '=?',
					title : '@',
					id : '@'
				},
				template : '<div id="globalPromiseIndicatorDiv" style="min-width: 100%; height: 300px; margin: 0 auto"></div>',
				link : function($scope, element, attrs) {

					var chart = false;

					i = 0;

					$scope.data1 = loadData($scope.id).then(
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