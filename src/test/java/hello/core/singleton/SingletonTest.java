package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 사실 appConfig.memberService()를 통해  더 들어가보면 MemberServiceImpl만 생성하는게 아니라,
        // MemberServiceImpl에서 생성자에 getRepository() 를 호출하는데 이 과정에서 return new MemoryMemberRepository();
        // 가 호출됨. 즉, 이렇게 두 개 객체 만들면 총 네 개가 만들어지는거임.

        // 2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인.
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

        /*
        스프링은 대부분 웹어플리케이션을 주로 만듬.
        웹 어플리케이션의 특징은 고객의 요청이 많음.
        이러면 요청할 때 마다 객체가 생성되어야함. 효율적이지 않음.
         */
    }
}
