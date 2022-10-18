package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * Component 스캔 방식 대신에 자바 코드로 스프링 빈을 직접 등록
 * @Service, @Repository, @Autowired 애노테이션을 모두 지우고 진행
 */
@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository){
        /**
         * Spring Data JPA가 SpringDataJpaRepository를 스프링 빈으로 자동 등록 해준다.
         */
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

}
