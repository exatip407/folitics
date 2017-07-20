(function(angular) {

	'use strict';

	function GpiService($http, $q) {

		this.getgpi = function() {

			//var response = $http.get("/poll/getIsolatedPolls");
			//return response;
		};

	}

	GpiService.$inject = [ '$http', '$q' ];

	angular.module('governProject.gpi').service('GpiService', GpiService);

})(window.angular);