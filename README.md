# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## 📝 스프링 DB 접근 기술


### ✏ 기술 별 핵심 개념 및 코드

<details>
<summary><b>1) 순수 Jdbc</b></summary>

- <b>DataSouce</b>

    : 데이터베이스 커넥션을 획득할 때 사용하는 객체

    : 스프링부트는 <b>데이터베이스 커넥션 정보</b>를 바탕으로 <b>DataSource를 생성하고 스프링 빈으로</b> 만들어둔다. 
    → SpringConfig에서 <b>DI 적용</b> 가능 

<p align="center"><img src="https://user-images.githubusercontent.com/68148196/196693575-1b6c9311-aa37-4f85-9bad-44bb6dcfc87b.JPG" width="500" height="280"/></p>

<br/>

- <b>개방 폐쇄 원칙(OCP, Orpen-Closed Principle)</b>
    - 확장에는 열려있고, 수정, 변경에는 닫혀있다.
    - 스프링의 <b>DI</b>를 사용하면 <b>기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경</b>할 수 있다.(SpringConfig.java)
    - 객체 지향의 <b>다형성</b> 활용

<br/>
       
- <b>JdbcMemberRepository.java</b>

```java
public class JdbcMemberRepository implements MemberRepository {

  private final DataSource dataSource;

  public JdbcMemberRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Member save(Member member) {
    String sql = "insert into member(name) values(?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, member.getName());

      pstmt.executeUpdate();
      rs = pstmt.getGeneratedKeys();

      if (rs.next()) {
        member.setId(rs.getLong(1));
      } else {
        throw new SQLException("id 조회 실패");
      }
      return member;

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }
    . . . 
}
```


<br/>
   
- <b>SpringConfig.java</b>

```java
@Configuration
public class SpringConfig {
  private final DataSource dataSource;

  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    // return new MemoryMemberRepository();
    return new JdbcMemberRepository(dataSource);
  }
}

```
</details>

       
<details>
<summary><b>2) 스프링 JdbcTemplate</b></summary>

- 순수 Jdbc와 동일한 환경 설정

- <b>이 라이브러리(스프링 JdbcTemplate, MyBatis 등)</b>는 <b>JDBC API에서 반복 코드를 제거</b>해주지만, <b>SQL문은 직접 작성</b>해야 한다.

<br/>
   
- <b>JdbcTemplateMemberRepository.java</b>

```java
public class JdbcTemplateMemberRepository implements MemberRepository {
  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Member save(Member member) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", member.getName());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    member.setId(key.longValue());
    return member;
  }
    . . .
}
```

<br/>
   
- <b>SpringConfig.java</b>

```java
@Configuration
public class SpringConfig {
  private final DataSource dataSource;

  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    // return new MemoryMemberRepository();
    // return new JdbcMemberRepository(dataSource);
    return new JdbcTemplateMemberRepository(dataSource);
  }
}
```
</details>
<details>
<summary><b>3) JPA</b></summary>

<br/>
   
- <b>JPA(Java Persistent API)</b>

  - 자바 ORM 기술에 대한 API 표준 명세

  - <b>ORM을 사용하기 위한 인터페이스를 모아둔 것</b>
  
  - JPA는 자바 진영의 표준 기술이며, 여러 업체들이 구현한 결과물이 있다.
    ex) Hibernate, EclipseLink, DataNucleus
    
  - JPA를 사용하기 위해서는 <b>JPA를 구현한 Hibernate, EclipseLink, DataNucleus</b> 같은 <b>ORM 프레임워크</b>를 사용해야 한다.

<br/>
   
- <b>ORM(Object Relational Mapping)</b>

  - 객체와 DB 테이블이 매핑을 이루는 것
  
  - SQL 쿼리가 아닌 직관적인 메서드로 데이터를 조작
  
  - 메서드 호출만으로 query수행 → <b>생산성이 매우 높아짐</b>
  
  - <b>query가 복잡해지면 ORM으로 표현하는데 한계</b>가 있고, 성능이 raw query에 비해 느리다는 단점
  →  <b>JPQL, QueryDSL 등을 사용<b>하거나 한 프로젝트 내에서 <b>Mybatis</b>와 JPA를 같이 사용

<br/>
   
- <b>JPA 엔티티 매핑</b> - Member.java

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity /*@Entity로 JPA가 관리하는 객체임을 알려준다.*/
public class Member {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
    ...
```

<br/>
   

- <b>JpaMemberRepository.java</b>

```java
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
  private final EntityManager em;

  public JpaMemberRepository(EntityManager em) {
    this.em = em;
  }

  public Member save(Member member) {
    em.persist(member);
    return member;
  }

  public Optional<Member> findById(Long id) {
    Member member = em.find(Member.class, id);
    return Optional.ofNullable(member);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList(); /*JPQL 사용*/
  }
   . . .
```

<br/>
   

- <b>서비스 계층의 트랙잭션 추가</b> - MemberService.java

  - <b>JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행</b>해야한다.

  - 스프링은 <b>해당 클래스의 메서드를 실행할 때 트랜잭션을 시작</b>하고, <b>메서드가 정상 종료되면 트랜잭션을 커밋</b>한다.
  
  - 런타임 예외가 발생하면 롤백한다.

```java
import org.springframework.transaction.annotation.Transactional

@Transactional
public class MemberService {}
```

- <b>SpringConfig.java</b>

```java
@Configuration
public class SpringConfig {

  private final EntityManager em;

  public SpringConfig(EntityManager em) {
    this.em = em;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    // return new MemoryMemberRepository();
    // return new JdbcMemberRepository(dataSource);
    // return new JdbcTemplateMemberRepository(dataSource);
    return new JpaMemberRepository(em);
  }
}
```
</details>
<details>
<summary><b>4) 스프링 데이터 JPA</b></summary>

<br/>
   
- <b>스프링 데이터 JPA</b>

  - repository 에 구현 클래스 없이 <b>인터페이스</b> 만으로 개발
  
  - <b>CRUD 제공</b> → 개발자는 핵심 비지니스 로직을 개발하는데 집중할 수 있다.
  
  - <b>JPA를 편리하게 사용</b>하도록 도와주는 기술
  
  - 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용
  
  - 복잡한 동적 쿼리는 Querydsl 라이브러리 사용 + JdbcTemplate

- <b>SpringDataJpaRepository.java</b>

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
  /*인터페이스를 통한 기본적인 CRUD
  * findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
  * 페이징 기능 자동 제공
  * */
  Optional<Member> findByName(String name);
}
```


- <b>SpringConfig.java</b>
- <b>스프링 데이터 JPA</b>가 <b>SpringDataJpaMemberRepository 를 스프링 빈으로 자동 등록</b>

```java
@Configuration
public class SpringConfig {
  private final MemberRepository memberRepository;

  public SpringConfig(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository);
  }
}
```
<p align="center"><img src="https://user-images.githubusercontent.com/68148196/196695498-943cefc1-0b32-44ee-9c24-4e94730269b7.png" width="350" height="650" /></p>    
</details>

