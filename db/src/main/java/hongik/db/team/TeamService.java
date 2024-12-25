package hongik.db.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    // 팀 검색
    public List<Team> searchTeamByName(String name) {
        return teamRepository.findByNameContaining(name);
    }

    public void createTeam(String name, String password, Integer memberId) {
        // 팀 이름 중복 검사
        if (teamRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 팀 이름입니다.");
        }

        // 팀 생성
        Team team = new Team();
        team.setName(name);
        team.setPassword(password);
        Team savedTeam = teamRepository.save(team); // 팀 저장

        // 팀 자동 가입 (팀 생성자 가입)
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(savedTeam.getId());
        teamMember.setMemberId(memberId);
        teamMemberRepository.save(teamMember); // 팀 멤버 저장
    }

    // 팀 ID로 팀 가져오기
    public Team getTeamById(Integer teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    // 팀 가입
    public boolean joinTeam(Integer teamId, Integer memberId, String password) {
        Team team = getTeamById(teamId);
        if (team != null && team.getPassword().equals(password)) {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(teamId);
            teamMember.setMemberId(memberId);
            teamMemberRepository.save(teamMember);
            return true;
        }
        return false;
    }

    // 팀 삭제
    public boolean deleteTeam(String name, String password) {
        Team team = teamRepository.findByName(name);
        if (team != null && team.getPassword().equals(password)) {
            teamRepository.delete(team);
            return true;
        }
        return false;
    }
}
