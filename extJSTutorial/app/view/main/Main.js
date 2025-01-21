/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 */
Ext.define('extJSTutorial.view.main.Main', {
    extend: 'Ext.Container',
    xtype: 'mainview',

    layout: 'fit',

    items: [
        // docked는 툴바가 어디에 고정될지 지정한다.
        // top,booton,right, left 중 선택 가능핟.
        // 아무것도 적어주지 않으면 위의 아이템 다음에 곧장 나온다.
        {
            xtype: 'toolbar',
            docked: 'top',
            layout: {
                // hbox 는 horizontal box 여러개의 아이템을 좌,우로 쌓을때 사용이왼다.
                type: 'hbox',
                // pack 은 아이템들의 배치가 어떻게 될지를 지정한다.
                pack: 'end',
            },
            items: [
                {
                    xtype: 'button', text: '로그아웃', handler: (button) => {
                        sessionStorage.removeItem('login')
                        Ext.Viewport.removeAll()
                        Ext.Viewport.add({
                            xtype:'loginview'
                        })
                    }
                }
            ]
        },
        {
            xtype: 'boardlistview'
        }
    ]

});
