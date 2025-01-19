$().ready(()=>{
    let url=window.location.href
    let boardId=url.substring(url.lastIndexOf('/')+1)
    console.log(boardId)

    $.ajax({
        url:`http://localhost:8080/api/board/showOne/${boardId}`,
        success:(resp)=>{
            console.log(resp)
            print(resp)
            update(resp)
        }

    })
})

function print(resp){
    let boardDTO=resp.boardDTO

    $('#title').val(boardDTO.title)
    $('#content').val(boardDTO.content)

}


function update(resp){
    let boardDTO=resp.boardDTO

    $('#btn-update').on('click',e=>{
        let title=$('#title').val()
        let content=$('#content').val()

        if(title === ''){
            title = boardDTO.title
        }

        if(content === ''){
            content = boardDTO.content
        }

        let data={
            'title':title,
            'content':content,
            'id':boardDTO.id
        }
        $.ajax({
            contentType:'application/json;charset=UTF-8',
            url: `http://localhost:8080/api/board/update`,
            type:'POST',
            data:JSON.stringify(data),
            success: (resp)=>{
                console.log(resp)
                if(resp.result === 'success'){
                    alert("수정 성공!!")
                    location.href=`/board/showOne/${resp.boardDTO.id}`
                }
            },
            error:(resp)=>{
                console.log('error')
                console.log(resp)
            }
        })
    })
}