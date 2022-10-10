package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * section5. 회원 웹 기능 - 홈 화면 추가
 * ViewResolver역할의 HomeController
 */
@Controller
public class HomeController {

    @GetMapping("/")//컨트롤러가 정적 파일보다 우선순위가 높다. == Controller의 "/"을 index.html보다 먼저 찾는다.
    public String home(){
        return "home";//.html을 추가하여 view단 파일 반환
    }
}
