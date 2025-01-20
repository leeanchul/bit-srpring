
$().ready(()=>{
    $('#input-content').summernote({
        height:500,
    });

    $('#div-btn-insert').on('click',e=>{
        // 제목이 입력되었는지 체크
        let title=$('#input-title').val()
        if(title===''){
            Swal.fire({
                text:'제목을 입력하세요.',
                icon:'error'
            }).then(()=>{
                $('#input-title').focus()
            })
            return
        }
        // 내용이 입력되었는지 체크
        // let content=$('#input-content').val()
        let content=$('#input-content').summernote('code')
        console.log(content)
        // if(content===''){
        if(content ==='<p><br></p>'){
            Swal.fire({
                text:'내용을 입력하세요.',
                icon:'error'
            }).then(()=>{
                $('#input-content').focus()
            })
            return
        }

        // 전부다 입력이 될 떄만 입력 누르기
        let login=JSON.parse(sessionStorage.getItem('login'))
        let boardDTO={
            'title':title,
            'content':content,
            'writerId':login.id
        }
        $.ajax({
            contentType:'application/json;charset=UTF-8',
            url:'http://localhost:8080/api/board/write',
            type:'POST',
            data:JSON.stringify(boardDTO),
            success:(resp)=>{
                location.href=`/board/showOne/${resp.boardDTO.id}`
            }
        })
    })
})