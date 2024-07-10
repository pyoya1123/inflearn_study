package hello.core.scan.Filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

//        ac.getBean("beanB", BeanB.class); // excludeComponent로 설정했으니까 컴포넌트 스캔 대상에서 빠지므로 no benn Named 오류뜸.
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }

    @Configuration
    // Annotation과 관련된 필터를 만드는거임.
    // type = FilterType.ANNOTATION은 default가 생략 가능
    @ComponentScan(includeFilters = @Filter(/*type = FilterType.ANNOTATION,*/ classes = MyIncludeComponent.class),
                   excludeFilters = @Filter(/*type = FilterType.ANNOTATION,*/ classes = MyExcludeComponent.class)
    )
    // 위처럼 구성하면 나만의 컴포넌트를 스캔할 수 있는 기능이 만들어짐.
    static class ComponentFilterAppConfig {

    }
}
