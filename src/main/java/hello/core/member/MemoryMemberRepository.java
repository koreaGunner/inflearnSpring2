package hello.core.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component //memoryMemberRepository
@Primary
public class MemoryMemberRepository implements MemberRepository{

    //ConcurrentHashMap 검색해보기
    private static Map<Long, Member> store = new HashMap<>();
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
