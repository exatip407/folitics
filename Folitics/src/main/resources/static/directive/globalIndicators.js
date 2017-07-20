/**
 * 
 */
(function(angular) {

	'use strict';

	function globalIndicators($parse) {
		 var tpl = '<ul  ng-repeat="indicator in indicators">'+
		    '  <li>' +
		      '    <span id={{indicator.id}} class="nav-btns-pro" name="Indicator" value={{indicator.value}} global-indicator-click>{{indicator.name}}<i class="glyphicon glyphicon-menu-right"></i></span'
		    '  </li>' +
		    '</ul>';
		
		return {
			restrict: "E",
			template : tpl

		}
	}
	

	globalIndicators.$inject = [ '$parse' ];

	angular.module('governProject').directive('globalIndicators', globalIndicators);

})(window.angular);