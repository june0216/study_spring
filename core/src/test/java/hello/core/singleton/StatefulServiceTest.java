package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

	@Test
	void statefulServiceSingleton()
	{
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);

		//ThreadA : A 사용자 10000원 주문
		int userAPrice = statefulService1.order("userA", 10000);
		//ThreadB : B 사용자 10000원 주문
		int userBPrice = statefulService2.order("userB", 20000);

		//ThreadA : 사용자A 주문 금액 조회
		//int price = statefulService1.getPrice();
		System.out.println("price = " + userAPrice); //20000원이 나온다. -> statefulService 1 이든, 2이든 다 같은 객체를 참조하고 있는 싱글톤이기 때문

		//Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);


	}

	static class TestConfig
	{

		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}

}