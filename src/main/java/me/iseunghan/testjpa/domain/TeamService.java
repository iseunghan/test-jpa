package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    /**
     * @Transactional을 붙이게 되면, TeamService.findAll 메소드 이름으로 트랜잭션이 생성된다.
     * SimpleJpaRepository에 붙은 @Transactional의 기본속성으로 인해 이전에 생성된 트랜잭션에 참여하게 되므로,
     * TeamService.findAll 까지 트랜잭션이 살아있어 LazyLoading이 가능하다.
     */
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        List<Team> teams = teamRepository.findAll();
        extractMembers(teams);
        return teams;
    }

    public void extractMembers(List<Team> teams) {
        teams.forEach(t -> t.getMembers().get(0).getName());
    }
}
