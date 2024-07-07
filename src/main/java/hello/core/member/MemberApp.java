package hello.core.member;

import hello.core.AppConfig;

// ctrl + alt + v
public class MemberApp {

    /*
    기존에는 밑에 코드처럼 MemberApp에서 직접 멤버서비스 객체도 생성하고 그랬는데 이젠 그게 아니라,
    이젠 AppConfig를 이용해서 개발할거임.
     */

    /*
    이렇게 MemberApp같은 경우는 개발자가 직접 제어함. 뭐 new해가지고 돌리고
    이런거는 개발자가 직접 순차적으로 돌리는거임. 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면
    그것은 프레임워크가 아니라 라이브러리다.
    예를 들어서 우리가 뭐 자바 객체를 xml로 바꾸거나, json으로 바꾸는게 있음.
    그럼 내가 그 라이브러리를 불러다가 직접 호출함. 이런거는 라이브러리라고 보면 된다.
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
