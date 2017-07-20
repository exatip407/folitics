(function(angular) {

	'use strict';

	function ResponseService($http, $q) {
		this.post = function(response) {
			
		$http.post('/response/add/', JSON.stringify(response)).then(
					function(resp) {
						//alert('response');
					});
		}

	}

	ResponseService.$inject = [ '$http' , '$q' ];

	angular.module('governProject.response').service('ResponseService',
			ResponseService);

})(window.angular);