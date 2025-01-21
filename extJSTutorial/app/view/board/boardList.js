Ext.define('extJSTutorial.view.board.BoardList', {
    extend: 'Ext.grid.Grid',
    xtype: 'boardlistview',
    title: '게시판',

    // store는 현재 view 가 열릴 때 기본적으로 필요한 데이터들을
    // 불러오는 코드이다.
    // 주로 ajxa 코드를 여기에 적지만
    // 우리는 일단 여기에 하드코딩으로 객체를 만든다.
    // store: Ext.create('Ext.data.Store', {
    //     fields: ['id', 'title', 'writerId', 'content', 'nickname', 'entryDate', 'modifyDate'],
    //     pageSize: 5,
    //     data: [
    //         {
    //             id: 1,
    //             title: '제목 1',
    //             writerId: 1,
    //             content: '내용 1',
    //             nickname: '작성자 1',
    //             entryDate: '2025-01-01',
    //             modifyDate: '2025-01-01'
    //         },
    //         {
    //             id: 2,
    //             title: '제목 2',
    //             writerId: 2,
    //             content: '내용 2',
    //             nickname: '작성자 2',
    //             entryDate: '2025-01-02',
    //             modifyDate: '2025-01-02'
    //         },
    //         {
    //             id: 3,
    //             title: '제목 3',
    //             writerId: 3,
    //             content: '내용 3',
    //             nickname: '작성자 3',
    //             entryDate: '2025-01-03',
    //             modifyDate: '2025-01-03'
    //         },
    //         {
    //             id: 4,
    //             title: '제목 4',
    //             writerId: 4,
    //             content: '내용 4',
    //             nickname: '작성자 4',
    //             entryDate: '2025-01-04',
    //             modifyDate: '2025-01-04'
    //         },
    //         {
    //             id: 5,
    //             title: '제목 5',
    //             writerId: 5,
    //             content: '내용 5',
    //             nickname: '작성자 5',
    //             entryDate: '2025-01-05',
    //             modifyDate: '2025-01-05'
    //         },
    //         {
    //             id: 6,
    //             title: '제목 6',
    //             writerId: 6,
    //             content: '내용 6',
    //             nickname: '작성자 6',
    //             entryDate: '2025-01-06',
    //             modifyDate: '2025-01-06'
    //         },
    //         {
    //             id: 7,
    //             title: '제목 7',
    //             writerId: 7,
    //             content: '내용 7',
    //             nickname: '작성자 7',
    //             entryDate: '2025-01-07',
    //             modifyDate: '2025-01-07'
    //         },
    //         {
    //             id: 8,
    //             title: '제목 8',
    //             writerId: 8,
    //             content: '내용 8',
    //             nickname: '작성자 8',
    //             entryDate: '2025-01-08',
    //             modifyDate: '2025-01-08'
    //         },
    //         {
    //             id: 9,
    //             title: '제목 9',
    //             writerId: 9,
    //             content: '내용 9',
    //             nickname: '작성자 9',
    //             entryDate: '2025-01-09',
    //             modifyDate: '2025-01-09'
    //         },
    //         {
    //             id: 10,
    //             title: '제목 10',
    //             writerId: 10,
    //             content: '내용 10',
    //             nickname: '작성자 10',
    //             entryDate: '2025-01-10',
    //             modifyDate: '2025-01-10'
    //         }
    //     ],
    //     // proxy는 이 store 가 값을 어떤 방식으로 불러온느지 지정한다.
    //     // 우리는 메모리에 위의 데이터를 저장하고 사용할 거기 때문에
    //     proxy: {
    //         type: 'memory',
    //         enablePaging: true
    //     },
    //     autoLoad: true
    // }),

    store:{
      type:'boards'
    },


    // grid 가 기본적으로 표의 모양을 따라가기 때문에
    // 최상단에 해당 컬럼의 이름을 지정한다.
    columns: [
        // text는 해당 컬럼의 제목, dateIndex는 위의 data에서 어떤 것과 연결 시킬지, flex 해당 컬럼의 길이를 상대적으로 정한다.
        {text: '글 번호', dataIndex: 'id', flex: 1},
        {text: '제목', dataIndex: 'title', flex: 4},
        {text: '작성자', dataIndex: 'writerId', flex: 1},
        {text: '작성일', dataIndex: 'entryDate', flex: 1},
        {text: '수정일', dataIndex: 'modifyDate', flex: 1},

    ],
    // listener 는 이벤트들을 묶어서 관리할 때 사용이 된다.
    // handler 는 한개의 이벤트(주로 클릭) 을 처리할 때 사용이 된다.
    // 여러개의 이벤트 처리를 만들어줄 예정이라 리슨너를 사용한다.

    listeners: {
        // 위의 개별 아이템이 선택될 때 실행할 코드
        select: (grid, record) => {
            // 1. 그리드에서 선택된 항목을 모두 해제
            grid.deselectAll();
            // 2. 선택된 항목의 세부 정보를 새로운 화면에 전달
            Ext.Viewport.removeAll()
            // 선택된 항목의 데이터(record)를 새로운 화면에 전달
            Ext.Viewport.add({
                xtype: 'boarddetailview',
                record: record
            })
        },

        // 화면에 렌더링이 끝난 후에 동적으로 무언가를 처리해야할 경우에는
        // painted 라는 어틀뷰트에 정의를 하게 된다.
        // 구 버전에서는 onrender 라는 이름의 어트리뷰트 였지만
        // 뉴 버전에는 painted가 되었다.
        painted: (grid) => {
            // 버튼 추가할 배열을 만들어준다.
            let buttons = []
            for (let i = 1; i <= 5; i++) {
                // JS 배열에 요소를 추가할 때에는
                // push를 사용한다.

                buttons.push({
                    xtype: 'button',
                    text: String(i),
                    handler: () => {
                        let store = grid.getStore()
                        store.loadPage(i)
                    }
                })


            }

            // 위에서 만든 버튼들을 pagination toolbar에 추가한다.
            // down()은 자식 중에서 파라미터와 일치하는 자식을 찾는다.
            let toolbar = grid.down('toolbar[docked=null]')
            toolbar.add(buttons)
        }
    },


    items: [
        {
            xtype: 'toolbar',
            docked: 'top',
            layout: {
                type: 'hbox',
                pack: 'end'
            },
            items: [
                {
                    xtype: 'button',
                    text: '글 작성하기',
                    handler: () => {
                        Ext.Viewport.removeAll()
                        Ext.Viewport.add({
                            xtype: 'boardformview'
                        })
                    }
                }
            ]
        },
        {
            // 페이지 네이션에서 사용할 툴바
            xtype: 'toolbar',
            // 어디에 고정시키는 것이 아니라 우리 게시판 목록 다음에 나올 수 있도록
            // doked 설정 하지 않는다
            layout: {
                type: 'hbox',
                pack: 'center'
            },
            items: []

        }
    ]


})