package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/login")
    public String loginForm(Model model,LoginForm form){
      model.addAttribute("loginForm",form);
        return "login/loginForm";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        /*
        * 로그인 아이디와 패스워드가 모두 불일치 할 경우
        * 글로벌 오류 발생
        * */
        if(loginMember == null){
            bindingResult.reject("loginfail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공
        //세션 쿠키 :  쿠키에 시간 정보를 설정하지 않으면 브라우저가 종료되면 모두 종료된다.
        Cookie cookie = new Cookie("memberid", String.valueOf(loginMember.getId()));
        response.addCookie(cookie); //쿠키 담기

        return "redirect:/";
    }

    /*
    * V1 단점 : 보안상 치명적인 문제 발생
    * 1. 쿠키 값을 임의로 변경할 수 있다.
    * 2. 쿠키에 담긴 정보를 훔쳐갈 수 있다.-> 해커가 쿠키를 한번 훔치면 평생 사용할 수 있다.
    *
    * */

    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response,"memberId");
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie("cookieName", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
