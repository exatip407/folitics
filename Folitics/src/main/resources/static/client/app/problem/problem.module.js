
(function (angular) {
    'use strict';

    angular.module('governProject.problem', []).config(Configure);

    Configure.$inject = ['$routeProvider'];

    function Configure($locationProvider, $routeProvider) {
      
//        $routeProvider
//            .when('/landing', {
//                templateUrl: "landingProblem.html",
//                controller: 'ProblemLandingController'
//            });
//        $routeProvider.when('/busticket/busroutes', {
//            templateUrl: 'Client/bus/bus-routes.html',
//            controller: 'BusRoutesController'
//
//        });
        
    }

})(window.angular);