$().ready(()=>{
    let login=JSON.parse(sessionStorage.getItem('login'))
    console.log(login)
    let info={
        username:login.username
    }

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
                    'writerId':login.id
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

})
