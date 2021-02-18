package com.yunjae.blog.test;

import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyInsertControllerTest {

    @Autowired // UserRepository가 생성되면 DI
    private UserRepository userRepository;
    // http://localhost:8000/blog/dummy/join 요청
    // http body(form)에 필요한 parameter 데이터를 가지고 요청 시 @RequestParam 없이도 데이터입력

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable); // 페이지 객체 리턴
        List<User> users = pagingUser.getContent();
        return users;
    }

    // {id}주소로 parameter를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3 -> id = 3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
//        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//            @Override
//            public User get() { // DB에 찾는 값이 없으면 이 부분이 실행되고 빈 객체가 생성됨.
//                return new User();
//            }
//        });
        // findById 반환값이 없을수도, Optional이 반환됨
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
            }
        });
//        User user = userRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
//        });

        // 요청: 웹브라우저
        // user 객체 = 자바 object
        // 웹 브라우저가 이해할 수 있는 데이터(json)으로 변환됨(MessageConverter -> Jackson 호출)
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user) { // object로 받을 수 있음
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(UserRoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
