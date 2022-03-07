package hello.springmvc.basic.request;

import hello.springmvc.basic.requestmapping.HelloData;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody //RestController 와 같은 동작
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge
    ){
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
        @RequestParam String username,
        @RequestParam int age
    ){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
        @RequestParam(required = true) String username, //default가 true, 반드시 들어와야한다
        @RequestParam(required = false) Integer age){
        //null != "" //빈 문자는 null이 아니므로 통과한다.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
        @RequestParam(defaultValue = "guest") String username, //default 값이 있다면 required 가 필요가 없다
        @RequestParam(defaultValue = "-1") Integer age){
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
        @RequestParam Map<String, Object> paramMap){
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
        //@RequestParam String username, @RequestParam int age
        @ModelAttribute HelloData helloData
    ){
        /*
        HelloData helloData = new HelloData();
        helloData.setAge(age);
        helloData.setUsername(username);
         */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        //log.info("helloData={}", helloData); //ToString 호출, @Data 를 사용하면 자동으로 toString 사용
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(
        HelloData helloData
    ){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
