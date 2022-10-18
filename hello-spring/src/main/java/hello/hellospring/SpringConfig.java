package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Component 스캔 방식 대신에 자바 코드로 스프링 빈을 직접 등록
 * @Service, @Repository, @Autowired 애노테이션을 모두 지우고 진행
 */
@Configuration
public class SpringConfig {
    /* repository 설정 변경 */
    /**
     * Spring이 application.properties의 설정에 따라 자동으로 스프링 빈 생성 및 관리
     */
    //1. @Autowired DataSource dataSource;
    //2.
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        /**
         * repository 교체 - 다형성의 활용
         * 인터페이스를 두고 구현체를 바꾸기 - Dependency Injection
         * 이 기능을 극대화 시켜주는 것이 Spring의 장점이다.
         * */
        // return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
