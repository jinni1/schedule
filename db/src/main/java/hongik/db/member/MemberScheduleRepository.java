package hongik.db.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberScheduleRepository extends JpaRepository<MemberSchedule, Long> {
    List<MemberSchedule> findByMember(Member member);
}
