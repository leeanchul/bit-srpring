$().ready(()=>{
    // 로그인 버튼을 누를 시.
    // jquery의 ajax를 사용해
    // 로그인을 시도해본다.
    $('#btn-login').on('click',e=>{
        let username=$('#input-username').val()
        let password=$('#input-password').val()

        if(username === '' || password === ''){
            alert("아디 비번 눌러라")
            return;
        }
        let data={
            'username':username,
            'password':password
        }

        $.ajax({
            contentType:'application/json;charset=UTF-8',
            url: 'http://localhost:8080/api/user/auth',
            type: 'POST',
            data:JSON.stringify(data),
            success: (resp)=>{
                if(resp.result === 'success!'){
                    sessionStorage.setItem("login",JSON.stringify(resp.login))
                    location.href='/board/showAll/1'
                }
            },
            error:(resp)=>{
                console.log('error')
                console.log(resp)
            }
        })

    })
    $('#btn-register').on('click',e=>{
        // 회원 가입 버튼을 누를 시,
        // /user/register 로 이동시킨다.
        location.href='/user/register'
    })
})