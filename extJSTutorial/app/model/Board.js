// Board 객체를 정의하는 JS
Ext.define('extJSTutorial.model.Board',{
    extend:'Ext.data.Model',
    fields:[
        {name:'id',type:'int'},
        {name:'title',type:'string'},
        {name:'content',type:'string'},
        {name:'writerId',type:'int'},
        {name:'nickname',type:'string'},
        {name:'entryDate',type:'string'},
        {name:'modifyDate',type:'string'} 
    ]
})