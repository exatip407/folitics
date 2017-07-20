/**
 * ASLMRQList
 */
(function(angular) {

	'use strict';

	function aslmrqList($parse) {

		var tpl =  ' <div class="col-lg-2" ng-repeat="indicator in indicators">'
		+ '    <span id={{indicator.id}} class="nav-btns-pro" name="Indicator" value={{indicator.value}} aslmrq-click>{{indicator.name}}<i class="glyphicon glyphicon-menu-right"></i></span>'
		' </div>' ;

		
		return {
			restrict : "E",
			template : tpl

		}
	}

	aslmrqList.$inject = [ '$parse' ];

	angular.module('governProject').directive('aslmrqList', aslmrqList);

})(window.angular);