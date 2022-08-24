package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {
	private String name;
	private int age;

/*	//롬복을 사용하면 다음과 같은 get, set 메소드 없이 get, set을 사용할 수 있다.

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}*/

	public static void main(String[] args) {
		HelloLombok helloLombok = new HelloLombok();
		helloLombok.setName("hello");
		String name = helloLombok.getName();
		System.out.println("name = " + name);
		System.out.println("helloLombok = " + helloLombok);
	}
}
