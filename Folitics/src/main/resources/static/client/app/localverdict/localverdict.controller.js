(function(angular) {

	'use strict';

	function LocalVerdictController($http, $scope, $routeParams,
			LocalVerdictService, CategoryService) {
		$scope.getPrerequisite = function(){
			LocalVerdictService.getGlobalOverAllverdict().then(function(globalOverAllVerdict){
				$scope.globalOverAllVerdict = globalOverAllVerdict;
			});
		};
		
		$scope.indicators = [  {
			'name' : 'Sex',
			value : 'globalSexVerdictService'
		}, {
			'name' : 'Age',
			value : 'globalVerdictAgeGroup'
		}, {
			'name' : 'Marital status',
			value : 'globalMaritalStatusVerdictService'
		}, {
			'name' : 'Region',
			value : 'globalRegionVerdictService'
		}, {
			'name' : 'Religion',
			value : 'globalReligionVerdictService'
		}, {
			'name' : 'Qualification',
			value : 'globalQualificationVerdictService'
		} ];
		
		LocalVerdictService.getIndicatorChange().then(function(indicators) {
			$scope.changeIndicators = indicators;
		});
		CategoryService.getAllIndicator().then(function(indicators) {
			$scope.promiseIndicators = indicators;
		});
	}

	LocalVerdictController.$inject = [ '$http', '$scope', '$routeParams',
			'LocalVerdictService', 'CategoryService' ];

	angular.module('governProject.localverdict').controller(
			'LocalVerdictController', LocalVerdictController);

})(window.angular);