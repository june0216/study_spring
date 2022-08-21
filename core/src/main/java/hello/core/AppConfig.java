package hello.core;

import hello.core.discount.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //어플리케이션의 구성정보를 담당한다.
public class AppConfig {
	/*2번 호출되어 싱글톤이 깨지는 것처럼 보임
	@Bean memberService -> new MemoryMemberRepository()
	@Bean orderService -> new MemoryMemberRepository()*/


	//System.out.println을 예상 호출
	//call AppConfig.memberService
	//call AppConfig.memberRepository
	//call AppConfig.memberRepository
	//call AppConfig.orderService
	//call AppConfig.memberRepository

	//하지만 3번 호출한다(5번이 아니라 중복이 없는 3개가 호출이 되었다)
	//call AppConfig.memberService
	//call AppConfig.memberRepository
	//call AppConfig.orderService


	@Bean //스프링 컨테이너에 담긴다.
	public MemberService memberService()
	{
		System.out.println("call AppConfig.memberService");
		return new MemberServiceImpl(memberRepository()); //구현체를 밖에서 생성(생성자 안에 들어가서 주입됨 -> 생성자주입)
	}//MemberServiceImpl을 생성하면서 이때 사용할 구현체는 MemoryMemberRepository()라고 객체를 생성하면서 알려주는 것이다.
	@Bean
	public MemberRepository memberRepository() { //따로 함수를 빼면 더 역할이 잘 보인다.
		System.out.println("call AppConfig.memberRepository");
		return new MemoryMemberRepository();
	}

	@Bean
	public OrderService orderService()
	{
		System.out.println("call AppConfig.orderService");
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	@Bean
	public RateDiscountPolicy discountPolicy() {
		//return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}


}
