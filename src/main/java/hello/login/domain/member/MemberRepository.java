package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;//static 사용


    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
    /*    ArrayList<Member> members = findAll();
        for (Member member : members) {
            if(member.getLoginId().equals(loginId)){
                return Optional.of(member);
            }
        }
        return Optional.empty();*/

        //람다 사용해보기
        return findAll().stream()
                .filter(m -> m.getId().equals(loginId))
                .findFirst();
    }

    private static ArrayList<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){

    }

}
