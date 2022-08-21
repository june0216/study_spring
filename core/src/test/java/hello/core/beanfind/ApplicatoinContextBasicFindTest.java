package hello.core.beanfind;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ApplicatoinContextBasicFindTest {
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

	@Test
	@DisplayName("빈 이름으로 조회 ")
	void findBeanByName()
	{
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		/*System.out.println("memberService = " + memberService);
		System.out.println("memberService.getClass() = " + memberService.getClass());*/
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}

	@Test
	@DisplayName("이름없이 타입으로만 조회 ")
	void findBeanByType()
	{
		MemberService memberService = ac.getBean(MemberService.class);
		/*System.out.println("memberService = " + memberService);
		System.out.println("memberService.getClass() = " + memberService.getClass());*/
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}

	@Test
	@DisplayName("구체 타입으로 조회 ") //구체 타입으로 조회해도 되지만 되도록이면 추상화된 것으로 조회하는 것을 권장(왜냐하면 스프링은 역할에 의존하도록 구현하는 것을 권장)
	void findBeanByName2()
	{
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);//여기서 인터페이스 타입이 아니어도 된다. -> 스프링빈에 등록되어 있는 인스턴스타입으로 결정하기 때문에
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

	}

	@Test
	@DisplayName("빈 이름으로 조회 X") // 실패 테스트
	void findBeanByNameX()
	{
		//ac.getBean("xxxxx", MemberService.class); -> 빈이 없을 때
		//MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
		Assertions.assertThrows(NoSuchBeanDefinitionException.class,
				() -> ac.getBean("xxxxx", MemberService.class)); // 뒤의 로직을 실행했을 때 이 예외가 터져야 테스트가 통과한다
	}



}
