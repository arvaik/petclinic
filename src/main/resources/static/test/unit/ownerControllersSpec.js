'use strict';

/* jasmine specs for controllers go here */
describe('Petclinic controllers', function () {

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(module('petclinicApp'));

    describe('OwnerListController', function () {
        var $httpBackend, $controller, $log, ctrl;

        beforeEach(inject(function ($injector) {

            $httpBackend = $injector.get('$httpBackend');
            $log = $injector.get('$log');

            $httpBackend.when('GET', '/owner/list').
            respond([{
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                },
                {
                    "id": 2,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                }]);

            $controller = $injector.get('$controller');
            ctrl = $controller('OwnerListController', {});
        }));

        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
            console.log($log.info.logs);
        });

        it('should create "owners" model with 2 owner fetched from xhr', function () {

            expect(ctrl.owners).toEqual([]);
            $httpBackend.flush();

            expect(ctrl.owners).toEqual([{
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                    },
                {
                    "id": 2,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",

                    "telephone": "3639306554"
                    }]);
        });
    });

    describe('OwnerDetailsController', function () {
        var $httpBackend, $controller, $routeParams, $log, ctrl;

        beforeEach(inject(function ($injector) {

            $httpBackend = $injector.get('$httpBackend');
            $log = $injector.get('$log');
            $routeParams = $injector.get('$routeParams');

            $httpBackend.when('GET', '/owner/' [0 - 9]).
            respond({
                "id": 1,
                "firstName": "Károly",
                "lastName": "Árvai",
                "address": "1118, Budapest Csiki-hegyek utca",
                "city": "Budapest",
                "telephone": "3639306554"
            });

            $controller = $injector.get('$controller');
            ctrl = $controller('OwnerDetailsController', {
                $routeParams: {
                    ownerId: 1
                }
            });
        }));

        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
            console.log($log.debug.logs);
        });

        it('should create owner model fetched from xhr', function () {
            expect(ctrl.owner).toEqual({});
            $httpBackend.flush();

            expect(ctrl.owner).toEqual({
                "id": 1,
                "firstName": "Károly",
                "lastName": "Árvai",
                "address": "1118, Budapest Csiki-hegyek utca",
                "city": "Budapest",
                "telephone": "3639306554"
            });
        });
    });

    describe('OwnerController', function () {
        var $httpBackend, $controller, $routeParams, $log, ctrl;

        beforeEach(inject(function ($injector) {

            $httpBackend = $injector.get('$httpBackend');
            $log = $injector.get('$log');
            $routeParams = $injector.get('$routeParams');

            $httpBackend.when('GET', '/owner/' [0 - 9]).
            respond({
                "id": 1,
                "firstName": "Károly",
                "lastName": "Árvai",
                "address": "1118, Budapest Csiki-hegyek utca",
                "city": "Budapest",
                "telephone": "3639306554"
            });

            $controller = $injector.get('$controller');
        }));

        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
            console.log($log.debug.logs);
            console.log($log.info.logs);
        });

        describe('OwnerController getOwner', function () {

            beforeEach(function () {

                $httpBackend.when('GET', '/owner/' [0 - 9]).respond({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });

                ctrl = $controller('OwnerController', {
                    $routeParams: {
                        ownerId: 1
                    }
                });
            });


            it('should create owner model fetched from xhr', function () {
                expect(ctrl.owner).toEqual({});
                $httpBackend.flush();

                expect(ctrl.owner).toEqual({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });
            });
        });

        describe('OwnerController getNewOwner', function () {

            beforeEach(function () {

                ctrl = $controller('OwnerController', {
                    $routeParams: {}
                });
            });


            it('should create owner model fetched from xhr', function () {
                expect(ctrl.owner).toEqual({
                    "firstName": "",
                    "lastName": "",
                    "address": "",
                    "city": "",
                    "telephone": "",
                    "new": true
                });
            });
        });

        describe('OwnerController saveOwner', function () {

            beforeEach(function () {

                $httpBackend.when('GET', '/owner/' [0 - 9]).respond({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });

                $httpBackend.when('PUT', '/owner/save').respond({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });

                ctrl = $controller('OwnerController', {
                    $routeParams: {
                        ownerId: 1
                    },
                    isValid: true
                });
            });


            it('should create owner model fetched from xhr', function () {
                expect(ctrl.owner).toEqual({});
                $httpBackend.flush();

                expect(ctrl.owner).toEqual({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });
            });

            it('should create owner model fetched from xhr', function () {
                expect(ctrl.owner).toEqual({});

                //$log.debug('valid: ' + isValid);

                //make the call.
                ctrl.submitOwnerForm(true);

                $httpBackend.flush();

                expect(ctrl.owner).toEqual({
                    "id": 1,
                    "firstName": "Károly",
                    "lastName": "Árvai",
                    "address": "1118, Budapest Csiki-hegyek utca",
                    "city": "Budapest",
                    "telephone": "3639306554"
                });
            });
        });
    });
});