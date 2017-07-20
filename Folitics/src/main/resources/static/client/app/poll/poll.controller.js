(function(angular) {

	'use strict';

	function PollController($http, $scope, PollService) {

/*	$http.get('/poll/find?id=1').then(function(response) {
           alert("working");
			if (response.data.messages != null) {
			$scope.viewpoll = response.data.messages[0];
			}
});*/
		

		$http.get('/poll/getAll').then(function(response) {
           alert("working");
		if (response.data.messages != null) {
				$scope.polls = response.data.messages;
		}	});
	
	}

	PollController.$inject = [ '$http', '$scope','PollService' ];

	angular.module('governProject.poll').controller('PollController', PollController);

})(window.angular);