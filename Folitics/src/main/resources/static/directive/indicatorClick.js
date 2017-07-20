/**
 * Indicator click
 */
(function(angular) {

	'use strict';

	function indicatorClick($parse,$compile) {
		return function(scope, element, attrs){
			element.bind("click", function(){
				scope.count++;
				angular.element(document.getElementsByTagName('indicator-graph')).replaceWith($compile("<indicator-graph id='"+attrs.id+"' data='data1' title='"+attrs.value+"'></indicator-graph>")(scope));
			});
		};
	}

	indicatorClick.$inject = [ '$parse' ,'$compile'];

	angular.module('governProject').directive('indicatorClick', indicatorClick);

})(window.angular);