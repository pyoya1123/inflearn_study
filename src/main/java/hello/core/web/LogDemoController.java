package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor // 생성자에 autowired가 자동으로 들어가면서 자동 주입 시켜주는 애임.
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider; // 이렇게하면 myLogger을 주입 받는게 아니라,
    // myLogger을 찾을 수 있는, dependency lookup 할 수 있는 애가 주입이 됨.
    // 그래서 얘는 주입 시점에 주입 받을 수 있음.

    /*
    myLogger의 생명주기에 대해 말하자면, HttpRequest가 들어와서 나갈 때까지 이거를 쓸 수 있는데,
    그 사이에 스프링 컨테이너한테 달라고 해야하는데, 지금 HttpRequest 요청이 없는거임.
    당연히 스프링 컨테이너가 뜨는 시점에 HttpRequest 요청이 없음. 그러니까 없는데, 생존 주기 범위가 아닌데
    스프링 컨테이너한테 달라고 하니까 오류가 뜨는거임.
    -> 스코프 request가 활성화 되지 않았다.
    이럴 땐 어떻게 하냐? 우리가 직전에 배운 provider을 쓰면 되는거임.
     */

    /*
    HttpServletRequest란?
    자바에서 제공하는 표준 서블릿 규약이 있는데,
    이거에 의한 HttpRequest 정보를 받을 수 있음. 고객 요청 정보를 받을 수 있는거임.
     */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject(); // getObject를 하는 순간에 MyLogger가 만들어짐.
        // 만들어지면서 init을 호출하고, 호출하는 과정에서 uuid를 HttpRequest랑 연결을 시키는거임.
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";

    }
}
