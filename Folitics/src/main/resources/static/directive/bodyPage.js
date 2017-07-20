(function(angular) {

	'use strict';

	function bodyPage() {

		return {
			restrict : 'A',
			templateUrl : "bodyPage.html"
		};
	}

	bodyPage.$inject = [];

	angular.module('governProject').directive('bodyPage', bodyPage);

})(window.angular);