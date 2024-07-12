package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /*
    Autowired라는 기능을 생성자에 붙여주면 스프링이 어떻게 하냐?
    MemberRepository 타입에 맞는 애를 찾아와서 의존관계 주입을 auto, wired 자동으로 연결해서 주입해줌.
    그래서 ComponentScan을 쓰면, 이 Autowired를 쓰게 됨.
    빈이 자동으로 등록이 되는데, 의존 관계을 설정할 수 있는 방법이 없음. AppConfig를 설정하자니 이거는 충돌나니까 배제해야하고,
    장소가 없는거임. 그래서 Autowired를 쓰게 되면 스프링이 MemberServiceImpl을 스프링 빈으로 등록해주면서,
    MemberRepository 타입에 맞는 애를 갖고와서 주입해준다. (여기서는 MemoryMemberRepository일거임.)
     */
    @Autowired // ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
