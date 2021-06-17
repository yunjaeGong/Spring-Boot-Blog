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
        let r = confirm("이 글을 삭제하시겠습니까?");
        if (r === false)
            return;
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
        let r = confirm("글을 수정하시겠습니까?");
        if (r === false)
            return;
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

        this.login(data.userId, data.password)
            .done(function() {
            console.log("댓글 쓰기 요청됨")
            $.ajax({
                type: "POST",
                url: `/api/board/${data.boardId}/reply`,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                data_type: "json",
                headers: {"Authentication": 'Bearer ' + window.token},
            }).done(function (resp) {
                alert("댓글 쓰기가 완료되었습니다.");
                location.href = `/board/${data.boardId}`;
            }).fail(function (error) {
                console.log(JSON.stringify(error));
            });
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    },
    saveNestedReply: function (rootId) {
        let data = {
            userId: $("#nestedReplyUsername-" + rootId).val(),
            password: $("#nestedReplyPassword-" + rootId).val(),
            boardId: $("#boardId").val(),
            content: $("#nestedReplyContent-" + rootId).val(),
            parentId: rootId,
            depth: 1,
            rootId: rootId,
        };

        this.login(data.userId, data.password)
            .done(function (resp) {
            $.ajax({
                type: "POST",
                url: `/api/board/${data.boardId}/reply`,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                data_type: "json",
                headers: {"Authentication": 'Bearer ' + window.token},
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
        let r = confirm("댓글을 삭제하시겠습니까?");
        if (r === false)
            return;
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/${replyId}`,
            data_type: "json",
            headers: {"Authentication": 'Bearer ' + window.token},
        }).done(function (resp) {
            alert("댓글 삭제가 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    modifyReplyTextareaToggle: function (replyId, selector) {
        // let textarea = document.getElementById(selector + "TextareaId-" + replyId);
        let textarea = $(`#${selector}TextareaId-${replyId}`)[0];
        // let origContent = document.getElementById(selector + "ContentId-" + replyId);
        let origContent = $(`#${selector}ContentId-${replyId}`)[0];
        // let btnModify = document.getElementById(selector + "Modify-" + replyId);
        let btnModify = $(`#${selector}Modify-${replyId}`)[0];
        // let spanRegisterCancel = document.getElementById(selector + "OnModify-" + replyId);
        let spanRegisterCancel = $(`#${selector}OnModify-${replyId}`)[0];

        // if (document.getElementById("nestedReplyTextareaId-" + replyId).display === "none") {
        if (textarea.style.display === "none") { // Textarea 열기
            textarea.innerText = origContent.textContent;
            textarea.style.display = "block";
            origContent.style.display = "none";
            btnModify.style.display = "none";
            spanRegisterCancel.style.display = "inline-block";
        } else { // Textarea 닫기
            textarea.style.display = "none"
            origContent.style.display = "block"
            btnModify.style.display = "inline-block";
            spanRegisterCancel.style.display = "none";
        }

    },
    updateReply: function (boardId, replyId, selector) {
        let r = confirm("댓글을 수정하시겠습니까?");
        if (r === false)
            return;
        console.log("Authentication: Bearer " + window.token);
        let content = {content: $(`#${selector}TextareaId-${replyId}`).val()};
        $.ajax({
            type: "PUT",
            url: `/api/board/${boardId}/${replyId}`,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(content),
            headers: {"Authentication": 'Bearer ' + window.token},
            xhrFields: {
                withCredentials: true
            },
        }).done(function (resp) {
            alert("대댓글 수정이 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    login: function (userId, password) {
        $.ajax({
            type: "POST",
            url: `/api/login`, // TODO: join & login을 하도록 바꾸기
            data: JSON.stringify({username: userId, password: password}),
            contentType: "application/json; charset=utf-8",
            data_type: "json",
            success: function(respData, status, xhr) {
                window.token = xhr.getResponseHeader("Authentication");
            }
            // 입력한 로그인 정보로 ajax 로그인 요청
        })
    }

};
index.init();