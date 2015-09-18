'use strict';

/* Controllers */

function PetController($routeParams, $log, $location, petService) {

    var vm = this;
    var petId = $routeParams.petId;
    var ownerId = $routeParams.ownerId;
    var owner = {
        "id": ownerId,
        "new": false
    };
    vm.pet = {};
    vm.birthDate = {};
    vm.petTypes = [];
    vm.selectedType = {};
    vm.petFormError = {};
    vm.submitPetForm = submitPetForm;
    vm.dateOptions = {
        changeYear: true,
        changeMonth: true,
        yearRange: '1900:-0'
    };

    //varvm.getPet = getPet;
    $log.debug('Petcontoller: ' + petId);
    $log.debug('Petcontoller: ' + ownerId);

    activate();

    function activate() {
        if (petId) {
            getPet()
            $log.debug('Activated PetEditForm View');
        } else {
            getNewPet();
            $log.debug('Activated PetNewForm View');
        }
        $log.info('getPetTypes');
        getPetTypes();
    }

    function getPet() {
        return petService.getPet(petId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.pet = data;
                vm.pet.owner = owner;
                return vm.pet;
            });
    }

    function getPetTypes() {
        return petService.getPetTypes()
            .success(function (data) {
                vm.petTypes = data;
                return vm.petTypes;
            });
    }

    function getNewPet() {
        vm.pet = {
            "name": "",
            "birthDate": "",
            "type": {},
            "new": true
        };
        vm.pet.owner = owner;
        return vm.pet;
    }

    function savePet() {
        return petService.savePet(vm.pet)
            .success(savePetSuccess)
            .error(savePetError);

        function savePetSuccess(data) {
            $log.debug('contoller id: ' + data.id);
            vm.pet = data;
            return vm.pet;
        }

        function savePetError(error) {
            $log.error('exception.' + error.exception);
            $log.error('message.' + error.message);
            $log.error('error.' + error.error);
            vm.petFormError = error;
            return vm.petFormError;
        }
    }

    // function to submit the form after all validation has occurred            
    function submitPetForm(isValid) {

        // check to make sure the form is completely valid
        if (isValid) {
            //alert('our form is amazing');
            $log.info('call savePet');
            return savePet(vm.pet).then(function () {
                $log.info('savePet: ');
                $location.path("/owners/" + ownerId);
            });
        }
    }
}

function PetDetailsController($routeParams, $log, petService) {

    var vm = this;
    var petId = $routeParams.petId;
    vm.pet = {};

    //varvm.getPet = getPet;
    $log.debug('contoller: ' + petId);

    activate();

    function activate() {
        return getPet().then(function () {
            $log.info('Activated PetDetails View');
        });
    }

    function getPet() {
        return petService.getPet(petId)
            .success(function (data) {
                $log.debug('contoller id: ' + data.id);
                vm.pet = data;
                return vm.pet;
            });
    }
}

function PetListController($routeParams, $log, petService) {

    var vm = this;
    vm.pets = [];

    activate();

    function activate() {
        return getPets().then(function () {
            $log.info('Activated PetList View');
        });
    }

    function getPets() {
        return petService.getPets()
            .success(function (data) {
                vm.pets = data;
                return vm.pets;
            });
    }
}

PetDetailsController.$inject = ['$routeParams', '$log', 'petService'];
PetListController.$inject = ['$routeParams', '$log', 'petService'];
PetController.$inject = ['$routeParams', '$log', '$location', 'petService'];

angular.module('petclinicControllers')
    .controller('PetDetailsController', PetDetailsController)
    .controller('PetController', PetController)
    .controller('PetListController', PetListController);