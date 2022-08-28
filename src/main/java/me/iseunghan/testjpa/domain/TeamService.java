package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> findAll() {
        List<Team> teams = teamRepository.findAllEntityGraph();
        extractMembers(teams);
        return teams;
    }

    public void extractMembers(List<Team> teams) {
        teams.forEach(t -> t.getMembers().get(0).getName());
    }

    public void 트랜잭션_X_메소드() {
        System.out.println("-----------------");
        memberRepository.findById(1L);
        System.out.println("-----------------");
        System.out.println("this_is_not_transaction_method");
    }
}
