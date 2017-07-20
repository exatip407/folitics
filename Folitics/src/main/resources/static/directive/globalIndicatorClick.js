/**
 * 
 */
(function(angular) {

	'use strict';

	function globalIndicatorClick($parse,$compile) {
		return function(scope, element, attrs){
			element.bind("click", function(){
				scope.count++;
				angular.element(document.getElementsByTagName('global-overall-verdict')).replaceWith($compile("<global-overall-verdict id='1' data='data1' title='"+attrs.value+"'></global-overall-verdict>")(scope));
			});
		};
	}

	globalIndicatorClick.$inject = [ '$parse' ,'$compile'];

	angular.module('governProject').directive('globalIndicatorClick', globalIndicatorClick);

})(window.angular);