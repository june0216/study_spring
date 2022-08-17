package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //어플리케이션의 구성정보를 담당한다.
public class AppConfig {

	@Bean //스프링 컨테이너에 담긴다.
	public MemberService memberService()
	{
		return new MemberServiceImpl(memberRepository()); //구현체를 밖에서 생성(생성자 안에 들어가서 주입됨 -> 생성자주입)
	}//MemberServiceImpl을 생성하면서 이때 사용할 구현체는 MemoryMemberRepository()라고 객체를 생성하면서 알려주는 것이다.
	@Bean
	public MemberRepository memberRepository() { //따로 함수를 빼면 더 역할이 잘 보인다.
		return new MemoryMemberRepository();
	}

	@Bean
	public OrderService orderService()
	{
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	@Bean
	public RateDiscountPolicy discountPolicy() {
		//return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}


}
