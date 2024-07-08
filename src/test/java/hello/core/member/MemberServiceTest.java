package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    
    MemberService memberService;

    // test 실행 전에 무조건 실행되는 거임
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }


    /*
    나는 밑에 join 로직을 개발만 한거임.
    이거에 대한 실행하고, 제어권은 누가 가져가냐면, JUNIT이나 test 프레임워크가 대신 실행해주는거임.
    그냥 실행하는게 아니라, 자신만의 라이프 사이클이 또 있음.
    BeforeEach를 먼저 실행하고, 그 다음에 test를 실행한다 라는 이 라이프 사이클 속에서
    프레임워크가 이만큼 있는데, 내 것만 콜백식으로 불러지는거임.
    이렇게 내가 뭔가 제어권을 가지고 있는게 아니라,
    나는 그 프레임 워크 안에서 필요한 부분만 딱 개발하면
    그게 프레임워크이 알아서 적절한 타이밍에 호출이 되는거,
    이렇게 호출하는 제어권을 넘기는 것을 제어의 역전이라고 함.
     */
    @Test
    void join() {
        // given ~~가 주어졌을 때
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when ~~이렇게 했을 때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then  ~~이렇게 된다
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
