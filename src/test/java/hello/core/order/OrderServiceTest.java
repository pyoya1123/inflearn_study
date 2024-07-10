package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {



//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    // 추가 설명
    /*
    밑에 코드처럼 구현하고 실행하면 NullPointerException이 발생할거임. OrderServiceImpl 파일에서 createOrder 함수에서 발생함.
    왜냐하면 new해서 객체 생성하고 실행하는건 스프링에서 돌리는게 아님.
    그냥 순수하게 자바로 돌리는거임.
    결국 memberRepository와 discountPolity에 값을 대입하는 set함수를 각각 구현해줘야하는거임.
    이래서 필드 인젝션은 안씀.
    Autowired는 스프링 컨테이너가 관리하는걸 가져와야 적용이 되는거지, 내가 임의로 new해서 생성하는 애는
    당연히 Autowired가 적용이 안됨.
     */
    @Test
    void fieldInjectionTest() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.createOrder(1L, "itemA", 10000);
    }
}
