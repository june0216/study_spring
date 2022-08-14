package hello.core.member;

public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	//그냥 인터페이스인 private final MemberRepository memberRepository; 만 구현체 연결 없이 선언하면 null 오류가 날 것이다.
	// -> 구현 객체를 선택해야 한다.

	@Override
	public void join(Member member) {
		memberRepository.save(member); //이렇게 하면 다형성에 의해 인터페이스가 아닌 구현체의 메소드를 호출한다.

	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	} // 구현체가 하나만 있다면 관례상 impl이라고 붙여서 사용한다.

}
