package com.yunjae.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자의 요청 -> 응답(HTML) : @Controller

@RestController // 사용자의 요청 -> 응답(Data)
public class HttpControllerTest{
    private static final String TAG = "HttpControllerTest: ";

    // http://localhost:8000/blog/
    // context-path : /blog

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("ssar").password("1234").email("ssar@email.com").build(); // @Builder 빌터 패턴 사용시 아규먼트 순서 x
        System.out.println(TAG + "getter :" + m.getUsername());
        m.setUsername("cos");
        System.out.println(TAG + "getter :" + m.getUsername());
        return "lombok test 완료";
    }


    @GetMapping("/http/get")
    public String getTest(Member m) { // query string 데이터를 오브젝트에 스프링이 넣어줌
        return "get 요청:" + m.getId() + ", " + m.getUsername()  + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // 브라우저에서는 get 요청만 할 수 있다. : 405에러
    @PostMapping("/http/post")
    public String postTest(@RequestBody String text) {
        // return "post 요청:" + m.getId() + ", " + m.getUsername()  + ", " + m.getPassword() + ", " + m.getEmail();
        return "post 요청: " + text;
        // application/json이 아닌 text/plain으로 보내면 그대로 출력
        // aplication/json으로 보낸 데이터를 Member m으로 받으면 자동으로 파싱 -> MessageConverter(spring boot)
    }

    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
