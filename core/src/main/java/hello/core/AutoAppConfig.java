package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)) //자동으로 스프링 빈으로 등록//자동 스캔 예외 대상 설정 (앞서 한 예시들과의 충돌을 피하기 위해)
public class AutoAppConfig {
}
