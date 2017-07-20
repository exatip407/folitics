(function(angular) {

	'use strict';

	function QuickProblemController($http, $scope, $rootScope, $location,
			QuickProblemService) {

		$scope.quickProblem = {
			quickProblemPersonality : []

		};
		$scope.quickpersonalities1 = [];

		$http.get('/quickProblem/getAllQuickPersonality').then(
				function(response) {
					if (response.data.messages != null) {
						$scope.quickpersonalities = response.data.messages[0];

					}
				});

		$scope.quickProblem = QuickProblemService.quickProblem;

		$scope.submit = function() {
			//alert("submit");

			for (var i = 0; i < $scope.quickpersonalities1.length; i++) {
				var quickProblemPersonalities1 = {

					quickpersonality : $scope.quickpersonalities[i]
				};
				$scope.quickProblem.quickProblemPersonality
						.push(quickProblemPersonalities1);
			}
			;

			QuickProblemService.post($scope.quickProblem, $scope.file,
					$rootScope.sessionUser.id);
		};

		$scope.reload = function() {

			$location.path("").reload(false);

		}
	}

	QuickProblemController.$inject = [ '$http', '$scope', '$rootScope',
			'$location', 'QuickProblemService' ];

	angular.module('governProject.quickproblem').controller(
			'QuickProblemController', QuickProblemController).directive(
			'myEnter', function() {

				return function(scope, element, attrs) {

					element.bind("keydown keypress", function(event) {

						if (event.which === 13) {
							if (!scope.$$phase) {
								scope.$apply(function() {

									scope.$eval(attrs.myEnter);
								});
							}
							event.preventDefault();
						}
					});
				};
			});

})(window.angular);