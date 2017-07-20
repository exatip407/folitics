(function(angular) {

	'use strict';

	function GpiController($http, $scope, GpiService) {

		

          alert("hii");
		$http.get('/sentiment/getAll').then(function(response) {
			alert("angular");
			console.log('Inside');
	    	$scope.sentiments = JSON.parse(JSON.stringify(response.data.messages));

		});
		console.log('Inside');
	}
	
	
	
	GpiController.$inject = [ '$http', '$scope','GpiService' ];

	angular.module('governProject.gpi').controller('GpiController', GpiController);

})(window.angular);