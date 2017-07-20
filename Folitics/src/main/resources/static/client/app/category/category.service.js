(function(angular) {

	'use strict';

	function CategoryService($http, $q) {

		this.getAllIndicator = function() {

			var promise = $http.get('/category/getActiveIndicators').then(
			function(response) {

				if (response.data != null) {
					return response.data.messages;
				}
			});

			return promise;
		};

	}

	CategoryService.$inject = [ '$http', '$q' ];

	angular.module('governProject.category').service('CategoryService', CategoryService);

})(window.angular);