
(function(angular) {

	'use strict';

	function GovtschemedataController($http, $scope, GovtschemedataService) {
		
		$http.get('/govtschemedata/getall').then(function(response) {
          //  alert("hiii");
			if (response.data.messages != null) {
				$scope.govtschemedata = response.data.messages;
				console.log($scope.govtschemedata);
			}
		});
	
	}

	GovtschemedataController.$inject = [ '$http', '$scope', 'GovtschemedataService' ];

	angular.module('governProject.govtschemedata').controller('GovtschemedataController', GovtschemedataController);

})(window.angular);