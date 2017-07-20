/**
 * ASLMRQClick
 */
(function(angular) {

	'use strict';

	function aslmrqClick($parse,$compile) {
		return function(scope, element, attrs){
			element.bind("click", function(){
				scope.count++;
				angular.element(document.getElementsByTagName('global-verdict')).replaceWith($compile("<global-verdict id='{{sentimentId}}' data='data1' title='"+attrs.value+"'></global-verdict>")(scope));
			});
		};
	}

	aslmrqClick.$inject = [ '$parse' ,'$compile'];

	angular.module('governProject').directive('aslmrqClick', aslmrqClick);

})(window.angular);