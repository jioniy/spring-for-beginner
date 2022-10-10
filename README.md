# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## 📝 프로젝트 환경 설정

### ✏ 프로젝트 생성
**Maven**

- 필요한 library들을 네트워크를 통해 자동으로 다운 받는다.
- 프로젝트의 전체적인 life cycle을 관리하는 도구
- maven에서 미리 정의하고 있는 빌드 순서가 있으며, 이 순서를 **life cycle**이라고 한다.(Clean, Validate, Compile, Test, Package …)
- XML로 라이브러리를 정의하고 활용

**Gradle**

- 빌드 배포 도구
- 별도의 스크립트를 통하여 사용할 어플리케이션 버전, 라이브러리 등의 항목을 설정
- **라이브러리 관리**
    
    메이븐 레파지토리를 동일하게 사용할 수 있어서 설정된 서버를 통하여 라이브러리를 다운로드 받아 모두 동일한 의존성을 가진 환경을 수정할 수 있다. 자신이 추가한 라이브러리도 레파지토리 서버에 올릴 수 있다.
    
- **프로젝트 관리**
    
    모든 프로젝트가 일관된 디렉토리 구조를 가지고 빌드 프로세스를 유지하도록 도와준다.
    
- **단위 테스트 시 의존성 관리**
    
    junit 등을 사용하기 위해서 명시한다.
```java
repositories{
	mavenCentral() // dependencies library를 다운받는 경로 설정(이 코드는 maven 사이트)
}
dependencies{
	implementation '...'//해당 library를 사용하기 위한 모든 library를 가져온다.
}
```

<br/>

### ✏ 라이브러리
- **spring-boot-starter-web**
    
    ```java
    |- spring-boot-starter-tomcat : 톰캣(웹서버) → 웹 서버를 라이브러리로 내장하여 별도의 설치 과정과 설정이 필요없다.
    |- spring-webmvc : 스프링 웹 MVC
    ```
    
- **spring-boot-starter-thymeleaf** : 타임리프 템플릿 엔진(View)
- **spring-boot-starter**(공통): 스프링 부트 + 스프링 코어 + 로깅
    
    ```coffeescript
    |- spring-boot
    		|- spring-core
    |- spring-boot-starter-logging
    		|- logback, slf4j
    ```
    
- **spring-boot-starter-test**
    
    ```java
    |- junit : 테스트 프레임워크
    |- mockito : 목 라이브러리
    |- assertj : 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리 
    							ex) Assertions.assertThat()
    |- spring-test : 스프링 통합 테스트 지원
    ```
    
    실무에서는 error를 더 빨리 잡기 위해 System.out.println()보다 log를 더 많이 사용한다.

<br/>

### ✏ 동작 환경
- Controller에서 리턴 값으로 문자를 반환하면 viewResolver가 찾아서 리턴한다.
    - 이때 **요청** **url 매핑 우선 순위는 controller → view 순**이다. controller에 같은 이름의 매핑이 있을 시 그곳을 먼저 거친다.
    - 스프링부트 템플릿엔진 기본 viewName 매핑(resources:templates/{ViewName}.html)
<p align="center"><img src="https://user-images.githubusercontent.com/68148196/194913024-cd20b5fa-8ee3-4d1a-b4de-156287f9e002.png" width="500" height="280"/></p>


<br/>

### ✏ 빌드하고 실행하기
- 콘솔로 이동

```java
./gradlew build --> build/libs 밑에 jar 파일 생성
cd build/libs
java -jar hello-spring-0.0.1-SNAPSHOT.jar --> jar파일 실행
```
