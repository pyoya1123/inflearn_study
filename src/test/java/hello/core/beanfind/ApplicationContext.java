package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// JUNIT5 부터는 pulbic 안써도됨
class ApplicationContext {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] strings = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : strings) {
            Object bean = ac.getBean(beanDefinitionName); // 타입을 지정안해서 모름. 그래서 Object로 꺼내짐
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] strings = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : strings) {
            // beanDefinition 이란게 있음. 빈에 대한 어떤 정보들임. 빈 하나하나에 대한 메타데이터 정보임.
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // getRole이란게 있음. 이게 뭐냐면, 이 role이 세개가 있음. 하나는 안쓰고,
            // 밑에 코드는 스프링 내부에서 뭔갈 하기 위해 등록한 빈들이 아니라, 내가 애플리케이션 개발을 하기 위해서 등록한 빈들이라고 생각하면 됨.
            // 아니면 외부 라이브러리들.
            // Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
            // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName); // 타입을 지정안해서 모름. 그래서 Object로 꺼내짐
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }
}
