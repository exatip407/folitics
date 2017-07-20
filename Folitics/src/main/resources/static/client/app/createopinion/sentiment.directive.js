(function(angular) {

	'use strict';

	function Sentiment($parse) {

		return {
			
				restrict : 'A',
				link : function(scope, element, attrs) {
					var model = $parse(attrs.fileModel);
					var modelSetter = model.assign;

					element.bind('change', function() {
						scope.$apply(function() {
							modelSetter(scope, element[0].files[0]);
						});
					});
				}
			};
	}

	Sentiment.$inject = [ '$parse' ];

	angular.module('governProject').directive('Sentiment', Sentiment);

})(window.angular);