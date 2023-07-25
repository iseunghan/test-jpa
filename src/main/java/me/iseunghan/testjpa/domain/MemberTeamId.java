package me.iseunghan.testjpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Embeddable
public class MemberTeamId implements Serializable {
    private Long teamId;
    private Long memberId;

    public void changeMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberTeamId)) return false;
        MemberTeamId that = (MemberTeamId) o;
        return Objects.equals(teamId, that.teamId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, memberId);
    }
}
