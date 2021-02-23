let index = {
    init:function () {
        $("#btn-save").on("click", ()=> { // function() {}대신 ()=>{} 이유? this를 바인딩 하기 위해
            this.save();
        });
    },

    save: function () {
        alert('user의 save함수 호출됨');
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };
        console.log(data);

        // ajax 요청을 이용, 3개의 데이터를 json으로 변경해 insert 요청
        $.ajax({ // ajax 호출은 default가 비동기 호출
            type: "POST",
            url: "/api/user/join",
            data: JSON.stringify(data)
        }).done(function () { // 성공 시

        }).fail({ // 실패 시

        })
    } //
}; // joinForm의 btn-save 누르면 save함수 호출됨

index.init();