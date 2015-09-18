'use strict';

/* Controllers */

function VisitController($routeParams, $log, $location, visitService, petService) {

    var vm = this;
    var visitId = $routeParams.visitId;
    var petId = $routeParams.petId;
    var ownerId = $routeParams.ownerId;
    var actPet = {
        "id": petId,
        "new": false
    };
    vm.visit = {};
    vm.pet = {};
    vm.visitFormError = {};
    vm.submitVisitForm = submitVisitForm;
    vm.dateOptions = {
        changeYear: true,
        changeMonth: true,
        yearRange: '1900:-0'
    };

    //varvm.getVisit = getVisit;
    $log.debug('Visitcontoller: ' + visitId);
    $log.debug('Visitcontoller: ' + petId);
    $log.debug('Visitcontoller: ' + ownerId);

    activate();

    function activate() {
        if (visitId) {
            getVisit()
            $log.debug('Activated VisitEditForm View');
        } else {
            getNewVisit();
            $log.debug('Activated VisitNewForm View');
        }
        getPet();
    }

    function getVisit() {
        return visitService.getVisit(visitId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.visit = data;
                vm.visit.pet = actPet;
                return vm.visit;
            });
    }

    function getVisitTypes() {
        return visitService.getVisitTypes()
            .success(function (data) {
                vm.visitTypes = data;
                return vm.visitTypes;
            });
    }

    function getNewVisit() {
        vm.visit = {
            "description": "",
            "date": "",
            "new": true
        };
        vm.visit.pet = actPet;
        return vm.visit;
    }

    function saveVisit() {
        return visitService.saveVisit(vm.visit)
            .success(saveVisitSuccess)
            .error(saveVisitError);

        function saveVisitSuccess(data) {
            $log.debug('contoller id: ' + data.id);
            vm.visit = data;
            return vm.visit;
        }

        function saveVisitError(error) {
            $log.error('exception.' + error.exception);
            $log.error('message.' + error.message);
            $log.error('error.' + error.error);
            vm.visitFormError = error;
            return vm.visitFormError;
        }
    }

    function getPet() {
        return petService.getPet(petId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.pet = data;
                return vm.pet;
            });
    }

    // function to submit the form after all validation has occurred            
    function submitVisitForm(isValid) {

        // check to make sure the form is completely valid
        if (isValid) {
            //alert('our form is amazing');
            $log.info('call saveVisit');
            return saveVisit(vm.visit).then(function () {
                $log.info('saveVisit: ');
                $location.path("/owners/" + ownerId);
            });
        }
    }
}

VisitController.$inject = ['$routeParams', '$log', '$location', 'visitService', 'petService'];

angular.module('petclinicControllers')
    .controller('VisitController', VisitController);