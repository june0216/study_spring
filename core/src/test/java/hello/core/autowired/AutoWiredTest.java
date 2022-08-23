package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

	@Test
	void AutowiredOption() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class); //TestBean이 빈이 된다.
	}

	static class TestBean{
		@Autowired(required = false) //자동 주입할 대상이 없으면 이 메소드 자체가 호출되지 않음
		public void setBean1(Member noBena1){ //Member는 스프링 빈이 아니므로 의존 관계 주입 안하려고 함
			System.out.println("noBena1 = " + noBena1);
		}

		@Autowired
		public void setBean2(@Nullable Member noBena2){
			System.out.println("noBena2 = " + noBena2);
		}


		@Autowired
		public void setBean3(Optional<Member> noBena3){
			System.out.println("noBena3 = " + noBena3);
		}

	}
}
