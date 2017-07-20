/**
 * 
 */
(function(angular) {

	'use strict';

	function promiseIndicators($parse) {
		 var tpl = '<ul  ng-repeat="indicator in promiseIndicators">'+
		    '  <li>' +
		    
		      '    <span id={{indicator.id}} class="nav-btns-pro" name="Indicator" value={{indicator.name}} promise-indicator-click>{{indicator.name}}<i class="glyphicon glyphicon-menu-right"></i></span>'
		    '  </li>' +
		    '</ul>';
		
		return {
			restrict: "E",
			template : tpl

		}
	}

	promiseIndicators.$inject = [ '$parse' ];

	angular.module('governProject').directive('promiseIndicators', promiseIndicators);

})(window.angular);