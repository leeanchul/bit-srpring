// 로그인 화면을 담당할 Login.js
// 해당 컴포넌트 이름 정의 , 설정 객체
Ext.define('extJSTutorial.view.login.Login',{
    extend:'Ext.form.Panel',
    xtype:'loginview',

    title:'로그인',

    items:[
        {
            xtype:'textfield', name: 'username' ,label:'아이디'
        },
        {
            xtype:'passwordfield',name:'password',label:"비번"
        },
        {
            xtype:'button' ,text:'로그인',handler:(button)=>{
                // 파라미터로 들어온 button은 현재 눌린 버튼을 뜻한다.
                // 그리고 그 버튼의 상위 컴포넌트 중 'formpanel' 을 찾는
                // 함수가 바로 .up('formpanl')이 된다.
                let form= button.up('formpanel')
                let values=form.getValues()

                Ext.Ajax.request({
                    url:'http://localhost:8080/api/user/auth',
                    method:'POST',
                    jsonData:values,
                    success:(resp)=>{
                        let data=JSON.parse(resp.responseText)

                        if(data.result === 'success!'){
                            sessionStorage.setItem('login',JSON.stringify(data.login))
                            Ext.Viewport.removeAll()
                            Ext.Viewport.add({
                                xtype:'mainview'
                            })
                        }else{
                            Swal.fire({
                                icon: 'error',
                                confirmButtonText: '확인'
                            })
                        }
                    }
                })
            }
        },
        {
            xtype:'button',text:'회원 가입', handler:(button)=>{
                Ext.Viewport.removeAll()
                Ext.Viewport.add({
                    xtype:'registerview'
                })
            }
        }
    ]
})
