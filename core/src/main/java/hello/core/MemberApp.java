package hello.core;

import hello.core.member.*;
import hello.core.order.AppConfig;

public class MemberApp {
	public static void main(String[] args) {
		//MemberService memberService = new MemberServiceImpl();
		AppConfig appConfig = new AppConfig();
		MemberService memberService = appConfig.memberService(); // memberService 인터페이스를 불러오고 "memberService"에는 만들어진 구현체가 들어갈 것이다.
		Member member = new Member(1L, "mamberA", Grade.VIP);
		memberService.join(member);

		Member findMember = memberService.findMember(1L);
		System.out.println("findMember = " + findMember.getName());
		System.out.println("member = " + member.getName());
	}
}
