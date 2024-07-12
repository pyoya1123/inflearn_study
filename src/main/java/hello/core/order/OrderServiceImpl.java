package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    /*
    생성자 주입의 장점으로는 객체에 final 키워드를 넣을 수 있다는거임.
    여기서 final 키는 뭐냐?
    딱 한번 생성할 때 정해지면 안바뀐다는거임.
    얘의 장점은, 생성자 에서만 값을 세팅할 수 있다. (또는 = new ~~ 해서 바로 값을 대입해주거나)
    나머지는 이 final이 붙은 애들의 값을 바꿀 수 없음.
    개발자가 실수로 생성자를 만드는데, 코드를 누락할 수도 있는데, 이런거는 오류로 바로 잡아줄 수 있음.
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    // 일반 메소드 주입
    // 사실 수정자 주입도 이거랑 비슷함. 그냥 메소드 위에 Autowired가 있는거임.
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
