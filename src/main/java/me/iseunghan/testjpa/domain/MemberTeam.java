package me.iseunghan.testjpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"MEMBER_ID", "TEAM_ID"})})
public class MemberTeam {
    @Id @GeneratedValue
    private Long candidateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;

    private String text = "hello";

    public MemberTeam(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeText(String text) {
        this.text = text;
    }
}
