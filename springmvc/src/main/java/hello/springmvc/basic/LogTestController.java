package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //롬복이 지원해주는 어노테이션
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass()); //내 클래스 지정

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        //로그 레벨 상태 지정
        log.trace("trace my log = "
            + name); //+연산을 사용하게 되면 사용하지 않는 로그 레벨에 문자열 +연산을 다하고 전달하기 때문에 {} 안에 사용하는것이 좋다

        log.trace("trace log={}", name);

        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
