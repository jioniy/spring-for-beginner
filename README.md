# spring-for-beginner
스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

<br/>

## 📝 스프링 웹 개발 기초

<br/>

#### ✏ Controller의 Request/Reponse 처리 방식

**1) 화면 전환** 

- Controller에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리한다. resources:templates/{viewName}.html
    
    ```java
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }
    
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }
    ```
    <p align="center"><img src="https://user-images.githubusercontent.com/68148196/196641349-746d3964-3f39-4b68-9cc2-5446b850a62a.JPG" width="500" height="280"/></p>

<br/>

**2) API**

- **@ResponseBody 사용**
    
    : 반환 값을 처리할 때 viewResolver 대신 HttpMessageConverter가 동작한다.
    
    - 문자 - StringHttpMessageConverter
    - 객체 - MappingJackson2HttpMessageConverter
    
    <p align="center"><img src="https://user-images.githubusercontent.com/68148196/196640660-98a19141-2002-4ad4-af06-f193fc142071.png" width="500" height="280"/></p>

<br/>
  

- **문자열 반환**
    
    : **StringHttpMessageConverter**를 사용하여 **HTTP body에 해당 문자열을 담아 직접 반환**한다.
    
    ```java
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }
    ```
    
    
- **객체 반환**
    
    : **MappingJackson2HttpMessageConverter**를 사용하여 **HTTP body에 해당 객체를 JSON 형식으로 담아 반환**한다.
    
    : **{"name":"hello"}**
    
    ```java
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    /*Hello 객체*/
    static class Hello{
        private String name;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }
    ```
 
<br/>
