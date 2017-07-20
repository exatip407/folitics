(function (angular) {
    'use strict';
      

    function AppController($window,$http,$scope,ElasticsearchService,esFactory) {
     // your code
	    $scope.getLocation = function(val) {
		    alert('as');
		    return ElasticsearchService.search({
				  index: 'megacorp',
				  type: 'employee',
				  body: {
					  query: {
						    query_string: {
						        query: '*'+val+'*',
						        fields: ['*_name']
						    }
						}
				  }
				}).then(function (resp) {
				    console.log(JSON.stringify(resp.hits.hits));
				    return resp.hits.hits;
				}, function (err) {
				    console.trace(err.message);
				});
		  };  
		  $scope.test = function(){
			  alert('call');
		  }
		  alert('call');
    }

    AppController.$inject = ['$window','$http','$scope','ElasticsearchService','esFactory'];

    angular.module('governProject').controller('AppController', AppController);

})(window.angular);