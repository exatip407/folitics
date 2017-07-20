(function(angular) {

 'use strict';

 function userRegistrationController($http, $scope, userService) {

  var marital = [];
  var religion1 = [];
  var state1 = [];
  
  
  
  
  $scope.addEducation = function() {
   $scope.newEducation = {
     instituteName : "",
     startDate : "",
     endDate : "",
     courseName : ""
    };
   
   var item =$scope.newEducation; 
   $scope.registration.userEducation.push(item);
  };

  $scope.removeChoice = function() { 
   $scope.newEducation = {
     instituteName : "",
     startDate : "",
     endDate : "",
     courseName : ""
    };
   
   var item1 = $scope.newEducation;
   $scope.registration.userEducation.splice(item1);
  };

 /* var date1 = new Date();

  $scope.date = date1.getFullYear() + '-' + date1.getDate() + '-'
    + ('0' + (date1.getMonth() + 1)).slice(-2);*/
  
  
  $scope.getCity=function(){
   /*alert("function call");
   alert($scope.state1);*/
   //console.log($scope.state1);
   console.log("state select"  +$scope.state1.id.state);
  $http.get('statelookup/search?state='+$scope.state1.id.state).then(function(response) {
   if (response.data.messages != null) {
    //alert(response.data.messages);
    $scope.statlookup = response.data.messages;
    //alert("check" + $scope.statlookup);
    //console.log($scope.statlookup );
   }
  });
  }
  
  $scope.compare=function(){
 		$http.get('/user/verifyIfUsernameExist?username='+$scope.registration.username).then(function(response){

    		   $scope.result =response.data.success;
    		   return result;
 		});
 		
	};   
  $scope.checkErr = function(dob) {

   var date1 = new Date();

   if (date1 < dob) {

    $scope.errMessage = 'Are you mad???';
    return false;
   } else {
    $scope.errMessage = '';
    return true;
   }

  }

  $scope.checkdate = function(start, end) {

   if (start > end) {

    $scope.ErrMessage = 'Course end date must be greater than end date';
    $scope.error1 = true;
    return false;
   } else {
    $scope.ErrMessage = '';
    return true;
   }

  }

  $scope.add = function() {

   var newAssociation = {
    associationName : ""
   };
   $scope.registration.userAssociation.push(newAssociation);
  };

  $scope.registration = userService._registration;
  $scope.submit = function(isValid) {
   $scope.registration.maritalStatus = $scope.marital.maritalStatus;
   $scope.registration.religion = $scope.religion1.religion;
   $scope.registration.state = $scope.state1.id.state;

   if (isValid) {
    if (typeof ($scope.file) != "undefined") {
     if ($scope.file.size < 1000000) {
      userService.post($scope.registration, $scope.file);
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

  $http.get('/user/getAllReligion').then(function(response) {

   if (response.data.messages != null) {
    // alert(response.data.messages );
    $scope.religions = response.data.messages[0];
   }
  })
  $http.get('/user/getAllState').then(function(response) {

   if (response.data.messages != null) {
    // alert(response.data.messages );
    $scope.states = response.data.messages[0];
   }
  })
  $http.get('/user/getAllMaritalStatus').then(function(response) {

   if (response.data.messages != null) {
    // alert(response.data.messages );
    $scope.status = response.data.messages[0];
   }
  })
  $http.get('/user/getAllQualification').then(function(response) {

   if (response.data.messages != null) {
    // alert(response.data.messages );
    $scope.qualifications = response.data.messages[0];
    // console.log($scope.qualifications);
   }
  });
  
 }

 userRegistrationController.$inject = [ '$http', '$scope', 'userService' ];

 angular.module('governProject.user').controller(
   'userRegistrationController', userRegistrationController);

})(window.angular);