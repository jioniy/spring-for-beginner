package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository{
    Optional<Member> findByName(String name);
    /**
     * 인터페이스를 통한 기본적인 CRUD
     * findAll(),save(),delete(), findOne() 등의 메소드는 자동 구현 및 제공
     */
}
