package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

	private String url;

	//디폴트 생성자
	public NetworkClient()
	{
		System.out.println("생성자 호출, url = " + url);
		connect(); //객체 생성, 연결
		call("초기화 연결 메시지"); // 다른 서버에 초기화 연결 메시지를 보낸다. (예제에서는 로그에 찍기만 한다)
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//서비스 시작 시 호출
	public void connect()
	{
		System.out.println("connect : " + url); //어떤 url과 연결 되었는지
	}

	//연결된 상태
	public void call(String message)
	{
		//어떤 url과 연결되어 있고 메시지는 어떤 것인지 출력
		System.out.println("call : " + url + " message = " + message);
	}

	//서비스 종료시 호출
	public void disconnect()
	{
		System.out.println("close : " + url);
	}

	@Override
	public void afterPropertiesSet() throws Exception {//의존관계 주입이 끝나면 호출해주겠다. (객체 생성이 끝나고 의존관계 주입이 끝나면)
		System.out.println("NetworkClient.afterPropertiesSet");
		connect(); //객체 생성, 연결
		call("초기화 연결 메시지");
	}

	@Override
	public void destroy() throws Exception { //종료될 때 호출이 될 것이다.(컨테이너가 내려가면)
		System.out.println("NetworkClient.destroy");
		disconnect();
	}
}
