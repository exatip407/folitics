(function(angular) {

	'use strict';

	function pageHeader() {

		return {
			restrict : 'A',
			templateUrl : "header.html"
		};
	}

	pageHeader.$inject = [];

	angular.module('governProject').directive('pageHeader', pageHeader);

})(window.angular);