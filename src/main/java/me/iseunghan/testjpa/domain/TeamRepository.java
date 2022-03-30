package me.iseunghan.testjpa.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "select t from Team t join fetch t.members")
    List<Team> findAllJoinFetch();

    @EntityGraph(attributePaths = "members")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();
}
