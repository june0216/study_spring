package hello.core.order;

import hello.core.discount.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

/*	version1 -> AppConfig 적용 전
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	//private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); -> 할인 정책 변경으로 삭제
	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	//DIP 위반! -> 구현체인 RateDiscountPolicy에도 의존하고 있음*/


	//version2  -> AppConfig 적용 후
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		//-> OrderServiceImpl 어떤 구현 객체가 들어올지는 알 수 없다. 의존 관계를 외부에 맡긴 것이다.
		this.memberRepository = memberRepository; //할당 하는 부분
		this.discountPolicy = discountPolicy;
	}


	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		//단일 책임 원칙에 의하여 잘 설계된 것이다. -> 할인 정책이 바뀌어도 할인에 대한 모든 부분은 따로 만들어두었기 때문에 주문은 그냥
		//결과만 받아도 된다. 그래서 변경이 있어도 한 곳만 변경 가능

		return new Order(memberId,itemName, itemPrice, discountPrice );
	}

	//테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
