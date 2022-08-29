package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {
	@Test
	void prototypeBeanFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		System.out.println("find prototypeBean1");//빈 생성 시점을 확인
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

		System.out.println("find prototypeBean2");
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

		//결과 출력
		System.out.println("prototypeBean1 = " + prototypeBean1);
		System.out.println("prototypeBean2 = " + prototypeBean2);

		//검증 -
		Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);


		prototypeBean1.destoy();//클라이언트가 직접 호출해야 한다.
		prototypeBean2.destoy();
		ac.close(); //클로즈가 안 된다.
	}

	@Scope("prototype")
	//@Component 없어도 된다. -> 파라미터로 들어가면 스캔 대상이 되기 때문
	static class PrototypeBean{
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init");
		}

		@PreDestroy
		public void destoy() {
			System.out.println("PrototypeBean.destoy");
		}

	}
}
