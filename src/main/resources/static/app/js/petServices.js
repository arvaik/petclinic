'use strict';

/* Services */

function petService($http, $log) {

    var service = {
        getPet: getPet,
        getPetTypes: getPetTypes,
        savePet: savePet
    };

    return service;

    ////////////

    function getPet(petId) {
        return $http.get('/pet/' + petId)
            .success(getPetSuccess)
            .error(getPetError);

        function getPetSuccess(response) {
            return response;
        }

        function getPetError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
            return error;
        }
    }

    function savePet(pet) {
        return $http.put('/pet/save', pet)
            .success(savePetSuccess)
            .error(savePetError);

        function savePetSuccess(response) {
            return response;
        }

        function savePetError(error) {
            $log.error('XHR Failed for getAvengers.' + error.exception);
            $log.error('XHR Failed for getAvengers.' + error.message);
            $log.error('XHR Failed for getAvengers.' + error.error);
            return error;
        }
    }

    function getPetTypes() {
        return $http.get('/pet/types')
            .success(getPetTypesSuccess)
            .error(getPetTypesError);

        function getPetTypesSuccess(response) {
            return response;
        }

        function getPetTypesError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
            return error;
        }
    }
}

petService.$inject = ['$http', '$log'];

angular.module('petclinicServices')
    .factory('petService', petService);