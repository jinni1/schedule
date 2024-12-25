package hongik.db.member;

import hongik.db.team.Team;
import hongik.db.team.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private MemberScheduleRepository memberScheduleRepository;

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    public Member login(String name, String password) throws IllegalAccessException {
        Member member = memberRepository.findByName(name);
        if (member != null && ((Member) member).getPassword().equals(password)) {
            return member;
        }
        throw new IllegalAccessException("Invalid name or password");
    }

    public void deleteMember(Integer id) {
        memberRepository.deleteById(Long.valueOf(id));
    }

    public List<Team> getTeamsByMemberId(Integer memberId) {
        return teamMemberRepository.findTeamsByMemberId(Long.valueOf(memberId));
    }

    public List<MemberSchedule> getSchedulesByMemberId(Integer memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        return memberScheduleRepository.findByMember(member);
    }

    public void updateSchedule(Integer id, String newSchedule) {
        MemberSchedule existingSchedule = getScheduleById(id);
        existingSchedule.setSchedule(LocalDate.parse(newSchedule));
        memberScheduleRepository.save(existingSchedule);
    }

    private MemberSchedule getScheduleById(Integer id) {
        return null;
    }

    public void deleteSchedule(Integer id) {
        memberScheduleRepository.deleteById(Long.valueOf(id));
    }
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // 모든 멤버를 가져오는 JPA 메서드
    }
}
