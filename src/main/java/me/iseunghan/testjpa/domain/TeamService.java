package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.findById(2L).get();
        System.out.println(member.getTeam().getName()); // Team Lazy 로딩
        System.out.println("-----------------");
        System.out.println("this_is_not_transaction_method");
    }

    /**
     * 트랜잭션이 있더라도 무조건 새로 생성
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 매번_새로운_트랜잭션_생성() {
        System.out.println("REQUIRES_NEW---------------");
        memberRepository.findById(1L);
        System.out.println("REQUIRES_NEW---------------");
    }

    /**
     * 트랜잭션이 있더라도 무조건 새로 생성
     * 분리된 트랜잭션이여서 부모 트랜잭션에 영향을 끼치지 않음.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 매번_새로운_트랜잭션_생성_예외발생() {
        System.out.println("REQUIRES_NEW---------------");
        memberRepository.save(new Member());    // roll-back
        System.out.println("REQUIRES_NEW---------------");
        throw new RuntimeException();
    }

    /**
     * 트랜잭션이 있더라도 강제로 없이 진행
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void 기존_트랜잭션_중지_후_트랜잭션_없이_진행() {
        System.out.println("NOT_SUPPORTED---------------");
        memberRepository.findById(1L);
        System.out.println("NOT_SUPPORTED---------------");
    }

    /**
     * 트랜잭션이 있더라도 강제로 없이 진행 + Exception 발생
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void 무조건_트랜잭션_없이_진행_Ex() {
        System.out.println("NOT_SUPPORTED---------------");
        memberRepository.findById(1L);
        System.out.println("NOT_SUPPORTED---------------");
        throw new RuntimeException();
    }

    /**
     * 기존 트랜잭션이 존재하면 중첩 트랜잭션으로 실행, 없다면 새로 생성 (REQUIRED 와 동일)
     */
    @Transactional(propagation = Propagation.NESTED)
    public void 중첩된_트랜잭션_실행() {
        System.out.println("NESTED---------------");
        memberRepository.findById(1L);
        System.out.println("NESTED---------------");
    }
}
