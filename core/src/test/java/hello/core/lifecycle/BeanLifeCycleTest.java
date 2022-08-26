package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

	@Test
	public void lifeCycleTest()
	{
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		//ConfigurableApplicationContext는  AnnotationConfigApplicationContext의 상위 것이다.
		NetworkClient client = ac.getBean(NetworkClient.class);
		ac.close(); //스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
	}

	@Configuration
	static class LifeCycleConfig
	{
		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient(); // 객체 생성 이후 값이 세팅이 되기 때문에
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient; //이렇게 리턴한 결과물이 스프링 빈에 적용이 된다.
		}
	}
}
