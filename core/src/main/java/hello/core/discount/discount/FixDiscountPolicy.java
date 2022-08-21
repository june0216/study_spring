package hello.core.discount.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{
	private int discountFixAmount = 1000;// 1000원 할인

	@Override
	public int discount(Member member, int price) {
		if(member.getGrade() == Grade.VIP) // enum 타입은은 == 으로 동등 비교 한다.
		{
			return discountFixAmount;
		}
		return 0;
	}
}
