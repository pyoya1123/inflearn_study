package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /*
    어느 누구도 이 memberRepository를 "외부에서" 수정할 수 있는 방법이 없음.
    memberRepository는 애플리케이션 조립되는 딱 그 순간에만 딱 한 번 들어오고
    이 memberRepository의 인스턴스를 변경할 수 있는 방법은 이 안에 없음.
    왜냐하면 나의 의도가 뭐냐면, 나는 처음에 애플리케이션 그 Configuration으로 딱 스프링 컨테이너에 빌딩되면서 올라갈 때
    이 연관 관계의 그림을 다 만들고 끝내고싶은거임.
    예를 들어, 공연을 띄우기 전에 배우들을 다 정하고 난 끝내고싶은거임. 공연하는 중간에 배우들을 바꾸는 일이 나는 없는거임.
    그렇다고 하면,
     */

    /*
    MemberServiceImpl 보면, Component 스캔할 때 어? MemberServiceImpl 있네 하면서 스프링 빈에 등록이 될거임.
    등록이 될 때 생성자 호출할 텐데, 생성자를 보니까 어? Autowired 붙어있네?
    라고 하면 스프링 컨테이너에서 memberRepository 스프링 빈을 꺼냄.
    그리고 꺼낸 데에다가 딱 주입을 해주는거임.
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
