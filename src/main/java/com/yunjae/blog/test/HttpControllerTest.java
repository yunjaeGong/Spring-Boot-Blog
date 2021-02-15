package com.yunjae.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자의 요청 -> 응답(HTML) : @Controller

@RestController // 사용자의 요청 -> 응답(Data)
public class HttpControllerTest{

    // http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) { // query string 데이터를 오브젝트에 스프링이 넣어줌
        return "get 요청:" + m.getId() + ", " + m.getUsername()  + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // 브라우저에서는 get 요청만 할 수 있다. : 405에러
    // http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody String text) {
        // return "post 요청:" + m.getId() + ", " + m.getUsername()  + ", " + m.getPassword() + ", " + m.getEmail();
        return "post 요청: " + text;
    }

    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
