package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        // AnnotationConfigAppl~~에서 파라미터로 넘긴 AppConfig.class는 스프링 빈으로 등록이 됨.
        // 그리고 AppConfig 안에 있는 @bean 어노테이션이 붙은 애들도 스프링 빈으로 등록이 됨.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
    /*
    AppConfig가 있는데, 이걸 가지고 CGLIB라는 바이트코드 조작 라이브러리를 가지고, AppConfig@CGLIB를 상속받아서
    다른 클래스를 만드는거임. 그리고 스프링이 어떻게 하냐면, 내 거인 AppConfig 말고 AppConfig@CGLIB라는 조작한 클래스를 
    스프링 빈으로 등록해버리는거임.
    그래서 분명히 이름은 AppConfig인데, 인스턴스 객체가 AppConfig@CGLIB 인 애가 들어가있는거임.
    그래서 내가 등록한 애는 사라지고 @CGLIB 붙은 애가 등록되어있는거임.
    내가 만든 AppConfig가 아닌, 임의의 다른 클래스가 싱글톤을 보장해줌.

    *AppConfig@CGLIB 예상 코드 설명 내용임.
    처음에 memberRepository가 호출되면, 스프링 컨테이너에 등록이 안되어있으니까, else를 타서,
    기존에 있던 로직을 호출할거임(AppCofing.java 파일에 있는 memoryRepository 함수).
    그런데, 두번째 부터는, memberRepository를 호출하게 되면, 오버라이드 된 애가 호출되는거임.
    우리는 AppConfig를 호출하러 갔지만, 실제로는 AppConfig@CGLIB고, 얘가 오버라이드된 메소드가 실행이 되는거임.
    이러면 어떻게 되냐? memoryRepository가 이미 스프링 컨테이너에 있기 떄문에 컨테이너에서 찾아서 반환하는거임.

     */
}
