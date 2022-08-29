package me.iseunghan.testjpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Transaction_Propagation_Test {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @DisplayName("트랜잭션 안붙은 메소드에서 -> 트랜잭션이 안붙은 메소드를 호출 시 영속성 컨텍스트 공유가 안됨.")
    @Test
    void 트랜잭션_X_메소드에서_트랜잭션_X_메소드_호출시_영속성컨텍스트_공유X_테스트() {
        // given
        System.out.println("-----------------");
        memberRepository.save(new Member(1L, "member"));
        System.out.println("-----------------");

        // 트랜잭션 붙은 메소드 -> 안 붙은 메소드 호출
        memberService.트랜잭션_X_메소드가_트랜잭션_X_메소드_호출();
    }

    @DisplayName("트랜잭션 붙은 메소드에서 -> 트랜잭션이 안붙은 메소드를 호출 시 영속성 컨텍스트 공유함.")
    @Test
    void 트랜잭션_O_메소드에서_트랜잭션_X_메소드_호출시_영속성컨텍스트_공유O_테스트() {
        // given
        System.out.println("-----------------");
        Team team1 = new Team();
        team1.setName("team");
        Member member = new Member(null, "member", team1);
        team1.setMembers(List.of(member));
        Team team = teamRepository.save(team1);

        System.out.println("-----------------");

        // 트랜잭션 붙은 메소드 -> 안 붙은 메소드 호출
        memberService.트랜잭션_O_메소드가_트랜잭션_X_메소드_호출();
    }

    @DisplayName("동일 클래스) 트랜잭션이 붙지 않은 메소드에서 -> 트랜잭션이 붙은 메소드 호출 시 트랜잭션 동작 안함!")
    @Test
    void call_inner_transaction_method1() {
        // given
        memberRepository.save(new Member());

        memberService.call_내부_트랜잭션_호출함_1_without_Tx();
    }

    @DisplayName("동일 클래스) 트랜잭션이 붙은 메소드에서 -> 트랜잭션이 붙은 내부 메소드 호출 시 트랜잭션 동작 안함!")
    @Test
    void call_inner_transaction_method2() {
        // given
        memberRepository.save(new Member());

        memberService.call_내부_트랜잭션_호출함_2_with_Tx();
    }

    @DisplayName("REQUIRED_NEW 속성 테스트")
    @Test
    void test_REQUIRED_NEW() {
        // given
        memberRepository.save(new Member());

        memberService.call_REQUIRED_NEW_Tx();
    }

    @DisplayName("REQUIRED_NEW 속성 예외 발생 테스트")
    @Test
    void test_Exception_REQUIRED_NEW() {
        // given
        memberRepository.save(new Member());    // save success

        memberService.call_REQUIRED_NEW_Tx_and_Exception(); // exception
    }

    @DisplayName("NOT_SUPPORTED 속성 테스트")
    @Test
    void test_NOT_SUPPORTED() {
        // given
        memberRepository.save(new Member());

        memberService.call_NOT_SUPPORTED_Tx();
    }
}
