package hongik.db.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/{memberId}/schedule")
public class MemberScheduleController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberScheduleService memberScheduleService;
/*
    @PostMapping("/delete/{scheduleId}")
    public String deleteSchedule(@PathVariable Integer memberId, @PathVariable Integer scheduleId) {
        memberScheduleService.deleteSchedule(scheduleId);
        return "redirect:/member/" + memberId;
    }

    @GetMapping("/edit/{scheduleId}")
    public String getEditSchedulePage(@PathVariable Integer memberId, @PathVariable Integer scheduleId, Model model) {
        MemberSchedule schedule = memberScheduleService.getScheduleById(scheduleId);
        model.addAttribute("schedule", schedule);
        model.addAttribute("memberId", memberId);
        return "editSchedule";
    }

    @PostMapping("/edit/{scheduleId}")
    public String editSchedule(@PathVariable Integer memberId, @PathVariable Integer scheduleId, @RequestParam String schedule) {
        memberScheduleService.updateSchedule(scheduleId, schedule);
        return "redirect:/member/" + memberId;
    }
*/
}
