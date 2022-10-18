package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service - 비지니스 로직을 만드는 부분
 * JPA - Service 계층에 @Transactional 추가
 *     - JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야한다.
 *     - 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커밋한다. 만약 런타임 예외가 발생하면 롤백한다.
 * */
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /*Service 계층은 개발보다 비지니스에 가까운 용어를 사용해야함*/
    /**
    * 회원 리포지토리의 코드가
    * 회원 서비스 코드를 DI 가능하게 변경한다.
     * 생성자 주입
     */
    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
