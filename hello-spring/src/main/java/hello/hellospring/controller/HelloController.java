package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    /**
     * Controller에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아서 리턴한다.
    * */
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }

    /**
     * MVC - Controller
    * */
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    /**
     * API
    * */

    /**
     * @RequestBody
     * viewResolver를 사용하지 않음
     * HTTP BODY 에 문자 내용을 직접 반환
    * */
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }

    /**
     * @ResponseBody를 사용하고, 객체를 반환하면
     * 객체가 JSON 형태로 변환됨 - {"name":"hello"}
     * viewResolver 대신에 HttpMessageConverter가 동작
     * 문자 - StringHttpMessageConverter
     * 객체 - MappingJackson2HttpMessageConverter
    * */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }

}
