Ext.define('extJSTutorial.view.register.Register',{
    extend:'Ext.form.Panel',
    xtype:'registerview',
    
    title:'회원 가입',
    
    items:[
        { xtype:'textfield' , name: 'username' ,label:'아이디'},
        { xtype:'passwordfield' , name: 'password' ,label:'비밀번호'},
        { xtype:'textfield' , name: 'nickname' ,label:'닉네임'},
        { xtype:'button', text:'회원가입' ,handler:(button)=>{
            let form= button.up('formpanel')
            let values=form.getValues()

                Ext.Ajax.request({
                    url: 'http://localhost:8080/api/user/register',
                    method: 'POST',
                    jsonData: values,
                    success:(resp)=>{
                        let result=JSON.parse(resp.responseText)
                        if(result.result === 'success'){
                            Ext.Viewport.removeAll()
                            Ext.Viewport.add({
                                xtype:'loginview'
                            })
                        }else{
                            Swal.fire({
                                icon:'error',
                                title:result.message
                            })
                        }

                    }
                })
        }
        }]
})