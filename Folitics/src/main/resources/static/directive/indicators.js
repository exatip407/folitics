/**
 * All indicator directive
 */
(function(angular) {

	'use strict';

	function indicators($parse) {

		var tpl = '<div class="col-lg-2" ng-repeat="indicator in indicators" >'
		+ '    <span id={{indicator.id}} class="nav-btns-pro" name="Indicator" value={{indicator.name}} indicator-click>{{indicator.name}}<i class="glyphicon glyphicon-menu-right"></i></span>'
		'</div>';

		return {
			restrict : "E",
			template : tpl

		}
	}

	indicators.$inject = [ '$parse' ];

	angular.module('governProject').directive('indicators', indicators);

})(window.angular);