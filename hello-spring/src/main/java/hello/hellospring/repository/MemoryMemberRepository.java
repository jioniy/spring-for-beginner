package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * Repository - 데이터를 저장하는 등 데이터에 접근하는 부분
 */
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();//DB 저장소 역할
    private static long sequence=0L;

    @Override
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long Id){
        return Optional.ofNullable(store.get(Id));
    }

    @Override
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name){
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore(){
        store.clear();
    }
}
