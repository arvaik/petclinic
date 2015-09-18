function config($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/welcome.html'
    }).when('/owners', {
        templateUrl: 'partials/owners/ownersList.html',
        controller: 'OwnerListController',
        controllerAs: 'vm'
    }).when('/owners/new', {
        templateUrl: 'partials/owners/createOrUpdateOwnerForm.html',
        controller: 'OwnerController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId', {
        templateUrl: 'partials/owners/ownerDetails.html',
        controller: 'OwnerDetailsController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId/edit', {
        templateUrl: 'partials/owners/createOrUpdateOwnerForm.html',
        controller: 'OwnerController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId/pets/new', {
        templateUrl: 'partials/pets/createOrUpdatePetForm.html',
        controller: 'PetController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId/pets/:petId/edit', {
        templateUrl: 'partials/pets/createOrUpdatePetForm.html',
        controller: 'PetController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId/pets/:petId/visits/new', {
        templateUrl: 'partials/pets/createOrUpdateVisitForm.html',
        controller: 'VisitController',
        controllerAs: 'vm'
    }).when('/owners/:ownerId/pets/:petId/visits/:visitId/edit', {
        templateUrl: 'partials/pets/createOrUpdateVisitForm.html',
        controller: 'VisitController',
        controllerAs: 'vm'
    }).when('/vets', {
        templateUrl: 'partials/vets/vetList.html',
        controller: 'VeterinarianListController',
        controllerAs: 'vm'
    }).otherwise({
        redirectTo: '/'
    });
}

angular.module('petclinicControllers', []);

angular.module('petclinicServices', []);

angular.module('petclinicApp', ['ngRoute', 'ui.date', 'petclinicControllers', 'petclinicServices']).config(config);