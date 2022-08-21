package hello.core.singleton;

public class StatefulService {
	/* 변경 전
	private int price; // 상태를 유지하는 필드

	public void order(String name, int price)
	{
		System.out.println("name = " + name + "price = " + price);
		this.price = price;

	}*/

	//해결 = 전역 변수로 변경하기
	public int order(String name, int price)
	{
		System.out.println("name = " + name + "price = " + price);
		return price;

	}

}
