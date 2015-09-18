'use strict';

describe('Petclinic services', function () {

    // load modules
    beforeEach(module('petclinicApp'));

    // Test service availability
    it('check the existence of Owner factory', inject(function (ownerService) {
        expect(ownerService).toBeDefined();
    }));

    describe('OwnerService', function () {
        var $httpBackend, service;

        beforeEach(inject(function ($injector) {
            service = $injector.get('ownerService');
            $httpBackend = $injector.get('$httpBackend');
        }));

        // check to see if it has the expected functions
        it('should have an getOwner function', function () {
            expect(angular.isFunction(service.getOwner)).toBe(true);
        });

        it('should have an getOwners function', function () {
            expect(angular.isFunction(service.getOwners)).toBe(true);
        });

        it('should have an getNewOwner function', function () {
            expect(angular.isFunction(service.getNewOwner)).toBe(true);
        });

        it('should have an saveOwner function', function () {
            expect(angular.isFunction(service.saveOwner)).toBe(true);
        });

        // make sure no expectations were missed in your tests.
        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('should call the getOwners and return the response.', function () {

            // set up some data for the http call to return and test later.
            var returnData = [{
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
                    }];

            $httpBackend.when('GET', '/owner/list').respond(returnData);

            // make the call.
            var returnedOwners = service.getOwners();

            // set up a handler for the response, that will put the result
            // into a variable in this scope for you to test.
            var result;
            returnedOwners.then(function (response) {
                result = response.data;
            });

            // flush the backend to "execute" the request to do the expectedGET assertion.
            $httpBackend.flush();

            // check the result. 
            // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
            // as the object will be a copy and not the same instance.)
            expect(result).toEqual(returnData);
        });

        it('should call the getOwner and return the response.', function () {

            // set up some data for the http call to return and test later.
            var returnData = {
                "id": 4,
                "firstName": "Harold",
                "lastName": "Davis",
                "address": "563 Friendly St.",
                "city": "Windsor",
                "telephone": "6085553198",
                "pets": [
                    {
                        "id": 5,
                        "name": "Iggy",
                        "birthDate": "2010-11-30T00:00:00.000Z",
                        "type": {
                            "id": 3,
                            "name": "lizard",
                            "new": false
                        },
                        "visits": [],
                        "new": false
                    }],
                "new": false
            };

            $httpBackend.when('GET', '/owner/4').respond(returnData);

            // make the call.
            var returnedOwner = service.getOwner(4);

            // set up a handler for the response, that will put the result
            // into a variable in this scope for you to test.
            var result;
            returnedOwner.then(function (response) {
                result = response.data;
            });

            // flush the backend to "execute" the request to do the expectedGET assertion.
            $httpBackend.flush();

            // check the result. 
            // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
            // as the object will be a copy and not the same instance.)
            expect(result).toEqual(returnData);
        });

        it('should call the saveOwner and return the response.', function () {
            var actOwner = {
                "firstName": "Károly",
                "lastName": "Árvai",
                "address": "1118, Budapest Csiki-hegyek utca",
                "city": "Budapest",
                "telephone": "3619306554"
            };

            // set up some data for the http call to return and test later.
            var returnData = {
                "id": 11,
                "firstName": "Károly",
                "lastName": "Árvai",
                "address": "1118, Budapest Csiki-hegyek utca",
                "city": "Budapest",
                "telephone": "3619306554",
                "pets": null,
                "new": false
            };

            $httpBackend.when('PUT', '/owner/save', actOwner).respond(200, returnData);

            // make the call.
            var returnedOwner = service.saveOwner(actOwner);

            // set up a handler for the response, that will put the result
            // into a variable in this scope for you to test.
            var result;
            returnedOwner.then(function (response) {
                result = response.data;
            });

            // flush the backend to "execute" the request to do the expectedGET assertion.
            $httpBackend.flush();

            // check the result. 
            // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
            // as the object will be a copy and not the same instance.)
            expect(result).toEqual(returnData);
        });
    });
});