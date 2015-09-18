module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            'app/bower_components/jquery/dist/jquery.js',
            'app/bower_components/jquery-ui/jquery-ui.min.js',
            'app/bower_components/angular/angular.js',
            'app/bower_components/angular-route/angular-route.js',
            'app/bower_components/angular-resource/angular-resource.js',
            'app/bower_components/angular-animate/angular-animate.js',
            'app/bower_components/angular-mocks/angular-mocks.js',
            'app/bower_components/bootstrap/dist/js/bootstrap.js',
            'app/bower_components/angular-ui-date/src/date.js',
            'app/js/**/*.js',
            'test/unit/**/*.js'
            ],

        autoWatch: true,

        frameworks: ['jasmine'],

        //preprocessors: {
        //    'app/js/**/*.js': 'coverage'
        //},

        browsers: ['Chrome'],

        //coverageReporter: {
        //    type: 'text-summary',
        //    dir: 'coverage/'
        //},

        // test results reporter to use
        reporters: ['progress', 'coverage'],
        //reporters: ['progress', 'brackets'],

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        plugins: [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
            ],

        junitReporter: {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }

    });
};