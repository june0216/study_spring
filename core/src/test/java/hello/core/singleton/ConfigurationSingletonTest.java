package hello.core.singleton;

import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.AppConfig;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

	@Test
	void configurationTest()
	{
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

		//Impl에 테스트용으로 만들어놓은 get을 사용하기 위해서 구체 타입을 가져온다.
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
		MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();

		//3개 다 같은 같을 참조한다.
		System.out.println("MemberService -> memberRepository = " + memberRepository1);
		System.out.println("OrderService -> memberRepository = " + memberRepository2);
		System.out.println("memberRepository = " + memberRepository);

		assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
		assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);


	}

	@Test
	void configurationDeep() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		//AppConfig도 스프링 빈으로 등록된다.
		AppConfig bean = ac.getBean(AppConfig.class);

		System.out.println("bean.getClass() = " + bean.getClass());//클래스 정보 출력 = class hello.core.order.AppConfig$$EnhancerBySpringCGLIB$$cfee49bc
	}
}
