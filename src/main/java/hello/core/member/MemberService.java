package hello.core.member;

// 얘는 회원 가입, 회원 조회 두가지 기능이 있음
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
