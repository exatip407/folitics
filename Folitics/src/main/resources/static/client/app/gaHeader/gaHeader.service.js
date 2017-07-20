(function(angular) {

	'use strict';

	function gaHeaderService($http, $q) {
		
		this.getData = function(){
		
		var promise = $http.get('/govtschemedata/getall').then(function(resp){
			
			if(resp.data!=null){
				return resp.data.messages;
			}	
		});
		return promise;
		};
		
			this.categories = function() {
				
		};
	}

	gaHeaderService.$inject = [ '$http', '$q' ];

	angular.module('governProject.gaHeader').service('gaHeaderService', gaHeaderService);

})(window.angular);