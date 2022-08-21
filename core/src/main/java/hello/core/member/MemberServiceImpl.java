package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
	/*version1 (AppConfig 추가 전 상황)
	그냥 인터페이스인 private final MemberRepository memberRepository; 만 구현체 연결 없이 선언하면 null 오류가 날 것이다.
	-> 구현 객체를 선택해야 한다.
	문제점 = 여기서 DIP를 위반한다. -> memberRepository도 의존하고 이를 구현한 구현체도 의존한다.
	private final MemberRepository memberRepository = new MemoryMemberRepository();*/



	//version2 AppConfing 추가 후 상황
	private final MemberRepository memberRepository; // 인터페이스만 의존할 수 있게 되었다.

	@Autowired//ac.getBean(MemberRepository.class) 이것과 비슷하게 동작
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member); //이렇게 하면 다형성에 의해 인터페이스가 아닌 구현체의 메소드를 호출한다.

	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	} // 구현체가 하나만 있다면 관례상 impl이라고 붙여서 사용한다.

	//테스트 용도
	public MemberRepository getMemberRepository()
	{
		return memberRepository;
	}

}
