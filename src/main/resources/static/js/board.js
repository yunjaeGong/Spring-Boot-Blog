let index = {
    init:function () {
        $("#btn-save").on("click", ()=> { // function() {}대신 ()=>{} 이유? this를 바인딩 하기 위해
            this.save();
        });

        $("#btn-delete").on("click", ()=> {
            this.delete();
        });

        $("#btn-update").on("click", ()=> {
            this.update();
        });
    },
    save: function () {
        let data = {
            title:$("#title").val(),
            content:$("#content").val()
        };

        // ajax 요청을 이용, 3개의 데이터를 json으로 변경해 insert 요청
        // ajax가 통신에 성공하고 서버가 json을 반환해주면 자동으로 Js 오브젝트로 변환
        $.ajax({ // ajax 호출은 default가 비동기 호출
            type: "POST",
            url: "/api/board/save",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터의 MIME Type 설정
            data_type: "json" // 응답으로 온 데이터가 문자열(json으로 변환 가능) => js 오브젝트로 변경
        }).done(function (resp) { // 성공 시
            alert("글쓰기가 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        let id = $("#id").val();

        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id,
            data_type: "json"
        }).done(function (resp) {
            alert("삭제가 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        let id = $("#id").val();
        let data = {
            title:$("#title").val(),
            content:$("#content").val()
        };
        $.ajax({
            type: "PUT",
            url: "/api/board/" + id + "/reply",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            data_type: "json"
        }).done(function (resp) {
            alert("수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert("/api/board/" + id);
            alert(JSON.stringify(error));
        });
    },
    saveReply: function () {
        let data = {
            userId: $("#userId").val(),
            password: $("#replyPassword").val(),
            boardId: $("#boardId").val(),
            content: $("#replyContent").val(),
            parentId: 0,
            depth: 0,
            rootId: 0,
        };

        // console.log(data);

        $.ajax({
            type: "POST",
            url: `/api/login`,
            data: JSON.stringify({username: data.userId, password: data.password}),
            contentType: "application/json; charset=utf-8",
            data_type: "json"
            // 입력한 로그인 정보로 ajax 로그인 요청
        }).done(function (resp) {
            $.ajax({
                type: "POST",
                url: `/api/board/${data.boardId}/reply`,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                data_type: "json"
            }).done(function (resp) {
                alert("댓글 쓰기가 완료되었습니다.");
                location.href = `/board/${data.boardId}`;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    saveNestedReply: function (rootId) {
        let data = {
            userId: $("#nestedReplyId-" + rootId).val(),
            password: $("#nestedReplyPassword-" + rootId).val(),
            boardId: $("#boardId").val(),
            content: $("#nestedReplyContent-" + rootId).val(),
            parentId: rootId,
            depth: 1,
            rootId: rootId,
        };

        console.log(data);

        $.ajax({
            type: "POST",
            url: `/api/login`, // TODO: join & login을 하도록 바꾸기
            data: JSON.stringify({username: data.userId, password: data.password}),
            contentType: "application/json; charset=utf-8",
            data_type: "json"
            // 입력한 로그인 정보로 ajax 로그인 요청
        }).done(function (resp) {
            $.ajax({
                type: "POST",
                url: `/api/board/${data.boardId}/reply`,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                data_type: "json"
            }).done(function (resp) {
                alert("대댓글 쓰기가 완료되었습니다.");
                location.href = `/board/${data.boardId}`;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replyDelete: function (boardId, replyId) {
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/${replyId}`,
            data_type: "json"
        }).done(function (resp) {
            alert("댓글 삭제가 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

index.init();