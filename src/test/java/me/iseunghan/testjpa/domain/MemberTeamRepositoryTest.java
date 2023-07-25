package me.iseunghan.testjpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTeamRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private MemberTeamRepository memberTeamRepository;
    @Autowired private EntityManager em;
    private Long member1Id;
    private Long member2Id;
    private Long team1Id;
    private Long team2Id;
    private Long memberTeam1CandidateId;


    @BeforeEach
    void setup() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        team1.addMember(member1);
        team1.addMember(member2);
        teamRepository.saveAndFlush(team1);
        teamRepository.saveAndFlush(team2);

        MemberTeam memberTeam = new MemberTeam(member1, team1);
        memberTeamRepository.saveAndFlush(memberTeam);
        memberTeam1CandidateId = memberTeam.getCandidateId();
        MemberTeam memberTeam2 = new MemberTeam(member1, team2);
        memberTeamRepository.saveAndFlush(memberTeam2);

        this.member1Id = member1.getId();
        this.member2Id = member2.getId();
        this.team1Id = team1.getId();
        this.team2Id = team2.getId();
        em.flush();
        em.clear();
    }

    @Test
    void MemberTeam을_변경할수있다() throws Exception {
        System.out.println("-----------------------------------");
        // given
        MemberTeam memberTeam = memberTeamRepository.findByCandidateId(memberTeam1CandidateId).orElseThrow();
        Member member2 = memberRepository.findById(member2Id).orElseThrow();

        // when
        memberTeam.changeText("edit");
        memberTeam.changeMember(member2);
        MemberTeam modifiedMemberTeam = memberTeamRepository.saveAndFlush(memberTeam);

        // then
        assertThat(modifiedMemberTeam.getMember().getId()).isEqualTo(member2Id);
        assertThat(memberTeamRepository.count()).isEqualTo(2);
    }
}