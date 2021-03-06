let index = {
    init:function () {
        $("#btn-save").on("click", ()=> { // function() {}대신 ()=>{} 이유? this를 바인딩 하기 위해
            this.save();
        });
        $("#btn-update").on("click", ()=> {
            this.save();
        });
        /*$("#btn-login").on("click", ()=> { // function() {}대신 ()=>{} 이유? this를 바인딩 하기 위해
            this.login();
        });*/
    },

    save: function () {
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };

        // ajax 요청을 이용, 3개의 데이터를 json으로 변경해 insert 요청
        // ajax가 통신에 성공하고 서버가 json을 반환해주면 자동으로 Js 오브젝트로 변환
        $.ajax({ // ajax 호출은 default가 비동기 호출
            type: "POST",
            url: "/auth/join",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터의 MIME Type 설정
            data_type: "json" // 응답으로 온 데이터가 문자열(json으로 변환 가능) => js 오브젝트로 변경
        }).done(function (resp) { // 성공 시
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
            // 실패 시
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },
    update: function () {
        let id = $("#id").val();
        let data = {
            password: $("#password").val(),
            email: $("#email").val()
        };

        // ajax 요청을 이용, 3개의 데이터를 json으로 변경해 insert 요청
        // ajax가 통신에 성공하고 서버가 json을 반환해주면 자동으로 Js 오브젝트로 변환
        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            data_type: "json"
        }).done(function (resp) {
            alert("회원정보 수정이 완료되었습니다.");
            location.href = "/user/updateForm";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }

    /*login: function () {
    // spring security 이용하지 않는 로그인
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
        };

        $.ajax({ // ajax 호출은 default가 비동기 호출
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", // body 데이터의 MIME Type 설정
            data_type: "json" // 응답으로 온 데이터가 문자열(json으로 변환 가능) => js 오브젝트로 변경
        }).done(function (resp) { // 성공 시
            alert("로그인이 완료되었습니다.");
            // alert(resp);
            location.href = "/";
            // 실패 시
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }*/
}; // joinForm의 btn-save 누르면 save함수 호출됨

index.init();