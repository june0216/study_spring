package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;

public class AppConfig {

	public MemberService memberService()
	{
		return new MemberServiceImpl(new MemoryMemberRepository()); //구현체를 밖에서 생성(생성자 안에 들어가서 주입됨 -> 생성자주입)
	}//MemberServiceImpl을 생성하면서 이때 사용할 구현체는 MemoryMemberRepository()라고 객체를 생성하면서 알려주는 것이다.


	public OrderService orderService()
	{
		return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
	}


}
