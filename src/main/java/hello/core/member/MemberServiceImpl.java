package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 얘가 가입을 하고, 회원을 찾으려면 뭐가 필요하냐? 우리가 앞에서 만들었던 멤버 레포지토리 인터페이스가 필요함.
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 인터페이스는 적었는데, 인터페이스만 가지고 있으면 nullPointException이 날거임. 만약에 뭐
    // memberRepository.save(member)을 호출해도 널이면 터질거임.
    // 그래서 구현 객체를 선택해줘야함.

    @Override
    public void join(Member member) {
        memberRepository.save(member);
        // join에서 save를 호출하면, 다형성에 의해서 MemberRepository 인터페이스가 아니라,
        // MemoryMemberRepository에 있는 save가 호출이 될거임. 오버라이드한.
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
