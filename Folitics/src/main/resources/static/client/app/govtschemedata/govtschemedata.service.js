(function(angular) {

	'use strict';

	function GovtschemedataService($http, $q) {

		this.getGovtschemedata = function() {

			//var response = $http.get("/poll/getIsolatedPolls");
			return response;
		};

	}

	GovtschemedataService.$inject = [ '$http', '$q' ];

	angular.module('governProject.govtschemedata').service('GovtschemedataService', GovtschemedataService);

})(window.angular);