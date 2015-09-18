'use strict';

/* Services */

function visitService($http, $log) {

    var service = {
        getVisit: getVisit,
        saveVisit: saveVisit
    };

    return service;

    ////////////

    function getVisit(visitId) {
        return $http.get('/visit/' + visitId)
            .success(getVisitSuccess)
            .error(getVisitError);

        function getVisitSuccess(response) {
            return response;
        }

        function getVisitError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
            return error;
        }
    }

    function saveVisit(visit) {
        return $http.put('/visit/save', visit)
            .success(saveVisitSuccess)
            .error(saveVisitError);

        function saveVisitSuccess(response) {
            return response;
        }

        function saveVisitError(error) {
            $log.error('XHR Failed for getAvengers.' + error.exception);
            $log.error('XHR Failed for getAvengers.' + error.message);
            $log.error('XHR Failed for getAvengers.' + error.error);
            return error;
        }
    }
}

visitService.$inject = ['$http', '$log'];

angular.module('petclinicServices')
    .factory('visitService', visitService);