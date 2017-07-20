(function(angular) {

	'use strict';

	function LocalVerdictService($http, $q) {
		this.getGlobalOverAllverdict = function(){
			var promise = $http.get('/globalVerdict/getGlobalVerdict').then(
					function(response) {

				if (response.data != null) {
					 return response.data.messages[0];
				}
			});
			return promise;
		};
		
		this.getIndicatorChange = function(){
			var promise = $http.get('/indicatordata/getIndicatorDataForVerdict').then(
					function(response) {

				if (response.data != null) {
					 return response.data.messages;
				}
			});
			return promise;
		};
	}

	LocalVerdictService.$inject = [ '$http', '$q' ];

	angular.module('governProject.localverdict').service('LocalVerdictService',
			LocalVerdictService);

})(window.angular);