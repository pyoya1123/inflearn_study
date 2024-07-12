package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {


    @Test
    void createOrder() {
        /*
        생성자 주입을 했을 땐 밑에 new OrderServiceImpl에 파라미터로 뭘 안넣어주면 컴파일 오류가 뜸. 여기서 실수를 바로 잡을 수 있는거임.
        더미 데이터든, 뭐 임의의 어떤 메모리멤버레포지토리 든지 new로해서 넣어줘야하는 것을 알 수 있는거임.
         */

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}