package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.member.Member;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        // 생성자를 통해서 MemberRepository에 뭐가 들어갈지 정하는거임.
        // 이렇게되면 이제 OrderServiceImpl은 MemberRepository라는 인터페이스, 즉 추상화에만 의존하는거임.
        // DIP를 지키는거임.
        // 이제 구현체는 전혀 모르는거임. 이거를 누가 해주냐? AppConfig 즉, 외부에서 생성자를 통해 넣어주는거임.
        this.discountPolicy = discountPolicy;
    }
    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // -> 위에 코드가 바로 DIP 원칙에 어긋난다는 거임.
    // 클라이언트 OrderServiceImpl이 DiscountPolicy 인터페이스 뿐만 아니라, FixDiscountPolicy인 구체 클래스도
    // 함께 의존하고 있다는 거임.

    /*
    우리는 역할과 구현을 충실하게 분리하긴 했음.
    -> discountPolicy에 대해서 역할과 그리고 구현들 (FixDiscountPolicy, RateDiscountPolicy)로 잘 분리했음.
    그다음에 다형성 활용하고, 인터페이스와 구현 객체를 분리 했음.
    근데 OCP, DIP 같은 객체지향 설계 원칙을 충실히 준수하지 못했음.
    
    그래서 정책을 FixDicountPolicy에서 RateDiscountPolicy로 변경하는 순간 OrderSerivceImpl의 소스 코드도 함께 
    변경 해야한다는 거임 -> OCP 위반
     */

//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 개선점
//    private DiscountPolicy discountPolicy;
    // 이렇게만 선언하면 인터페이스에만 의존하게 됨.
    // 근데 실행을 하면 당연히 초기화가 안되어있으니까 null값이므로 NullPointException 뜰거임.
    // 일단 이렇게하면 DIP는 지키긴함.

    // 해결 방안
    // 이 문제를 해결하려면 누군가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해줘야함.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
