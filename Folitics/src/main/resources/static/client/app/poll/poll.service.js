(function(angular) {

	'use strict';

	function PollService($http, $q) {

		this.getPolls = function() {

			var response = $http.get("/poll/getIsolatedPolls");
			return response;
		};
		
		this.getPollResult = function(pollId,filter) {

			var promise = $http.get('/poll/getPollResult?id='+pollId+'&filter='+filter).then(
					function(response) {

						if (response.data != null) {
							return response.data.messages;
						}
					});

			return promise;
		};
		this.answerPoll = function(pollOptionId,userId) {

			var promise = $http.get('/poll/answerPoll?pollOptionId='+pollOptionId+'&userId='+userId).then(
					function(response) {

						if (response.data != null) {
							return response.data.success;
						}
					});

			return promise;
		};

	}

	PollService.$inject = [ '$http', '$q' ];

	angular.module('governProject.poll').service('PollService', PollService);

})(window.angular);