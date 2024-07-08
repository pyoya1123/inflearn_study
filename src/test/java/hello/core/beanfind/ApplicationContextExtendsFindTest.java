package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/*
가끔 순수한 자바 애플리케이션에서 스프링 컨테이너를 사용할 때가 있는데 이럴 때
이런 빈 조회를 사용함. 보통은 잘 사용 안함.
 */

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회") // 이런 방법도 있지만, 물론 안좋은 방법임.
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
    
    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
            // 참고로 출력하는건 공부하는 용도로 하는거지, 실제 테케 용도로 짤 때는 출력물을 만들면 안됨.
            // 왜냐하면 어쩌다가 진짜 남길 순 있긴 해도, 시스템이 나중에는 자동 통과 실패를 시스템이 정하는데,
            // 이거를 눈으로 보고 있을 순 없음. 테스트를 통과 안한다고만 하면 되고, print 이런건 다 빼는게 좋음.
            // 물론 테스트 자체는 디버깅 하고싶을 떄도 있으니까 그때는 남겨도됨.
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        /*
        스프링이 내부적으로 쓰는 빈들까지 다 튀어나옴.
        스프링 안에는 여러가지 빈들이 등록이 되어있는데 얘네까지 다 딸려서 나옴.
        왜냐하면 자바 객체는 모든게 Object 타입이라, 스프링 빈에 등록된 모든 객체가 다 튀어나옴.
         */
    }

    @Configuration
    static class TestConfig {

        /*
        밑에 코드들에서 메소드 이름을 DiscountPolicy로 통일을 했음.
        근데 RateDiscountPolicy 처럼 이름을 지어도 똑같은데, 왜 그러지 않냐면,
        개발하거나 설계를 할 때, 역할과 구현을 항상 쪼개자고 했음. 그거임.
        DiscountPolicy를 보고 아 얘는 DiscountPolicy 관련된 애구나.
        사실 RateDiscountPolicy 처럼 구체적인걸로 해도 되지만, 이렇게 해두는게 좋음.
        그래야 다른 데에서 인젝션할 때도, 그니까 의존관계 주입할 때도, DiscountPolicy를 의존하고 있을테니까
        DiscountPolicy라고 적어놔야 얘만 보고 판단할 수 있는거임.
         */
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
