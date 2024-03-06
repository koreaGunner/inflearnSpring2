package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//현재 AppConfig에 @Configuration이 있으므로 ComponentScan의 대상이다
//그러므로 Configuration은 ComponentScaan 대상에서 제외시켜야함(예제 코드를 지우기않기위해)
@ComponentScan(
        //basePackages = "hello.core.member",
        //basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
