package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 나의 애플리케이션 전체를 설정하고 구성한다.
@Configuration
public class AppConfig {


    /*
    싱글톤 이라고 했는데, 이상한 점이 있음.
    만약에 스프링 빈이 이 멤버서비스를 생성할 때 호출을 할 텐데,
    그러면 어떻게 되냐? new MemberServiceImpl을 호출하면서 memberRepository()를 호출할거임.
    자바 코드니까 이 메소드가 그냥 호출이 될거임.
    그러면은 new MemoryMemberRepository가 생성이 될거임.

    @Bean memberService -> new MemoryMemberRepository()를 한 번 호출함.
    @Bean orderService -> new MemoryMemberRepository()를 한 번 호출함.
    이러면 싱글톤이 깨지지 않냐? 라고 생각하는게 정상임.
    이런 생각이 들면 일단 테스트 코드로 돌려보면 됨.
     */
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
