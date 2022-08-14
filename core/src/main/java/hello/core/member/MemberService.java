package hello.core.member;

public interface MemberService { //회원에 대한 요구 사항(가입, 조회)
	void join(Member member);
	Member findMember(Long memberId);
}
