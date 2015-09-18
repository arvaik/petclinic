'use strict';

/* Controllers */

function VeterinarianListController($routeParams, $log, veterinarianService) {

    var vm = this;
    vm.vets = [];

    activate();

    function activate() {
        return getVets().then(function () {
            $log.info('Activated VeterinarianList View');
        });
    }

    function getVets() {
        return veterinarianService.getVets()
            .success(function (data) {
                vm.vets = data;
                return vm.vets;
            });
    }
}

VeterinarianListController.$inject = ['$routeParams', '$log', 'veterinarianService'];

angular.module('petclinicControllers')
    .controller('VeterinarianListController', VeterinarianListController);