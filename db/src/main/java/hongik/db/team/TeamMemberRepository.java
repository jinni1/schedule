package hongik.db.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("SELECT t FROM Team t JOIN TeamMember tm ON t.id = tm.teamId WHERE tm.memberId = :memberId")
    List<Team> findTeamsByMemberId(@Param("memberId") Long memberId);
}
