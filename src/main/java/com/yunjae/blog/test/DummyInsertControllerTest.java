package com.yunjae.blog.test;

import com.yunjae.blog.model.User;
import com.yunjae.blog.model.UserRoleType;
import com.yunjae.blog.repository.UserRepository;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyInsertControllerTest {

    @Autowired // UserRepository가 생성되면 DI
    private UserRepository userRepository; // 의존성 주입(DI)

    // Delete
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id) {
        try {
            userRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            return "id: " + id + " 삭제에 실패했습니다. 해당 아이디는 DB에 없습니다.";
        }

        return "id: " + id + "삭제 되었습니다.";
    }


    // Update
    // email, password
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        // http://localhost:8000/blog/dummy/user/1 id = 1, body = json
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("id: " + id + " 수정에 실패했습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user); // save는 insert용 -> requestBody에 포함되지 않은 데이터는 null. 객체를 존재하는 데이터를 불러와 만든 후 저장
        // Dirty Checking - Transactional: 메소드 끝날 때 Dirty 상태인지 확인 후 commit

        return user;
    }

    // http://localhost:8000/blog/dummy/users 모든 유저들 정보 반환
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // http://localhost:8000/blog/dummy/user?page=x
    @GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable); // 페이지 객체 리턴
        List<User> users = pagingUser.getContent(); // Page객체가 아닌 Page 내 Content만 반환
        return pagingUser;
    }

    // {id}주소로 parameter를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3 -> id = 3 (PathVariable)
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
                return new IllegalArgumentException("id: " + id + " 해당 유저는 없습니다."); // GlobalExceptionHandler: handleArgumentException에 의해 처리됨
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

    // http://localhost:8000/blog/dummy/join으로 Post요청
    // http body(form)에 필요한 parameter 데이터를 가지고 요청 시 @RequestParam 없이도 데이터입력
    @PostMapping("/dummy/join")
    public String join(User user) { // object로 받을 수 있음 (form 이용)
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
