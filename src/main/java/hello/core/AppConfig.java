package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 나의 애플리케이션 전체를 설정하고 구성한다.
public class AppConfig {

    public MemberService memberService() {
        // 이제 new instance가 생성자를 통해 들어간다 해서, 생성자 주입이라고 함.
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

    /*
    이전에는 객체를 생성하고, 이 객체에다가 어떤게 들어가야돼, 할당 해야돼 이런거를 우리가 멤버서비스 클래스 안에서 직접 했었음.
    ex) private final MemberRepository memberRepository = new MemoryMemberRepository();
    이런거는 MemberServiceImpl이 해줬었음.
    위와 같은 코드는 배우가 직접 담당 배우를 섭외하는거랑 똑같은거임.
    이걸 어캐하냐? 이런거를 AppConfig에서 다 하는거임. 애플리케이션 환경 구성같은거를.
    먼저 멤버서비스를 여기서 만들거임.
    멤버서비스임플은 어떻게 되냐면, 메모리멤버 레포지토리를 쓸거임. 그러면 일단 MemberServiceImpl 클래스에서 MemberServiceRepository 인터페이스에
    할당했던 객체는 지워버리는거임.
    private final MemberRepository memberRepository; 이런식으로
    그리고 생성자를 만들어줌.

     */
}
