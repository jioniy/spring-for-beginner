package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Controller 를 작성하면 Spring Container가 MemberController 객체를 생성하여 Spring에 넣어주고 관리한다.(스프링 빈 관리)
 * Controller - 외부 요청을 받는 부분
 * */
@Controller
public class MemberController {
    private final MemberService memberService;

    /**
     * @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌.
     * DI(Dependency Injection) - 객체 연관관계를 외부에서 넣어주는 것
     *
     * @param memberService
     */
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
