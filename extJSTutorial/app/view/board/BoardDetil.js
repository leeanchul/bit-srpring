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
               <h1 id="test">제목: <span id="title">${data.title}</span></h1>
                <h3>작성자: ${data.nickname}</h3>
                <h3>작성일:${data.entryDate}</h3>
                <h3>수정일:${data.modifyDate}</h3>
                <hr>
                <h3>내용</h3>
                <h1> <span id="content">${data.content}</span></h1>
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
                        handler:(button)=>{
                            let login=JSON.parse(sessionStorage.getItem("login"))
                            if(login.id === data.writerId){

                                // html 에 id 값을 가져오기
                                let title = Ext.get('title');
                                let content= Ext.get('content')
                                // title과 content가 Ext.Element 객체인지 확인하고 destroy() 호출

                                // input 으로 바꾸기
                                title.update(`<input type="text" id="inputTitle" value="${title.dom.innerText}">`)
                                content.update(`<input type="text" id="inputContent" value="${content.dom.innerText}">`)
                                
                                button.setText("저장하기")
                                button.setHandler((button,title)=>{
                                    console.log(title)
                                    let boardDTO={
                                        title:Ext.get('inputTitle').dom.value,
                                        content:Ext.get('inputContent').dom.value,
                                        id:data.id
                                    }
                                    Ext.Ajax.request({
                                        url:`http://localhost:8080/api/board/update`,
                                        method:'POST',
                                        jsonData:boardDTO,
                                        success:(resp)=>{
                                            console.log(resp)
                                            Ext.Viewport.removeAll()
                                            Ext.Viewport.add({
                                                xtype:'mainview'
                                            })
                                        }
                                    })
                                })

                            }else{
                                Swal.fire({
                                    icon: 'error',
                                    text:'본인이 작성한 게시글이 아닙니다.',
                                    confirmButtonText: '확인'
                                })
                            }
                        }
                    },
                    {
                        xtype:'button',
                        text:'삭제',
                        handler:()=>{
                            let login=JSON.parse(sessionStorage.getItem("login"))
                            if(login.id === data.writerId){
                                Ext.Ajax.request({
                                    url:`http://localhost:8080/api/board/delete/${data.id}`,
                                    method:'GET',
                                    success:(resp)=>{
                                        console.log(resp)
                                        Ext.Viewport.removeAll()
                                        Ext.Viewport.add({
                                            xtype:'mainview'
                                        })
                                    }
                                })
                            }else{
                                Swal.fire({
                                    icon: 'error',
                                    text:'본인이 작성한 게시글이 아닙니다.',
                                    confirmButtonText: '확인'
                                })
                            }

                        }
                    }
                ]
            }
        ])

    }
})