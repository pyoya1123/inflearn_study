package hello.core.member;

import hello.core.AppConfig;

// ctrl + alt + v
public class MemberApp {

    /*
    기존에는 밑에 코드처럼 MemberApp에서 직접 멤버서비스 객체도 생성하고 그랬는데 이젠 그게 아니라,
    이젠 AppConfig를 이용해서 개발할거임.
     */

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        // 밑에 memberService에는 멤버서비스impl이 들어가있을거임.
        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
