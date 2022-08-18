package hello.core.beanfind;

import hello.core.order.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextinfoTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


	@Test
	@DisplayName("모든 빈 출력하기")
	void findAllBean()
	{
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			Object bean = ac.getBean(beanDefinitionName); // 타입을 아직 정하지 않았으므로 object로 꺼내온다.
			System.out.println("name = " + beanDefinitionName + "object = " + bean);
		}
	}

	@Test
	@DisplayName("애플리케이션 빈 출력하기")
	void findApplicationBean()
	{
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // 빈에 대한 메타 데이터정보

			//내가 애플리케이션을 개발하기 위해 등록한 빈만 출력하기 위해
			/*beanRole에는 2가지가 있다.
			ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
			ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
			*  */
			if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION)
			{
				Object bean = ac.getBean(beanDefinitionName);
				System.out.println("name = " + beanDefinitionName + "object = " + bean);
			}
		}
	}

}
