(function(angular) {

	'use strict';

	function pageFooter() {

		return {
			  restrict : 'A',
			  templateUrl: "footer.html"
			 };
	}

	pageFooter.$inject = [];

	angular.module('governProject').directive('pageFooter', pageFooter);

})(window.angular);