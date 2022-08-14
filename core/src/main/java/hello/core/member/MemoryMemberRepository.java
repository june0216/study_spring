package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{
	private static Map<Long, Member> store = new HashMap<>(); // 저장소를 만든다.
	//참고 - hashMap 같은 경우 여러 군데에서 쓰면 동시성 문제가 생겨서 ConcurrentHashMap 을 쓰는 것이 맞는데 간단한 예제이므로 그냥 해시 맵을 사용

	@Override
	public void save(Member member) {
		store.put(member.getId(), member);
	}

	@Override
	public Member findById(Long memberId) {
		return store.get(memberId);
	}
}
