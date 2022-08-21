package hello.core.beanfind;

import hello.core.discount.discount.DiscountPolicy;
import hello.core.discount.discount.FixDiscountPolicy;
import hello.core.discount.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

	@Test
	@DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다. ")
	void findBeanByParentTypeDuplicate()
	{
		assertThrows(NoUniqueBeanDefinitionException.class,
				()-> ac.getBean(DiscountPolicy.class));
	}

	@Test
	@DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다. ") //나중에 쓸 일이 있음
	void findBeanByParentTypeBeanName()
	{
		DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
		/*assertThrows(NoUniqueBeanDefinitionException.class,
				()-> ac.getBean(DiscountPolicy.class));*/
		assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
	}
	@Test
	@DisplayName("특정 하위 타입으로 조회")
	void findBeanBySubType()
	{
		RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class); //구체적인 타입을 지정
		assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
	}

	@Test
	@DisplayName("부모 타입으로 모두 조회하기")
	void findAllBeanByParentType()
	{
		Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
		assertThat(beansOfType.size()).isEqualTo(2);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + "value = " + beansOfType.get(key));

		}
	}

	@Test
	@DisplayName("부모타입으로 모두 조회하기  - Object")
	void findAllBeansObjectType()
	{
		Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + "value = " + beansOfType.get(key));
		}// 자바 객체는 모든 게 Object 타입이므로 스프링의 모든 빈들이 다 출력된다.
	}


	@Configuration
	public class TestConfig
	{
		@Bean
		public DiscountPolicy rateDiscountPolicy()
		{
			return new RateDiscountPolicy();
		}

		/*여기서 다음과 같이 지정해도 된다. 하지만 위와 같이 하는 이유는 역할과 구현을 쪼갰는데 이를 파악하기 위해서이다.
		@Bean
		public DiscountPolicy rateDiscountPolicy()
		{
			return new RateDiscountPolicy();
		}*/

		@Bean
		public DiscountPolicy fixDiscountPolicy()
		{
			return new FixDiscountPolicy();
		}

	}

}
