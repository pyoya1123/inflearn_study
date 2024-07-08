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
    밑에와 같이 @Bean을 이렇게 달아주면 얘네가 다 spring container에 등록이 됨.
     */
    @Bean
    public MemberService memberService() {
        // 이제 new instance가 생성자를 통해 들어간다 해서, 생성자 주입이라고 함.
        return new MemberServiceImpl(getRepository());
    }

    @Bean
    public static MemoryMemberRepository getRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(getRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
