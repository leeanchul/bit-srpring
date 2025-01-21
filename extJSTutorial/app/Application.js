/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('extJSTutorial.Application', {
    extend: 'Ext.app.Application',

    name: 'extJSTutorial',

    quickTips: false,
    platformConfig: {
        desktop: {
            quickTips: true
        }
    },

    init:()=>{
        let login=sessionStorage.getItem('login')
        console.log('login: '+login)

        if(login){
            // 로그인이 되어있는 상태면
            // mainview를 실행시킨다.
            Ext.Viewport.add({
                xtype:'mainview'
            })
        }else {
            Ext.Viewport.add({
                xtype:'loginview'
            })
        }
    }
});
