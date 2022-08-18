package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
	void findBeanByTypeDuplicate()
	{
		Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
				() -> ac.getBean(MemberRepository.class));

	}

	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름으로 지정하면 된다. ")
	void findBeanByName()
	{
		MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
		//다음과 같이 memberRepository1로 이름으로 지정한다.
		assertThat(memberRepository).isInstanceOf(MemberRepository.class);

	}

	@Test
	@DisplayName("특정 타입을 모두 조회하기 ")
	void findAllBeanByType()
	{
		Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + "value = " + beansOfType.get(key));
		}
		System.out.println("beansOfType = " + beansOfType);
		//검증
		assertThat(beansOfType.size()).isEqualTo(2);
	}


	@Configuration //테스트용 Config를 만든 것 , static은 해당 클래스 내에서만 사용한다는 의미
	static class SameBeanConfig {

		@Bean
		public MemberRepository memberRepository() {
			return new MemoryMemberRepository();
		}

		@Bean
		public MemberRepository memberRepository2()//클래스 타입이 같은 빈을 생성
		{
			return new MemoryMemberRepository();
		}

	}
}
