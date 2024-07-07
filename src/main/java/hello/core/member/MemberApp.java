package hello.core.member;

import hello.core.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// ctrl + alt + v
public class MemberApp {

    /*
    기존에는 밑에 코드처럼 MemberApp에서 직접 멤버서비스 객체도 생성하고 그랬는데 이젠 그게 아니라,
    이젠 AppConfig를 이용해서 개발할거임.
     */

    /*
    이렇게 MemberApp같은 경우는 개발자가 직접 제어함. 뭐 new해가지고 돌리고
    이런거는 개발자가 직접 순차적으로 돌리는거임. 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면
    그것은 프레임워크가 아니라 라이브러리다.
    예를 들어서 우리가 뭐 자바 객체를 xml로 바꾸거나, json으로 바꾸는게 있음.
    그럼 내가 그 라이브러리를 불러다가 직접 호출함. 이런거는 라이브러리라고 보면 된다.
     */

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
        // 밑에 memberService에는 멤버서비스impl이 들어가있을거임.
//        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();

        // 스프링은 모든게 ApplicationContext라는걸로 시작함.
        // 이게 스프링 컨테이너라고 보면 됨.
        // 얘가 모든걸 관리해줌. 우리 객체들을. 아까 AppConfig에서 Bean으로 등록해준 애들을
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 위에서 AnnotationConfig로 시작하는 ApplicationContext 객체를 생성해줬는데,
        // 이게 뭐냐면, AppConfig에서 annotation 기반으로 Configure를 하고 있는 애들을 의미함.
        // 위에처럼 파라미터로 AppConfig.class를 넣게되면, AppConfig에 있는 환경설정 정보를 가지고 스프링이
        // AppConfig 안에 있는 @Bean으로 등록된 애들을 다 스프링 컨테이너에다가 객체 생성한 것들을 집어넣어서 관리해줌.

        /*
        @Bean으로 등록하게되면 기본적으로 메소드 이름으로 등록이 됨. 그래서 밑에 코드는 무슨 의미냐면,
        memberService라는 이름을 가진 객체를 찾을거야임. 두번째 파라미터는 타입임. 이걸 넣어줘야 반환타입이 딱 맞음
        그니까 memberService라는 이름을 가진 객체를 찾을건데, 타입은 MemberService야.
         */
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
