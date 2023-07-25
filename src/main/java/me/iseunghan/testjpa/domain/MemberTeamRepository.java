package me.iseunghan.testjpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {
    Optional<MemberTeam> findByCandidateId(Long candidateId);
}
