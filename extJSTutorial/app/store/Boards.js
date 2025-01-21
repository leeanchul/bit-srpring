// 데이터를 관ㄹ리하는 Store를 정의한 JS
Ext.define('extJSTutorial.store.Boards',{
    extend:'Ext.data.Store',
    alias:'store.boards',
    model:'extJSTutorial.model.Board',

    pageSize:10,

    proxy:{
        type:'ajax',
        url:'http://localhost:8080/api/board/showAll',
        reader:{
            type:'json',
            rootProperty:'list',
            totalProperty:'total'
        },
        extraParams:{
            page:1
        },
        enablePaging:true
    },
    autoLoad:true
})