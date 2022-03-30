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

    public List<Team> findAll() {
        List<Team> teams = teamRepository.findAll();
        extractMembers(teams);
        return teams;
    }

    public void extractMembers(List<Team> teams) {
        teams.forEach(t -> t.getMembers().get(0).getName());
    }
}
