package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        //destroyMethod 에는 추론 기능이 내재돼있다.
        //사용하기 싫으면 destroyMethod = "" 라고 하면된다
        //추론기능은 close, shutdown 라는 이름의 메서드를 자동으로 호출해준다.
        //@PostConstruct, @PreDestroy의 사용을 권장하나 코드를 고치지 못하는 외부 라이브러리에서는 사용하지 못함
        //그럴경우 @Bean을 사용해야한다
        @Bean/*(initMethod = "init", destroyMethod = "close")*/
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
