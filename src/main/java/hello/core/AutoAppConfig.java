package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/*
컴포넌트 스캔이 뭐냐면, 스프링 빈을 쫙 끌고 와서 자동으로 스프링 빈으로 끌어올리는거임.
@Component 어노테이션이 붙은 클래스를 찾아서 다 자동으로 스프링 빈으로 등록해줌.
excludeFilters는 뭐냐면, 컴포넌트 스캔으로 쫙 뒤져가지고 스프링 빈으로 등록해주는데, 그 중에서 뺄거 지정하는거임.
밑에 classes = Configuration.class도 넣어줬음.
컴포넌트라는걸 찾는다고 했는데 밑에 Configuration을 빼는 이유가, AppConfig가 자동으로 등록되면 안됨.
얘는 수동으로 등록되는 케이스인데 얘 까지 스프링 빈으로 등록되어버리면 충돌이 날거임.
@Configuration 어노테이션을 까보면, @Component라는게 붙어있음. 그래서 @Configuration이 붙은 AppConfig도 컴포넌트 스캔의 자동 스캔 범위에 포함됨.
*/

@Configuration
@ComponentScan(
//        // member만 컴포넌트 스캔 범위에 허용함.
//        basePackages = "hello.core.member",
        // @Configuration 붙은 애는 뺀다는 의미임.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    // 기존의 AppConfig와는 다르게 @Bean 이 붙은 클래스가 없음.

    /*
    기존의 AppConfig는 memberService 코드를 통해 new MemberServiceImpl(memberRepository())를 통해 의존관계를 주입할 수 있었는데,
    지금 상황에서는 @Component 어노테이션만 붙여주면 그냥 스프링 빈으로만 등록되고 끝나버림.
    이러면 의존 관계 주입을 할 수 있는 방법이 없음.
    그래서 자동 의존 관계 주입이 필요하다. -> autowired
     */

    // MemoryMemberRepository 클래스에 가보면 @Component 어노테이션이 붙어있어서
    // 맨 앞글자를 소문자로하여 클래스 이름을 빈으로 등록함.
    // 밑에처럼 memoryMemberRepository라는 똑같은 이름으로 빈을 등록하게 되면 같은 이름의 빈이 두개 이상 존재하게 될거임.
    // 이러한 경우 수동 등록 빈이 우선순위를 가질거임.
    // 하지만 개발자가 의도해서 내는 경우는 거의 없고, 의도하지 않은 경우가 많기에 이런 애매한 버그는 잡기가 힘듬.
    // 그래서 스프링 부트로 돌리면 이러한 경우까지 다 오류로 띄워버림. defaultㄱ밧으로 오버라이딩=true로 함으로써
    @Bean(name="memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
