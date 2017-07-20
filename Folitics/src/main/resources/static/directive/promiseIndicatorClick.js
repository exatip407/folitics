/**
 * 
 */
(function(angular) {

	'use strict';

	function promiseIndicatorClick($parse,$compile) {
		return function(scope, element, attrs){
			element.bind("click", function(){
				scope.count++;
				angular.element(document.getElementsByTagName('global-promise-indicator')).replaceWith($compile("<global-promise-indicator id='"+attrs.id+"' data='data1' title='"+attrs.value+"'></global-promise-indicator>")(scope));
			});
		};
	}

	promiseIndicatorClick.$inject = [ '$parse' ,'$compile'];

	angular.module('governProject').directive('promiseIndicatorClick', promiseIndicatorClick);

})(window.angular);