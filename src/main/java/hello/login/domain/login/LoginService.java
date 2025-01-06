package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /*
    * 로그인
    * 아이디와 비밀번호로 회원인증하기
    *
    * */

    public Member login(String loginId, String password){
        return  memberRepository.findByLoginId(loginId)
                .filter(m -> m.equals(password))
                .orElse(null);
    }
}
