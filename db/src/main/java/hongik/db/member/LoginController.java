package hongik.db.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public String getLoginPage() {
        return "login";
    }


    @PostMapping
    public String login(@RequestParam String name, @RequestParam String password, HttpSession session, Model model) {
        try {
            Member member = memberService.login(name, password); // 로그인 처리
            session.setAttribute("loggedInMember", member); // 세션에 로그인한 멤버 저장
            return "redirect:/member/" + member.getId(); // 회원 페이지로 리다이렉트
        } catch (IllegalAccessException e) {
            model.addAttribute("error", "Invalid name or password");
            return "login"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }
}
