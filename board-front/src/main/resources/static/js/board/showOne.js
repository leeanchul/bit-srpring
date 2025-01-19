$().ready(()=>{
    let url=window.location.href
    let boardId=url.substring(url.lastIndexOf('/')+1)

    $.ajax({
        url:`http://localhost:8080/api/board/showOne/${boardId}`,
        success:(resp)=>{
            console.log(resp)
            print(resp)
            test(resp)
        }
    })

    let login=JSON.parse(sessionStorage.getItem('login'))
    //console.log(login)
})

function print(resp){
    let data=resp.boardDTO
    console.log(data)
    $('#td-title').text(data.title)
    $('#td-nickname').text(data.nickname)
    $('#td-entryDate').text(data.entryDate)
    $('#td-modifyDate').text(data.modifyDate)
    $('#td-content').text(data.content)
}

function test(resp){
    let data=resp.boardDTO
    let aUpdate=document.createElement('a')
    $(aUpdate).addClass('page-link')
    $(aUpdate).attr('href','/board/update/'+data.id)
    $(aUpdate).text("update")

    let aDelete=document.createElement('a')
    $(aDelete).addClass('page-link')
    $(aDelete).attr('href','/board/delete/'+data.id)
    $(aDelete).text("delete")

    $('#update').append(aUpdate)
    $('#delete').append(aDelete)

    $("#delete").on('click',function(){
        $.ajax({
            url:`http://localhost:8080/api/board/delete/${data.id}`,
            success:(resp)=>{
                console.log(resp)
            }
        })
        alert(data.id+"삭제")
        console.log("삭제")
    })
}

