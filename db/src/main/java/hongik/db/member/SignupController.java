package hongik.db.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute Member member) {
        memberService.registerMember(member);
        return "redirect:/login";
    }
}
