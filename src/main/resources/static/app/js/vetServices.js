'use strict';

/* Services */

function veterinarianService($http, $log) {

    var service = {
        getVet: getVet,
        getVets: getVets
    };

    return service;

    ////////////

    function getVet(vetId) {
        return $http.get('/vets/' + vetId)
            .success(getVetSuccess)
            .error(getVetError);

        function getVetSuccess(response) {
            return response;
        }

        function getVetError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
        }
    }

    function getVets() {
        return $http.get('/vet/list')
            .success(getVetsSuccess)
            .error(getVetsError);

        function getVetsSuccess(response) {
            return response;
        }

        function getVetsError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
        }
    }
}

veterinarianService.$inject = ['$http', '$log'];

angular.module('petclinicServices')
    .factory('veterinarianService', veterinarianService);