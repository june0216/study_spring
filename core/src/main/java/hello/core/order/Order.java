package hello.core.order;

public class Order { // 주문 후 할인정책에 따른 가격이 정해진 다음 생성되는 객체임
	private Long memberId;
	private String itemName;
	private int itemPrice;
	private int discountPrice;

	public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
		this.memberId = memberId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.discountPrice = discountPrice;
	}

	//최종 할인 적용된 금액 계산 함수
	public int calculatePrice()
	{
		return itemPrice-discountPrice;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getItemName() {
		return itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}


	//결과를 출력하기 위해서 System.out.println("Order = " + Order);일 때 객체 출력하기 위해
	@Override
	public String toString() {
		return "Order{" +
				"memberId=" + memberId +
				", itemName='" + itemName + '\'' +
				", itemPrice=" + itemPrice +
				", discountPrice=" + discountPrice +
				'}';
	}

}
