(function(angular) {

	'use strict';

	function SearchController($window, $http, $timeout, $scope, $rootScope,
			$resource, userService, SearchService, ElasticsearchService,
			esFactory) {

		$scope.selected = undefined;

		$scope.click = function() {
			alert("test 1 passed");
			SearchService.searchText();
			$window.location.href = '/#/' + $scope.module + '';
			$rootScope.users = resp.hits.hits;

			/*SearchHit[response] = SearchService.searchDocument("module",'"' + $scope.module + '"');
			$window.location.href = '/#/' + $scope.module + '';
			$rootScope.users = {}*/
			/*$scope.user = userService.user;
			ElasticsearchService.search({
				index : 'module',
				type : $scope.module,
				body : {
					query : {
						match : {
							
							query : '*' + $scope.customSelected + '*',
							fields :  ['username']
							
						}
					}

				}
			}).then(function(resp) {

				$window.location.href = '/#/' + $scope.module + '';
				$rootScope.users = resp.hits.hits;
				// return resp.hits.hits;
			}, function(err) {

				console.trace(err.message);
			});*/
		}

		var promise;
		$scope.SearchResults = function(selected) {
			if (promise)
				$timeout.cancel(promise);

			promise = $timeout(function() {
				$scope.states = [ 'a', 'aa', 'aaa' ]
				alert(selected);
			}, 500);
		}

		/*
		 * $scope.getLocation = function(val) { return
		 * $http.get('//maps.googleapis.com/maps/api/geocode/json', { params: {
		 * address: val, sensor: false } }).then(function(response){ return
		 * response.data.results.map(function(item){ return
		 * item.formatted_address; }); }); };
		 */
		/*
		 * $scope.getLocation = function(val) { return
		 * ElasticsearchService.search({ index: 'megacorp', type: 'employee',
		 * body: { query: { query_string: { query: '*'+val+'*', fields:
		 * ['*_name'] } } } }).then(function (resp) {
		 * console.log(JSON.stringify(resp.hits.hits)); return resp.hits.hits; },
		 * function (err) { console.trace(err.message); }); };
		 */

		// $scope.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas',
		// 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida',
		// 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa',
		// 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland',
		// 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri',
		// 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New
		// Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio',
		// 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South
		// Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
		// 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];
		/*
		 * $scope.startsWith = function (state, viewValue) { return
		 * state.substr(0, viewValue.length).toLowerCase() ==
		 * viewValue.toLowerCase(); }
		 * 
		 * $scope.sentiments = [ { "subject" : "setiment", "description" :
		 * "description" } ];
		 */
		/*
		 * ElasticsearchService.search({ index: 'megacorp', type: 'employee',
		 * body: { query: { query_string: { query: '*sm*', fields: ['*_name'] } } }
		 * }).then(function (resp) {
		 * console.log(JSON.stringify(resp.hits.hits)); }, function (err) {
		 * console.trace(err.message); });
		 */

		/*
		 * $scope.searchRequest = { "query" : { "query_string" : { "query" :
		 * "*smi*", "fields" : [ "*_name" ] } } }; "query" : '"*' +
		 * $rootScope.textToSearch + '*"',
		 * 
		 * $rootScope.search = function() {
		 * 
		 * console.log("Search request ", JSON.stringify($scope.searchRequest));
		 * //$http.defaults.useXDomain = true;
		 * $http.post('http://localhost:9200/megacorp/employee/_search?pretty',
		 * JSON.stringify($scope.searchRequest)).then(function(resp) {
		 * 
		 * console.log(resp.data);
		 * 
		 * }); console.log("Search request ",
		 * JSON.stringify($scope.searchRequest)); $scope.test =
		 * $resource('http://localhost:9200/megacorp/employee/_search?pretty',
		 * JSON.stringify($scope.searchRequest)); console.log('end'); };
		 * 
		 * $rootScope.postData = function() { // Set the Content-Type
		 * //$http.defaults.useXDomain = true;
		 * 
		 * $http.defaults.headers.post["Content-Type"] =
		 * "application/x-www-form-urlencoded"; // Delete the Requested With
		 * Header delete $http.defaults.headers.common['X-Requested-With'];
		 * 
		 * var data = $.param({ json: JSON.stringify($scope.searchRequest) });
		 * 
		 * $http({ url:
		 * "http://localhost:9200/megacorp/employee/_search?pretty", method:
		 * "POST", data: data }).success(function(data, status) {
		 * $scope.response = "POST Response: " + data.form.json;
		 * console.log(JSON.stringify(data));
		 * 
		 * }); };
		 */

	}

	SearchController.$inject = [ '$window', '$http', '$timeout', '$scope',
			'$rootScope', '$resource', 'SearchService', 'userService',
			'ElasticsearchService', 'esFactory' ];

	angular.module('governProject.search',
			[ 'ngResource', 'ngAnimate', 'ui.bootstrap' ]).controller(
			'SearchController', SearchController);

})(window.angular);