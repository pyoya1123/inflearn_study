package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{

    // 저장소니까 Map같은게 있어야할거임.
    private static Map<Long, Member> store = new HashMap<>();
    // 여기서는 HashMap을 썼는데 사실 콘커런트 hashMap을 써야함.
    // 여러 군데에서 이 store 변수에 접근하면 동시성 이슈가 있을 수 있음.

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
