package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
	MemberService memberService;
	OrderService orderService;

	@BeforeEach // 테스트 실행 전에 무조건 실행되는 것
	public void beforeEach()
	{
		AppConfig appConfig = new AppConfig();
		memberService = appConfig.memberService();
		orderService = appConfig.orderService();
	}

	@Test
	void createOrder()
	{
		Long memberId = 1L;
		Member member = new Member(memberId, "memberA", Grade.VIP);
		memberService.join(member);

		Order order = orderService.createOrder(memberId, "itemA", 10000);
		Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
	}


/*	@Test
	void fieldInjectionTest() {
		OrderServiceImpl orderService = new OrderServiceImpl(); //여기서 컨테이너에서 꺼내온 게 아니라 이렇게 생성한 것은 Autowired가 안먹는다.
		//orderService.createOrder(1L, "itemA", 10000); //null point 예외가 나온다.
		//외부에서 값을 넣을 수 없음 -> setter를 만들어야 한다.
		orderService.setMemberRepository(new MemoryMemberRepository());
		orderService.setDiscountPolicy(new FixDiscountPolicy());
		orderService.createOrder(1L, "itemA", 10000);
	}*/
}
