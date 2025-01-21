/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'extJSTutorial.Application',

    name: 'extJSTutorial',

    requires: [
        // This will automatically load all classes in the extJSTutorial namespace
        // so that application classes do not need to require each other.
        'extJSTutorial.*'
    ],

    // The name of the initial view to create.
    mainView: 'extJSTutorial.view.main.Main'
});
