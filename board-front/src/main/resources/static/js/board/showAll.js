$().ready(()=>{

    let currentUrl=window.location.href
    let pageNo=currentUrl.substring(currentUrl.lastIndexOf('/')+1)

    $.ajax({
        url:'http://localhost:8080/api/board/showAll/'+pageNo,
        success:(resp)=>{
            resp.list.map(item=>{
                addRow(item)
            })
            addPaginationRow(resp)
            console.log(resp)
        }
    })
})

function addRow(data){
    let tr=document.createElement('tr')
    let idTd=document.createElement('td')
    $(idTd).text(data.id)
    let titleTd=document.createElement('td')
   $(titleTd).text(data.title)
    let nicknameTd=document.createElement('td')
    $(nicknameTd).text(data.nickname)
    let entryDateTd=document.createElement('td')
    $(entryDateTd).text(data.formattedEntryDate)
    let modifyDateTd=document.createElement('td')
    $(modifyDateTd).text(data.formattedModifyDate)

    tr.append(idTd)
    tr.append(titleTd)
    tr.append(nicknameTd)
    tr.append(entryDateTd)
    tr.append(modifyDateTd)

    let addr='location.href="/board/showOne/'+data.id+'"'

    $(tr).attr('onclick',addr)

    $('#tb-board').append(tr)
}

function addPaginationRow(resp){
    let startPage=resp.startPage
    let endPage=resp.endPage
    let maxPage=resp.maxPage
    let currentPage=resp.currentPage

    let tr=document.createElement('tr');
    let td=document.createElement('td')
    $(td).attr('colspan',5)
    $(td).addClass('text-center justify-content-center align-items-center')

    let ul=document.createElement('ul')
    $(ul).addClass('pagination justify-content-center')

    let firstLi=document.createElement("li")
    $(firstLi).addClass('page-item')

    let firstAnchor=document.createElement("a")
    $(firstAnchor).addClass('page-link')
    $(firstAnchor).attr('href','/board/showAll/1')
    $(firstAnchor).text('prev')

    $(firstLi).append(firstAnchor)
    $(ul).append(firstLi)

    for(let i=startPage; i<=endPage; i++){
        let li=document.createElement('li')
        $(li).addClass('page-item')

        let anchor=document.createElement('a')
        $(anchor).addClass('page-link')
        $(anchor).attr('href','/board/showAll/'+i)
        $(anchor).text(i)

        if(i === currentPage){
            $(anchor).addClass("active")
        }
        $(li).append(anchor)
        $(ul).append(li)
    }
    let maxLi=document.createElement("li")
    $(maxLi).addClass('page-item')

    let maxAnchor=document.createElement("a")
    $(maxAnchor).addClass('page-link')
    $(maxAnchor).attr('href','/board/showAll/'+maxPage)
    $(maxAnchor).text('next')

    $(maxLi).append(maxAnchor)
    $(ul).append(maxLi)

    $(td).append(ul)
    $(tr).append(td)
    $('#tb-board').append(tr)
}