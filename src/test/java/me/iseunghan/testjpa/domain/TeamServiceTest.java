package me.iseunghan.testjpa.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Before
    public void setup() {
        Team team = new Team(null, "team", new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            Member member = new Member(null, "member" + i, null);
            member.updateTeam(team);
        }

        teamService.save(team);
    }

    @Test
    public void lazy_exception() {
        List<Team> teams = teamService.findAll();

        assertThat(teams).isNotNull();
    }

}