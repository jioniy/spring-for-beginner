# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## 📝 회원 관리 예제 -  개발

<br/>

#### ✏ 비지니스 요구사항 정리

**1) 일반적인 웹 어플리케이션 구조**

- 컨트롤러 : 웹 MVC 컨트롤러
- 서비스 : 핵심 비지니스 로직 구현
- 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인 : 비지니스 도메인 객체

**2) 클래스 의존 관계**

<p align="center"><img src="https://user-images.githubusercontent.com/68148196/196385593-6421bc16-ae0e-4c6f-8fa8-1eabbecc79fb.png" width="500" height="280"/></p>

- 데이터 저장소가 선정되지 않아, 인터페이스로 구현 클래스를 변경할 수 있도록 설계

<br/>

#### ✏ 회원 서비스 및 리포지토리 개발

**1) MemberRepository(I)**

- 회원 저장 / 아이디로 조회 / 이름으로 조회 / 전체 조회

**2) MemoryMemberRepository**

- Map 형식 임의의 저장소 - DB 역할
- MemberRepository 인터페이스를 구현
- 해당 리포지토리는 동시성 문제가 고려되어 있지 않음. 실무에서 ConcurrentHashMap, AtomicLong 사용 고려

**3) MemberService**

- 중복 회원 검증과 같은 비지니스 로직을 처리
- repository의 메서드는 save, find처럼 db 쿼리에 가깝도록 네이밍하고, service의 메서드는 실제 웹에서 작동하는 역할에 가깝도록 네이밍한다.

**4) 회원 서비스와 리포지토리의 DI**

```java
/* 기존 코드 */
public class MemberService {
	private final MemberRepository memberRepository = new MemoryMemberRepository();
}

/* 회원 서비스 코드를 DI가 가능하도록 변경 */
/* SpringConfig 파일에서 @bean을 등록하거나 @Repository를 작성하는 방식으로 실제 사용할 Repository를 추후에 지정할 수 있다. */
public class MemberService {
	private final MemberRepository memberRepository;
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
		...
}
```

- **DI - Dependency Injection 적용**
    
    **:** 외부에서 클래스 간 의존성을 결정하는 것. 별도의 설정 파일로 의존성을 주입하면 가독성과 유지보수 하기 좋아진다.
    
<br/>

#### ✏ 회원 서비스 및 리포지토리 테스트 케이스 작성

- JUnit 프레임워크 사용
- 테스트는 연속적으로 반복하여 이루어지므로, 각 테스트가 독립적으로 수행하도록 코드를 구성해야 한다.
    
    테스트 간의 의존 관계가 없도록 한다.
    
- @AfterEach로 각 테스트 수행 후 처리할 함수를 구성한다. 이 TC의 경우, 메모리 DB에 저장된 데이터가 남아있으면 안되므로 테스트 수행 후 메모리 DB를 비운다.
- @BeforeEach 는 각 테스트 실행 전에 호출된다.  테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고 의존 관계도 새로 맺어준다.

<br/>
