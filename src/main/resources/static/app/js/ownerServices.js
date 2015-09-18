'use strict';

/* Services */

function ownerService($http, $log) {

    var service = {
        getOwner: getOwner,
        getOwners: getOwners,
        getNewOwner: getNewOwner,
        saveOwner: saveOwner
    };

    return service;

    ////////////

    /**
     * [[Description]]
     * @param   {[[Type]]} ownerId [[Description]]
     * @returns {[[Type]]} [[Description]]
     */
    function getOwner(ownerId) {
        return $http.get('/owner/' + ownerId)
            .success(getOwnerSuccess)
            .error(getOwnerError);

        function getOwnerSuccess(response) {
            return response;
        }

        function getOwnerError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
        }
    }

    function saveOwner(owner) {
        return $http.put('/owner/save', owner)
            .success(saveOwnerSuccess)
            .error(saveOwnerError);

        function saveOwnerSuccess(response) {
            return response;
        }

        function saveOwnerError(error) {
            $log.error('XHR Failed for getAvengers.' + error.exception);
            $log.error('XHR Failed for getAvengers.' + error.message);
            $log.error('XHR Failed for getAvengers.' + error.error);
            return error;
        }
    }

    function getNewOwner() {
        return $http.get('/owner/new')
            .success(getNewOwnerSuccess)
            .error(getNewOwnerError);

        function getNewOwnerSuccess(response) {
            return response;
        }

        function getNewOwnerError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
        }
    }

    function getOwners() {
        return $http.get('/owner/list')
            .success(getOwnersSuccess)
            .error(getOwnersError);

        function getOwnersSuccess(response) {
            return response;
        }

        function getOwnersError(error) {
            $log.error('XHR Failed for getAvengers.' + error.data);
            return error;
        }
    }
}

ownerService.$inject = ['$http', '$log'];

angular.module('petclinicServices')
    .factory('ownerService', ownerService);