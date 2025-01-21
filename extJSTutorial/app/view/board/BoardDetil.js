Ext.define('extJSTutorial.view.board.BoardDetail',{
    extend:'Ext.Container',
    xtype:'boarddetailview',

    // 다른 view 값을 받아올때 config
    config:{
        record:null
    },


    // 이 view 가 처음 만들어질 때 실행시킬 코드가 있다면
    // initialize
    initialize:function(){
        let data = this.config.record[0].data
        this.setItems([
            {
                xtype:'panel',
                html:`
                <h1>제목: ${data.title}</h1>
                <h3>작성자: ${data.nickname}</h3>
                <h3>작성일:${data.entryDate}</h3>
                <h3>수정일:${data.modifyDate}</h3>
                <hr>
                <h3>내용</h3>
                <h1>${data.content}</h1>
                `
            },
            {
                xtype:'toolbar',
                docked:'top',
                layout:{
                    type:'hbox'
                },
                items:[
                    {
                        xtype:'button',
                        text:'목록보기',
                        handler:()=>{
                            Ext.Viewport.removeAll()
                            Ext.Viewport.add({
                                xtype:'mainview'
                            })
                        }
                    }
                ]
            },
            {
                xtype:'toolbar',
                layout:{
                    type:'hbox',
                    pack:'center'
                },
                items:[
                    {
                        xtype:'button',
                        text:'수정',
                        handler:()=>{
                            console.log('수정 클릭')
                        }
                    },
                    {
                        xtype:'button',
                        text:'삭제',
                        handler:()=>{
                            console.log('삭제 클릭')
                        }
                    }
                ]
            }
        ])

    }
})