﻿(function(angular) {

	'use strict';

	function SearchService($http, $q , ElasticsearchService) {

		var searchRequest;
		
		/*this.searchText = function(index, text) {
			alert("check" + index);
			alert("check" + text);
			var promise = $http.get(
					'http://localhost:8080/search/search?index=module&type='
							+ index + '&text=' + text).then(function(resp) {
				alert("search function called");
				return resp.hits.hits;
			});
			return promise;
		}*/

		
		this.searchText = function(index,text) {

			if(index === "user"){
			var promise =  ElasticsearchService.search(
					{
						index : 'module',
						type : index,
						body : {
							query : {
								query_string : {
									query : '*' +text
									+ '*',
									fields : [ 'username' , 'emailId' , 'description' ]
								},
								query_string : {
									query : '' +text
									+ '*',
									fields : [ 'username' , 'emailId' , 'description' ]
								}
							}
						}
					}).then(function(resp) {

						return resp.hits.hits;
					}, function(err) {

						console.trace(err.message);
					});
			}
			
			if(index === "sentiment"){
				var promise =  ElasticsearchService.search(
						{
							index : 'module',
							type : index,
							body : {
								query : {
									query_string : {
										query : '*' +text
										+ '*',
										fields : [ 'subject' , 'description' ]
									}
								}
							}
						}).then(function(resp) {

							return resp.hits.hits;
							/* return resp.hits.hits; */
						}, function(err) {

							console.trace(err.message);
						});
				}
			
			if(index === "task"){
				var promise =  ElasticsearchService.search(
						{
							index : 'module',
							type : index,
							body : {
								query : {
									query_string : {
										query : '*' +text
										+ '*',
										fields : [ 'subject' , 'emailId' , 'description' ]
									}
								}
							}
						}).then(function(resp) {

							return resp.hits.hits;
							/* return resp.hits.hits; */
						}, function(err) {

							console.trace(err.message);
						});
				}
			return promise;
			
		};
			/*if(index === "user"){
			this.searchRequest = { "query" : { "query_string" : { "query" :
				  '"*'+text+'*"', "fields" : [ "username" ] } } };
			}
			
			if(index === "sentiment"){
				this.searchRequest = { "query" : { "query_string" : { "query" :
					  '"*'+text+'*"', "fields" : [ "subject" ] } } };
				}
			
			console.log("gpiId by combo ", JSON.stringify(this.searchRequest));
			
			var promise =$http.post('http://localhost:9200/module/'+index+'/_search?pretty',
					  JSON.stringify(searchRequest)).then(function(resp) {

						if (resp.data != null) {
							alert(resp.data.hits.hits);
							return resp.data.hits.hits;
						}
					});

			return promise;*/
		
	}

	SearchService.$inject = [ '$http', '$q' , 'ElasticsearchService'];

	angular.module('governProject.search').service('SearchService',
			SearchService);

})(window.angular);