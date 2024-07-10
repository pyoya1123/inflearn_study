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

    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    /*
    지금 내용은 수정 주입 내용임.
    필드를 수정하려면 관례상 set 함수를 사용함. 자바 빈 프로퍼티 규약임.
    OrderServiceImpl을 스프링 컨테이너에 딱 등록을 함.
    그다음에 어떻게 하냐?
    스프링 컨테이너는 크게 두가지 라이프사이클이 있음.
    첫번쨰는 뭐냐면, 스프링 빈을 등록하는거임. 스프링 빈을 다 등록하는거임.
    그다음에 뭘하냐? 연관 관계를 자동으로 주입한다.
    바로 이 Autowired 어노테이션이 붙은 애들을 자동으로 주입함.
    처음에 스프링이 빈을 생성하는 단계랑 의존관계 주입하는 단계가 나눠져있다고 했었음.
     */


    /*
    생성자가 하나 일 땐 Autowired를 생략해도 알아서 해줌.
    생성자가 두개 일 땐 Autowired 지정하기.
     */

    /*
    생성자 주입은, 일단 이 파일은 자바 코드임. 스프링이 뭐 용뺴는 재주가 있는건 아님.
    그래서 OrderServiceImpl 객체를 스프링이 생성해서 빈에 등록을 해야함.
    그럼 뭘 호출해야하냐? 스프링도 결국 new OrderServiceImpl 이렇게 해서 호출해야함.
    이러면 결국 생성자가 필요한거임. 그럼 스프링도 결국 스프링 컨테이너에서 밑에 memberRepository와 discountPolicy 두 개, 아 이거
    Autowired네 하고 스프링 컨테이너에서 스프링 빈 등록된거 찾아와가지고 new OrderServiceImpl(memberRepository, discountPolicy)
    이렇게해서 얘를 생성함.
    그래서 생성자는 어쩔 수 없이 스프링 라이프사이클 그 빈 등록할 때 자동 주입이 일어나는거임.
    그리고 수정자 주입은 의존관계 주입, 2번째 단게에서 일어나는거임.
    순서를 보면, 생성자 먼저 호출되고, 위에 set 함수들 차레로 호출되는거임.

    결과적으로 밑에 생성자 호출하는 코드는 필요가 없어지긴함. 위에 set함수들로 값 대입하니까.
     */

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
