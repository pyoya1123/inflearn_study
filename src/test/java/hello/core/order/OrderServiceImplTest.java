package hello.core.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    /*
    수정자 주입으로 OrderServiceImpl을 구현했을 땐 nullPointException이 남.
    내가 아무리 createOrder 함수를 사용하고 싶더라도, createOrder 까보면 memberRepository랑 discountPolicy 사용하는 코드 있음.
    그러면 가짜 메모리 멤버 레포지토리라도 만들어서 넣어줘야하는거임.
    임의의 더미라도. discountPolicy도 마찬가지임. 근데 여기선 누락을 해버린거임.
    왜 누락이 됐냐?
    왜냐하면 내가 테스트를 짜는 입장에서는 OrderServiceImpl에 의존관계가 뭐가 들어가더라?
    눈에 안보이는거임. 코드를 까봐야 아는거임. 그래서 테스트를 짤 때 안보여서 이런 실수를 하는거임.
     */
    @Test
    void createOrder() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.createOrder(1L, "itemA", 10000);
    }
}