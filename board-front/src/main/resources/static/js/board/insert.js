$().ready(()=>{
    let login=JSON.parse(sessionStorage.getItem('login'))
    console.log(login.username)
    let info={
        username:login.username
    }
    $.ajax({
        contentType:'application/json;charset=UTF-8',
        url: 'http://localhost:8080/api/user/userInfo',
        type: 'POST',
        data:JSON.stringify(info),
        success:(resp)=>{
            console.log(resp.id)
            $('#btn-insert').on('click', function() {

                let title=$('#title').val()
                let content=$('#content').val()

                if(title === '' || content === ''){
                    alert("제목 내용 입력해라 ㅡㅡ")
                    return
                }
                let data={
                    'title':title,
                    'content':content,
                    'writerId':resp.id
                }
                $.ajax({
                    contentType:'application/json;charset=UTF-8',
                    url: 'http://localhost:8080/api/board/write',
                    type: 'POST',
                    data:JSON.stringify(data),
                    success:(resp)=>{
                        console.log(resp)
                        location.href=`/board/showOne/${resp.boardDTO.id}`
                    },
                    error:(resp)=>{
                        console.log('error')
                        console.log(resp)
                    }
                })
            })


        }
    })


})
