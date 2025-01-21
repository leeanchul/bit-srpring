Ext.define('extJSTutorial.view.board.BoardForm',{
    extend:'Ext.form.Panel',
    xtype:'boardformview',
    
    title:'글 작성',


    items:[
        {
            xtype:'textfield',name:'title',label:'제목'
        },
        {
            xtype:'textareafield', name:'content', label:'내용'
        },
        {
            xtype:'button',text:'작성하기', handler:(button)=>{
                let login=JSON.parse(sessionStorage.getItem("login"))

                if(!login){
                    Ext.Viewport.removeAll()
                    Ext.Viewport.add({
                        xtype:'loginview'
                    })
                }else{
                    let form=button.up('formpanel')
                    let values=form.getValues()
                    values['writerId']=login.id

                    Ext.Ajax.request({
                        url:'http://localhost:8080/api/board/write',
                        method:'POST',
                        jsonData:values,
                        success:(resp)=>{
                            console.log(resp)
                            Ext.Viewport.removeAll()
                            Ext.Viewport.add({
                                xtype:'mainview'
                            })
                        }
                    })
                }



            }
        }
    ]
})