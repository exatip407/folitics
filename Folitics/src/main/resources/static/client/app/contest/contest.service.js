(function(angular) {

	'use strict';

	function ContestService($http, $q) {
		
		var contest = {};
		this.contest = {
			creationTime : "",
			description : "",
			expiryTime : "",
			name : "",
			state : "",
			termsAndCondition : "",
			luckyDrawId : [],
			userId : []
		};

	}

	ContestService.$inject = [ '$http', '$q' ];

	angular.module('governProject.contest').service('ContestService',ContestService);

})(window.angular);