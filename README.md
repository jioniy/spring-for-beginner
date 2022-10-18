# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## ****📝**** AOP

### ✏ AOP가 필요한 상황

- 각 메소드의 호출 시간을 측정할 때 ex) 회원 가입, 회원 조회 시간
- 회원 가입, 회원 조회 시간을 측정하는 것은 **공통 관심 사항(cross-cutting concern) O** but **핵심 관심 사항(core concern) X**
- **각 함수에 시간 측정 로직을 추가하게 되면 핵심 비지니스 로직과 섞여서 유지보수 어려움**

### ✏ AOP 적용

- **AOP : Aspect Oriented Programming**
- **공통 관심 사항**과 **핵심 관심 사항**의 분리

```java
@Component
@Aspect
public class TimeTraceAop {
	@Around("execution(* hello.hellospring..*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		System.out.println("START: " + joinPoint.toString());
		try {
			return joinPoint.proceed();
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("END: " + joinPoint.toString()+ " " + timeMs + "ms");
		}
	}
}
```
<p align="center"><img src="https://user-images.githubusercontent.com/68148196/196410714-a5e19126-bc3e-4525-9f53-a984f774a343.png" width="500" height="290"/></p>
<br/>
