package hello.core;

import hello.core.discount.DiscountPolicy;
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
        return new MemberServiceImpl(getRepository());
    }

    private static MemoryMemberRepository getRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(getRepository(), discountPolicy());
    }

    // 디스카운트 할인 정책은 나는 Fix를 사용할거야. 라는 의미임.
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
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

    /*
    AppConfig refactoring의 장점
    메소드 이름을 다 보는 순간 역할이 다 드러남. 그리고 멤버 서비스에 대한 구현은 현재 나의 애플리케이션에서는
    멤버서비스는 멤버서비스impl을 쓸거야. 현재 나의 애플리케이션에서는 멤버 레포지토리에 대해서는 메모리멤버레포지토리로 할거야. 그니까
    메모리로 할거야. 만약에 나중에 DB로 바뀐다고 치자. 뭐 JDBC로 바뀐다고 치자. 그러면 어디만 바꾸면 되냐?
    저 두번째 메소드인 return new MemoryMemberRepository 코드만 바꾸면 되는거임.
    오더서비스에 대한 구체적인거는, 나는 오더서비스를 쓰는데, 현재 내 어플리케이션에 멤버레포지토리 쓰는거 갖고오고,
    현재 내 애플리케이션에서 결정한 discountPolicy 갖고올거야. 라고 하면 멤버 레포지토리는 메모리, 디스카운트폴리시는 fix
    이렇게 딱 들어오는거임.
    이렇게 해서 pdf에서 AppConfig 리팩터링의 "기대하는 그림"에서 이 설계에 대한 그림이 여기 구성정보에 그대로 드러나는거임.
    역할들이 나오고, 그 역할에 대한 구현이 어떻게 되는지 한눈에 들어오는거임.
    이렇게 리팩토링하면 되게 큰 장점이 있다.
     */
}
