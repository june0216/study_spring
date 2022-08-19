package hello.core.singleton;

public class SingletonService {
	//자기 자신을 내부에서 private으로 가지고 있는데 static으로 가지고 있다. 이렇게 한다면 class level에 올라가기 때문에 딱 하나만 올라가게 된다.
	private static final SingletonService instance = new SingletonService(); //자기자신을 생성하여 instance에 넣는다

	public static SingletonService getInstance() { //조회할 때 쓴다.
		return instance;
	}

	private SingletonService() {//3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.

	}

	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");}

}
