package hongik.db.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberScheduleService {
    @Autowired
    private MemberScheduleRepository memberScheduleRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 스케줄 추가
    public void addSchedule(String title, String schedule, Integer memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        MemberSchedule newSchedule = new MemberSchedule();
        newSchedule.setTitle(title);
        newSchedule.setSchedule(LocalDate.parse(schedule));
        newSchedule.setMember(member); // Member 객체 설정

        memberScheduleRepository.save(newSchedule); // 스케줄 저장
        System.out.println("스케줄 저장 완료: " + newSchedule);
    }

    // 멤버 ID를 기반으로 스케줄 가져오기
    public List<MemberSchedule> getSchedulesByMemberId(Integer memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        return memberScheduleRepository.findByMember(member);
    }
}
