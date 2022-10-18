
# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## ****📝 회원 관리 예제 -**** 웹 MVC ****개발****

### ✏ 회원 웹 구성

**1) 홈 화면 추가** 

- **HomeController**
    - **home()** : 기본 시작 페이지 호출

**2) 등록** 

- **MemberController**
    - **createForm()** : 회원 등록 폼 페이지 호출
    - **create()** : 회원 등록 후 기본 페이지로 redirect
- **createForm.html** : 회원 등록 폼 페이지
- **MemberForm** : 웹 등록 화면에서 데이터를 전달 받을 폼 객체

**3) 조회**

- **MemberController**
    - **list** : 회원 리스트 조회 후 model attribute로 설정하여 member/memberList로 전달
- **memberList.html** : 회원 목록 페이지
