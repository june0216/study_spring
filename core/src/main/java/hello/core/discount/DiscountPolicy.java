package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
	/*
	* @return 은 할인 대상 금액이다.
	* */
	int discount(Member member, int price);

}
