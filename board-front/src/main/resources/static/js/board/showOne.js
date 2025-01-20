$().ready(() => {
    let url = window.location.href
    let boardId = url.substring(url.lastIndexOf('/') + 1)

    $.ajax({
        url: `http://localhost:8080/api/board/showOne/${boardId}`,
        success: (resp) => {
            print(resp)
            editLink(resp)
        }
    })

    let login = JSON.parse(sessionStorage.getItem('login'))

    $.ajax({
        url: `http://localhost:8080/api/reply/showAll/${boardId}`,
        success: (resp) => {
            printReply(resp.list)
        }
    })

    $('#btn-reply').on('click', (e) => {
        let content = $('#input-new-reply').val()
        let boardDTO = {
            'content': content,
            'boardId': parseInt(boardId),
            'writerId': login.id
        }
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            url: `http://localhost:8080/api/reply/write`,
            type: 'POST',
            data: JSON.stringify(boardDTO),
            success: (resp) => {
                window.location.reload()
            }
        })
    })

    $('#btn-update-board').on('click',e=>{
        let titleUpdate=document.createElement('input')
        $(titleUpdate).addClass('form-control')
        $(titleUpdate).attr('id','input-update')
        $(titleUpdate).val($('#td-title').text())

        $('#td-title').text('')
        $('#td-title').append(titleUpdate)

        let summernote=document.createElement('div')
        let content=$('#td-content').html()
        $('#td-content').text('')
        $('#td-content').append(summernote)

        $(summernote).summernote('code',content)


        // $(inputUpdate).addClass('form-control')
        // $(inputUpdate).attr('id','input-update')
        // $(inputUpdate).val($('#td-content').text())

        // $('#td-content').text('')
        // $('#td-content').append(inputUpdate)

        $('#btn-update-board').hide()
        $('#btn-delete-board').hide()

        let btnUpdateFinal = document.createElement('button')
        $(btnUpdateFinal).addClass('btn btn-warning')
        $(btnUpdateFinal).text('수정')
        $(btnUpdateFinal).on('click',e=>{
            let boardDTO={
                title:$(titleUpdate).val(),
                // content:$(inputUpdate).val(),
                content:$(summernote).summernote('code'),
                id:boardId
            }
            $.ajax({
                contentType:'application/json;charset=UTF-8',
                url: `http://localhost:8080/api/board/update`,
                type:'POST',
                data:JSON.stringify(boardDTO),
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
        $('#td-content').append(btnUpdateFinal)
    })

    $('#btn-delete-board').on('click',e=>{
        Swal.fire({
            title:'경고!!!',
            text:'정말로 삭제하겠습니까?',
            icon:'error',
            showDenyButton:true,
            confirmButtonText:'예',
            denyButtonText:'아니오'

        }).then(result=>{
            if(result.isConfirmed){
                    $.ajax({
                        url:`http://localhost:8080/api/board/delete/${boardId}`,
                        success:(resp)=>{
                            Swal.fire({
                                text:'삭제되었습니다.',
                                icon:'success'
                            }).then(()=>{
                                location.href='/board/showAll/1'
                            })
                        }
                    })
            }
        })
    })
})

function print(resp) {
    let data = resp.boardDTO
    $('#td-title').text(data.title)
    $('#td-nickname').text(data.nickname)
    $('#td-entryDate').text(data.formattedEntryDate)
    $('#td-modifyDate').text(data.formattedModifyDate)
    // $('#td-content').text(data.content)
    $('#td-content').html(data.content)
}

function editLink(resp) {
    let data = resp.boardDTO
    let aUpdate = document.createElement('a')
    $(aUpdate).addClass('page-link')
    $(aUpdate).attr('href', '/board/update/' + data.id)
    $(aUpdate).text("update")

    let aDelete = document.createElement('a')
    $(aDelete).addClass('page-link')
    $(aDelete).attr('href', '/board/delete/' + data.id)
    $(aDelete).text("delete")

    $('#update').append(aUpdate)
    $('#delete').append(aDelete)

    $("#delete").on('click', function () {
        $.ajax({
            url: `http://localhost:8080/api/board/delete/${data.id}`,
            success: (resp) => {
                console.log(resp)
            }
        })
        alert(data.id + "삭제")
        console.log("삭제")
    })
}

function printReply(list) {
    let login = JSON.parse(sessionStorage.getItem('login'))

    list.map((item, index) => {
        console.log(item)
        if (login.id !== item.writerId) {
            printOthersReply(item)
        } else {
            printMyReply(item, index)
        }
    })
}

function printMyReply(item, index) {
    let tr = document.createElement('tr')
    let nicknameTd = document.createElement('td')
    $(nicknameTd).text(item.nickname)
    $(tr).append(nicknameTd)

    let inputTd = document.createElement('td')
    let replyInput = document.createElement('input')
    $(replyInput).addClass('form-control')
    $(replyInput).attr('id', 'reply-input-' + index)
    $(replyInput).val(item.content)
    $(inputTd).append(replyInput)
    $(tr).append(inputTd)

    let dateTd = document.createElement('td')
    // 날짜 format 하기
    let date = item.entryDate < item.modifyDate ? item.modifyDate : item.entryDate
    let formatted = date.substring(0, 10)

    $(dateTd).text(formatted)
    $(tr).append(dateTd)

    let updateTd = document.createElement('td')
    let updateButton = document.createElement('button')
    $(updateButton).text('수정')
    $(updateButton).addClass('btn btn-outline-info')
    $(updateButton).on('click', e=>{
        let updatingContent=$('#reply-input-'+index).val()
        let updatingData={
            'content':updatingContent,
            'id':item.id
        }
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            url: `http://localhost:8080/api/reply/update`,
            type: 'POST',
            data: JSON.stringify(updatingData),
            success: (resp) => {
                window.location.reload()
            }
        })
    })

    $(updateTd).append(updateButton)
    $(tr).append(updateTd)

    let deleteTd=document.createElement('td')
    let deleteButton=document.createElement('button')

    $(deleteButton).addClass('btn btn-outline-danger')
    $(deleteButton).text('삭제')

    $(deleteButton).on('click',e=>{

        $.ajax({
            url:`http://localhost:8080/api/reply/delete/${item.id}`,
            success:(resp)=>{
                location.reload()
            }
        })
    })


    $(deleteTd).append(deleteButton)
    $(tr).append(deleteTd)

    $('#tbody-reply').append(tr)
}


function printOthersReply(item) {
    let tr = document.createElement('tr')

    let nicknameTd = document.createElement('td')
    $(nicknameTd).text(item.nickname)
    $(tr).append(nicknameTd)

    let contentTd = document.createElement('td')
    // let contentInput=document.createElement('input')
    // $(contentInput).addClass('form-control')

    $(contentTd).text(item.content)
    $(contentTd).attr('colspan', 3)
    $(tr).append(contentTd)

    let dateTd = document.createElement('td')
    // 날짜 format 하기
    let date = item.entryDate < item.modifyDate ? item.modifyDate : item.entryDate
    let formatted = date.substring(0, 10)

    $(dateTd).text(formatted)
    $(tr).append(dateTd)

    $('#tbody-reply').append(tr)
}
