package hongik.db.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findByNameContaining(String name);
    boolean existsByName(String name);
    Team findByName(String name);

}
