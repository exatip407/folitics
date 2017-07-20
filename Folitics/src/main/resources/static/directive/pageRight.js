(function(angular) {

	'use strict';

	function pageRight() {

		return {
			restrict : 'A',
			templateUrl : "right.html"
		};
	}

	pageRight.$inject = [];

	angular.module('governProject').directive('pageRight', pageRight);

})(window.angular);