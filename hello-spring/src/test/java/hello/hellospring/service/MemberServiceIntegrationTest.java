package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @SpringBootTest 스프링 컨테이너와 테스트를 함께 실행한다. 이전에 만들었던 테스트는 오직 순수한 자바 코드만을 실행(순수한 자바 코드만을 실행하는 방법으로 테스트케이스를 설계하는 것이 좋다.)
 * @Transactional 테스트케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
 *                이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다. ** 테스트는 반복할 수 있어야 한다.
 */
@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    /**
     * SpringConfig에서 설정한 대로 매핑(MemberService -> MemberService / MemberRepository -> JdbcMemberRepository)
     */
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //Given
        Member member = new Member();
        member.setName("spring_test");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(),findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("spring_test");

        Member member2 = new Member();
        member2.setName("spring_test");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야한다.

        //Then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}
