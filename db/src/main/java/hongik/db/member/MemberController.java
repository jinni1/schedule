package hongik.db.member;

import hongik.db.team.Team;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberScheduleService memberScheduleService;

    // 특정 멤버의 페이지 반환
    @GetMapping("/{id}")
    public String getMemberPage(@PathVariable Integer id, HttpSession session, Model model) {
        Member sessionMember = (Member) session.getAttribute("loggedInMember");

        if (sessionMember == null || !sessionMember.getId().equals(id)) {
            return "redirect:/login"; // 세션이 없거나 ID가 일치하지 않으면 로그인 페이지로 리다이렉트
        }

        // 팀 목록 가져오기
        List<Team> teams = memberService.getTeamsByMemberId(id);
        model.addAttribute("teams", teams);

        // 스케줄 목록 가져오기
        List<MemberSchedule> schedules = memberService.getSchedulesByMemberId(id);
        model.addAttribute("schedules", schedules);

        model.addAttribute("memberId", id);
        return "member"; // 회원 페이지 HTML 반환
    }

    // 모든 멤버 목록 페이지
    @GetMapping
    public String viewAllMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "memberList"; // 모든 멤버 목록 페이지
    }

    // 일정 추가
    @PostMapping("/{id}/add")
    public String addSchedule(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String schedule,
            Model model) {
        try {
            // 스케줄 추가 서비스 호출
            memberScheduleService.addSchedule(title, schedule, id);

            // 성공 메시지와 멤버 ID 전달
            model.addAttribute("memberId", id);
            model.addAttribute("message", "일정이 성공적으로 추가되었습니다!");

            return "addSuccess"; // 성공 페이지로 이동
        } catch (Exception e) {
            // 에러 처리
            model.addAttribute("error", "스케줄 추가 중 오류가 발생했습니다: " + e.getMessage());
            return "error"; // 에러 페이지로 이동
        }
    }

}
