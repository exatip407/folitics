var registration = angular.module("userRegistration", []);

registration.directive('fileModel', [ '$parse', function($parse) {

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
} ]);

registration.service("UserService", [
		"$http",
		function($http) {

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
				marritalStatus : "",
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
					url : '/user/add',
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
		} ]);

registration.controller("registrationController", [ '$scope', '$http', 'UserService',

function($scope, $http, UserService) {

	$scope.add = function() {

		var newAssociation = {
			associationName : ""
		};
		$scope.registration.userAssociation.push(newAssociation);
	};

	$scope.education = function() {

		var newEducation = {
			instituteName : "",
			startDate : "",
			endDate : "",
			courseName : ""
		};

		$scope.registration.userEducation.push(newEducation);
	};

	$scope.registration = UserService._registration;
	$scope.submit = function(isValid) {

		if (isValid) {
			if (typeof ($scope.file) != "undefined") {
				if ($scope.file.size < 1000000) {
					UserService.post($scope.registration, $scope.file);
				} else {
					alert('Image should be less than 1 MB');
				}
			} else {
				alert('Image is not selected');
			}

		} else {
			alert('Validation fail');
		}
	};
} ]);