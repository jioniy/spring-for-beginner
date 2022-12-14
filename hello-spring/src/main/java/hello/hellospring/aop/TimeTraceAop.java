package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")//Targeting - 적용 대상 지정
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        System.out.println("START: "+joinPoint.toString());

        try{
            return joinPoint.proceed();//다음 메서드 진행
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
