package hello.core;

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
}
