(function (angular) {
    'use strict';
    
    function UserController($scope, $http,$rootScope,$routeParams, userService) {
    	
    	var request = "";
    	
      
   // $scope.user = userService.user;

    	 /*$http.get('/user/getAll').then(function(response){
    		// alert(response.data.messages);
    		   $scope.users =response.data.messages;
    		   console.log($scope.users); 
		});*/
    
       $http.get('/user/getAllRemainingUsers?id='+$rootScope.sessionUser.id).then(function(response){
		// alert(response.data.messages);
		   $scope.users =response.data.messages;
		   console.log($scope.users);
	});
       
       
       $scope.deleteConnection = function(userId){
       $http.get('/user/deleteConnection?userId='+userId+'&connectionId='+$rootScope.sessionUser.id).then(function(response){
    	   alert("deleted");
    	   connection4.style.display = "none";
  	});
       } 
       
       $http.get('/user/getRequestConnection?connectionId='+$rootScope.sessionUser.id+'&status=Pending').then(
					function(response) {

						if (response.data.messages != "") {
							
							$scope.requestedUsers = response.data.messages;
						    console.log($scope.requestedUsers); 
						} 
						else{
							$scope.request = "You don't have any request";
							
							}
						
					});
    	 $scope.getRequestConnection = function() {
  			
  		}
    		
 		$scope.getUserConnection = function() {
 			$http.get('/user/getUserConnection?userId='+$rootScope.sessionUser.id).then(
 					function(response) {

 						if (response.data.messages != null) {
 							
 							$scope.friends = response.data.messages;
 						    console.log($scope.friends); 
 						    $scope.show = true;
 						} 
 						else
 							{
 							alert('u have no frd');
 							
 							}
 						
 					});
 		}
 		
		$scope.updateConnectionStatus = function(userId) {
			$http.get('/user/updateConnectionStatus?userId='+userId+'&connectionId='+$rootScope.sessionUser.id+'&connectionStatus=Accepted').then(
					function(response) {
					
						//alert(response.data.messages);
						$scope.msg = "You are now friend with " +$rootScope.sessionUser.name ;
						$scope.id=userId;
						if (response.data.messages != null) {
							
							$scope.friend = response.data.messages;
						    console.log($scope.friend); 
						} 
						
						
					});	
		}  	
    }
    
    UserController.$inject = ['$scope','$http','$rootScope','$routeParams','userService'];

    angular.module('governProject.user').controller('UserController', UserController);

})(window.angular);