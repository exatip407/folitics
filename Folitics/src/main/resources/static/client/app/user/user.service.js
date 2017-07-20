(function(angular) {

	'use strict';

	function userService($http, $q) {

		var user = {};

		var _registration = {};

		this._registration = {
			username : "",
			password : "",
			name : "",
			emailId : "",
			status : "NotVerified",
			role : {
				id : 2,
				userRole : "USER"
			},
			mobileNo : "",
			maritalStatus : "",
			Gender : "",
			userAssociation : [],
			religion : "",
			hobbies : "",
			dob : "",
			country : "",
			state : "",
			city : "",
			occupation : "",
			motherTongue : "",
			userEducation : []

		};

		this.post = function(_registration, file) {

			$http(
					{
						method : 'POST',
						url : '/user/addUserMultipart',
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : function() {

							var formData = new FormData();
							formData.append('user', new Blob([ JSOG
									.stringify(_registration) ], {
								type : "application/json"
							}));
							formData.append("userImage", file);
							return formData;
						},
						data : {
							user : _registration,
							userImage : file
						}
					}).then(function(data, status, headers, config) {

			});
		};

		this.getUserByUsername = function(username) {

			var promise = $http
					.get('/user/findByUsername?username=' + username).then(
							function(response) {

								console.log(response);
								if (response.data.success == true) {
									return response.data.messages[0];
								}
							});

			return promise;
		};

		this.updateNotification = function(notificationId) {

			var promise = $http.get(
					'/updateNotificationreadStatus?notificationMongoId='
							+ notificationId).then(function(response) {

				return response.data.success;
			});

			return promise;
		};

		this.getUser = function(userId) {
			var promise = $http.get('/user/find?id=' + userId).then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages[0];
						}
					});

			return promise;
		};

		this.subscribeUser = function(userId) {
			var promise = $http.post('/user/subscribe?userId=' + userId).then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages[0];
						}
					});

			return promise;
		};

		this.unSubscribeUser = function(userId) {
			var promise = $http.post('/user/unSubscribe?userId=' + userId)
					.then(function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages[0];
						} else {
							return false;
						}
					});

			return promise;
		};
		this.getUnreadNotificationsGeneral = function(userId) {
			var promise = $http.get(
					'/getUnreadNotifications?userId=' + userId
							+ '&notificationType=general').then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages;
						}
					});

			return promise;
		};

		this.getUnreadNotificationsConnection = function(userId) {

			var promise = $http.get(
					'/getUnreadNotifications?userId=' + userId
							+ '&notificationType=connection').then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages;
						}
					});
			
			return promise;

		};

		this.getUnreadNotificationsOpinion = function(userId) {
			var promise = $http.get(
					'/getUnreadNotifications?userId=' + userId
							+ '&notificationType=opinion').then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages;
						}
					});

			return promise;
		};
		this.getUnreadNotificationsTask = function(userId) {
			var promise = $http.get(
					'/getUnreadNotifications?userId=' + userId
							+ '&notificationType=task').then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages;
						}
					});

			return promise;
		};
		this.getUserFromSession = function() {
			var promise = $http.get('/user/getUserFromSession').then(
					function(response) {
						console.log(response);
						if (response.data.success == true) {
							return response.data.messages[0];
						} else {
							return false;
						}
					});

			return promise;
		};

	}

	userService.$inject = [ '$http', '$q' ];

	angular.module('governProject.user').service('userService', userService);

})(window.angular);