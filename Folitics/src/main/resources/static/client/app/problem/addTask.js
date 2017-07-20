var addTask = angular.module("taskApp", []);

addTask.directive('fileModel', [ '$parse', function($parse) {

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

addTask.service("taskService", [ "$http", function($http) {

	var task = {};
	var peopleMet = {};
	this.peopleMet = {
		name : "",
		departmentName : "",
		location : "",
		actionTaken : ""

	};

	this.task = {
		subject : "",
		description : "",
		createdBy : "",
		participants : "",
		departments : [],
		peopleMet : [],
		city : "",
		address : "",
		email : "",
		phone : "",
		category : [],
		isDept : "",
		isNGO : "",
		isLeader : ""
	};

	console.log(this.task.peopleMet)
	this.post = function(task, file) {

		task.createdBy = {
			"id" : 2
		};

		task.participants = [ {
			"id" : 2
		} ];

		console.log(task);
		// task.city = "INDORE", task.address = "23 A Vaishali Nagar",
		// task.email = "kshitij.mandloi@gmail.com",
		task.phone = "7748065039", $http({
			method : 'POST',
			url : 'http://localhost:8080/task/addTask',
			headers : {
				'Content-Type' : undefined
			},
			transformRequest : function() {

				var formData = new FormData();
				formData.append('task', new Blob([ JSOG.stringify(task) ], {
					type : "application/json"
				}));
				formData.append("file", file);
				return formData;
			},
			data : {
				task : task,
				file : file
			}

		}).success(function(data, status, headers, config) {

		});
	};
} ]);

addTask.controller("taskController", function($scope, $http, taskService) {

	$scope.task = taskService.task;

	$scope.submit = function() {

		$scope.task.peopleMet.push($scope.peopleMet)
		console.log($scope.task)

		taskService.task.category.push($scope.selectedOption)
		taskService.task.departments.push($scope.selectedDepartmentOption)

		taskService.post($scope.task, $scope.file);
	};

	$http.get('http://localhost:8080/task/getTaskCategories').then(function(response) {

		if (response.data.messages != null) {
			$scope.categories = response.data.messages[0];
		}
	})

	$http.get('http://localhost:8080/task/getAllDepartments').then(function(response) {

		if (response.data.messages != null) {
			$scope.departments = response.data.messages[0];
		}
	})
});
