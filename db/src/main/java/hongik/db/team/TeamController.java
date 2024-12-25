package hongik.db.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // 팀 페이지 렌더링
    @GetMapping
    public String getTeamPage() {
        return "team";
    }

    @GetMapping("/search")
    public String getSearchTeamPage() {
        return "searchTeam"; // 팀 검색 페이지
    }

    @PostMapping("/create")
    public String createTeam(@RequestParam String name,
                             @RequestParam String password,
                             @RequestParam Integer memberId, // 멤버 ID로 수정
                             Model model) {
        try {
            teamService.createTeam(name, password, memberId); // 팀 생성 및 자동 가입
            model.addAttribute("message", name + " 팀이 생성 및 자동 가입되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "createTeam"; // 생성된 팀 페이지로 리다이렉트
    }


    @GetMapping("/delete")
    public String getDeleteTeamPage() {
        return "deleteTeam"; // 팀 삭제 페이지
    }
}
