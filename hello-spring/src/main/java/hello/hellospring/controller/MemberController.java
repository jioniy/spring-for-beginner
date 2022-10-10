package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Controller 를 작성하면 Spring Container가 MemberController 객체를 생성하여 Spring에 넣어주고 관리한다.(스프링 빈 관리)
 * Controller - 외부 요청을 받는 부분
 * section5. 회원 웹 기능
 * */
@Controller
public class MemberController {
    private final MemberService memberService;

    /**
     * @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌.
     * DI(Dependency Injection) - 객체 연관관계를 외부에서 넣어주는 것
     */
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    /**
     * 회원가입 폼으로 이동
     * @ResponseBody를 사용하지 않았으므로 기본적으로 ViewResolver로 작동
     */
    @GetMapping(value="/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }


    /**
     * 회원을 실제 등록하는 기능
     * 회원가입 후 "/"으로 redirect하여 home.html로 이동
     */
    @PostMapping(value="/members/new")
    public String create(MemberForm memberForm){
        Member member = new Member();
        member.setName(memberForm.getName());
        System.out.println("Before join = "+member.getId());
        memberService.join(member);
        System.out.println("After join = "+member.getId());
        return "redirect:/";
    }


    /**
     * 조회 기능
     * 모든 회원 정보를 model 객체에 담아 memberList 페이지로 이동
     */
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
