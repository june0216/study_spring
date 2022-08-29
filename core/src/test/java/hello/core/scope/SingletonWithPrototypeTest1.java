package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.annotation.Annotation;

public class SingletonWithPrototypeTest1 {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();

		Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}

	@Test
	void singletonClientUserPrototype(){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);

		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		Assertions.assertThat(count1).isEqualTo(1);

		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean1.logic();
		Assertions.assertThat(count2).isEqualTo(2);

	}

	@Scope("singleton")
	static class ClientBean{
		private final PrototypeBean prototypeBean;//생성 시점에 주입

/*		@Autowired
		ApplicationContext applicationContext;*/

		@Autowired
		public ClientBean(PrototypeBean prototypeBean) //스프링 빈에 등록이 되면서 이 때 프로토타입 빈을 내놓으라고 한다.
		{
			this.prototypeBean = prototypeBean; //그러면 스프링 컨테이너가 프로토타입 빈을 만들어서 준다. (할당 + 주입)
		}


		public int logic(){
			//무식한 방법(만들 때마다 빈을 생성)PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}


	@Scope("prototype")
	static class PrototypeBean{
		private int count = 0;

		public void addCount(){
				count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init" + this);
		}

		@PreDestroy // 호출이 안되지만 적음
		public void destroy() {
			System.out.println("PrototypeBean.init" + this);
		}
	}
}
