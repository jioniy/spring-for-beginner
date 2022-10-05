package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

/**
 * 데이터 저장소(RDBMS, NoSQL)가 선정되지 않은 상태이므로
 * 인터페이스로 구현 클래스를 변경할 수 있도록 설계
 * 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용
* */

public interface MemberRepository {
    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long Id);//회원 조회
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
