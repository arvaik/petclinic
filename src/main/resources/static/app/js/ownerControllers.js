'use strict';

/* Controllers */

function OwnerController($routeParams, $log, $location, ownerService) {

    var vm = this;
    var ownerId = $routeParams.ownerId;
    vm.owner = {};
    vm.ownerFormError = {};
    vm.submitOwnerForm = submitOwnerForm;

    //varvm.getOwner = getOwner;
    $log.debug('contoller: ' + ownerId);

    activate();

    function activate() {
        if (ownerId) {
            return getOwner().then(function () {
                $log.info('Activated OwnerEditForm View');
            });
        } else {
            $log.info('Activated OwnerNewForm View');
            getNewOwner();
        }
    }

    function getOwner() {
        return ownerService.getOwner(ownerId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.owner = data;
                return vm.owner;
            });
    }

    function getNewOwner() {
        vm.owner = {
            "firstName": "",
            "lastName": "",
            "address": "",
            "city": "",
            "telephone": "",
            "new": true
        };
        return vm.owner;
    }

    function saveOwner() {
        return ownerService.saveOwner(vm.owner)
            .success(saveOwnerSuccess)
            .error(saveOwnerError);

        function saveOwnerSuccess(data) {
            $log.debug('contoller id: ' + data.id);
            vm.owner = data;
            return vm.owner;
        }

        function saveOwnerError(error) {
            $log.error('exception.' + error.exception);
            $log.error('message.' + error.message);
            $log.error('error.' + error.error);
            vm.ownerFormError = error;
            return vm.ownerFormError;
        }
    }

    // function to submit the form after all validation has occurred            
    function submitOwnerForm(isValid) {

        // check to make sure the form is completely valid
        if (isValid) {
            //alert('our form is amazing');
            $log.info('call saveOwner');
            return saveOwner(vm.owner).then(function () {
                $log.info('saveOwner');
                $location.path("/owners/" + vm.owner.id);
            });
        }
    }
}

function OwnerDetailsController($routeParams, $log, ownerService) {

    var vm = this;
    var ownerId = $routeParams.ownerId;
    vm.owner = {};

    //varvm.getOwner = getOwner;
    $log.debug('contoller: ' + ownerId);

    activate();

    function activate() {
        return getOwner().then(function () {
            $log.debug('Activated OwnerDetails View');
        });
    }

    function getOwner() {
        return ownerService.getOwner(ownerId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.owner = data;
                return vm.owner;
            });
    }
}

function OwnerListController($routeParams, $log, ownerService) {

    var vm = this;
    vm.owners = [];

    activate();

    function activate() {
        return getOwners().then(function () {
            $log.info('Activated OwnerList View');
        });
    }

    function getOwners() {
        return ownerService.getOwners()
            .success(function (data) {
                vm.owners = data;
                return vm.owners;
            });
    }
}

OwnerDetailsController.$inject = ['$routeParams', '$log', 'ownerService'];
OwnerListController.$inject = ['$routeParams', '$log', 'ownerService'];
OwnerController.$inject = ['$routeParams', '$log', '$location', 'ownerService'];

angular.module('petclinicControllers')
    .controller('OwnerDetailsController', OwnerDetailsController)
    .controller('OwnerController', OwnerController)
    .controller('OwnerListController', OwnerListController);