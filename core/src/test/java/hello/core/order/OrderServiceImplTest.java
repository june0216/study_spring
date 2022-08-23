package hello.core.order;

import hello.core.discount.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

	/*@Test
	void createOrder() {
		OrderServiceImpl orderService = new OrderServiceImpl(); //NullPointerException오류 -> MemberRepository 와 DiscountPolicy의 값을 정하지 않아서
		orderService.createOrder(1L, "itemA", 10000);
	}*/

	@Test
	void createOrder() { //스프링 없이 순수한 자바 코드로 테스트한 것임 -> 생성자 코드 의존관계 주입한다면 테스트 가능
		MemoryMemberRepository memberRepository = new MemoryMemberRepository();
		memberRepository.save(new Member(1L, "name", Grade.VIP));
		OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
		Order order = orderService.createOrder(1L, "itemA", 1000);
		Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
	}

}