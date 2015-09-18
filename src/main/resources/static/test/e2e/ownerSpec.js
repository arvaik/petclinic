describe("Integration/E2E Testing", function () {

    // start at root before every test is run
    //    beforeEach(function () {
    //        browser.get('http://localhost:8080/app/index.html');
    //    });

    // test default route
    //    it('should jump to the / path when / is accessed', function () {
    //        browser.get('http://localhost:8080/app/index.html#/');
    //        expect(browser.location.path).toBe("/");
    //    });

    it('ensures owners can get list', function () {
        browser.get('http://localhost:8080/app/index.html#/owners');
        //expect(browser.location().path()).toBe("/owners");

        var todoList = element.all(by.repeater('owner in vm.owners'));
        expect(todoList.count()).toEqual(10);
        //expect(todoList.get(2).getNameText()).toEqual('write first protractor test');

        //expect(element(by.id('owners_table')).html().toLowerCase()).toContain('failed');
        // my dashboard page has a label for the email address of the logged in user
    });

    //    it('ensures owners can get list', function () {
    //        browser().navigateTo('#/owners');
    //        expect(browser().location().path()).toBe("/owners");
    //
    //        element('submit').click();
    //
    //        expect(element('#message').html().toLowerCase()).toContain('failed');
    //
    //        // logged out route
    //        expect(browser().location().path()).toBe("/owners/new");
    //
    //    });
});