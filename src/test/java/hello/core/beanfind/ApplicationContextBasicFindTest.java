package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());

        // memberService가 MemberServiceImpl에 대한 인스턴스라면 성공임.
//        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByType2() {
        MemberServiceImpl memberService = ac.getBean("memberService" , MemberServiceImpl.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

        /*
        @Bean
        public MemberService memberService() {return new MemberServiceImpl(getRepository());}
        스프링 빈에 등록된 이 인스턴스 타입(new MemberServiceImpl)을 보고 결정하기 때문에 인터페이스인 MemberService가 아니라,
        실제 구현체인 MemberServiceImpl로 적어줘도 됨.
        물론 구체적으로 적는건 안좋음. 왜냐하면 항상 역할과 구현을 구분해야한다. 역할에만 의존해야지, 구현에 의존하면 안됨.
         */
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
//         ac.getBean("xxxxx", MemberService.class);
//        MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
        // 오른쪽에있는 로직에서 무조건 왼쪽에있는 예외가 터져야 테스트가 성공함.
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class));
    }
}
