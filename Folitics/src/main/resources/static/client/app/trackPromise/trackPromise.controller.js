(function(angular) {

	'use strict';

	function trackPromiseController($http, $scope, trackPromiseService) {
		
	
		$http.get('/indicatordata/getPromiseData').then(function(response) {
			//alert("TracK Promises");
			console.log('Inside');
		$scope.categories = JSON.parse(JSON.stringify(response.data.messages));

		});
	}
	

	trackPromiseController.$inject = [ '$http', '$scope', 'trackPromiseService' ];

	angular.module('governProject.trackPromise').controller('trackPromiseController', trackPromiseController);

})(window.angular);