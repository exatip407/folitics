
(function (angular) {
    'use strict';

    angular.module('governProject.home', []).config(Configure);

    Configure.$inject = ['$routeProvider'];

    function Configure($locationProvider, $routeProvider) {
      
        //$routeProvider
        //    .when('/busticket', {
        //        templateUrl: "Client/bus/bus.html",
        //        controller: 'BusController'
        //    });
        //$routeProvider.when('/busticket/busroutes', {
        //    templateUrl: 'Client/bus/bus-routes.html',
        //    controller: 'BusRoutesController'

        //});
        
    }

})(window.angular);