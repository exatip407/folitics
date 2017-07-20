(function(angular) {

	'use strict';

	function pageLeft() {

		return {
			restrict : 'A',
			templateUrl : "left.html"
		};
	}

	pageLeft.$inject = [];

	angular.module('governProject').directive('pageLeft', pageLeft);

})(window.angular);