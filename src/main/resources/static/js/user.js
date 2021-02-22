let index = {
    init:function () {
        $("#btn-save").on("click", ()=> {
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

    } //
}; // joinForm의 btn-save 누르면 save함수 호출됨

index.init();