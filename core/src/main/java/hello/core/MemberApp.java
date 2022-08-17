package hello.core;

import hello.core.member.*;
import hello.core.order.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
	public static void main(String[] args) {
		//MemberService memberService = new MemberServiceImpl();
		/*AppConfig appConfig = new AppConfig();
		MemberService memberService = appConfig.memberService(); */// memberService 인터페이스를 불러오고 "memberService"에는 만들어진 구현체가 들어갈 것이다.

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);//스프링이다. 스프링은 이것으로 시작된다. 빈들을 관리해준다.
		//어노테이션 기반으로 Config를 하고 있다. AppConfig에 있는 환경 설정들에 대한 정보를 기반으로 스프링 컨테이너에서 관리하게 해준다.

		MemberService memberService = applicationContext.getBean("memberService", MemberService.class); //빈을 꺼낸다, 함수이름과 반환타입을 파라미터에 넣는다.


		Member member = new Member(1L, "mamberA", Grade.VIP);
		memberService.join(member);

		Member findMember = memberService.findMember(1L);
		System.out.println("findMember = " + findMember.getName());
		System.out.println("member = " + member.getName());
	}
}
