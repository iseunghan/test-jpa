package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private TeamService teamService;

    public void catchException() {
        System.out.println("@@@ memberService catchException");
//        try {
//            teamService.exception();
//        } catch (RuntimeException e) {
//            System.out.println("잡았다!!");
//        }
    }

    /**
     * [호출] 트랜잭션 미적용 -> 트랜잭션 미적용
     */
    public void 트랜잭션_X_메소드가_트랜잭션_X_메소드_호출() {
        System.out.println("-----------------");
        memberRepository.findById(1L);
        System.out.println("-----------------");
        teamService.트랜잭션_X_메소드();
    }

    /**
     * [호출] 트랜잭션 적용 -> 트랜잭션 미적용
     */
    @Transactional
    public void 트랜잭션_O_메소드가_트랜잭션_X_메소드_호출() {
        System.out.println("-----------------");
        memberRepository.findById(2L);
        System.out.println("-----------------");
        teamService.트랜잭션_X_메소드();
    }

    public void call_내부_트랜잭션_호출함_1_without_Tx() {
        System.out.println("-----------------");
        memberRepository.findById(1L);
        called_내부_트랜잭션_호출해도_트랜잭션_동작안함();
        System.out.println("-----------------");
    }

    @Transactional
    public void call_내부_트랜잭션_호출함_2_with_Tx() {
        System.out.println("call_내부_트랜잭션_호출함");
        memberRepository.findById(1L);
        called_내부_트랜잭션_호출해도_트랜잭션_동작안함();
        System.out.println("--------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void called_내부_트랜잭션_호출해도_트랜잭션_동작안함() {
        System.out.println("called_내부_트랜잭션_호출해도_트랜잭션_동작안함");
        memberRepository.findById(1L);
        System.out.println("--------------------");
    }

    /**
     * 트랜잭션 적용 -> 트랜잭션 적용 (전파속성 REQUIRED_NEW)
     */
    @Transactional
    public void call_REQUIRED_NEW_Tx() {
        System.out.println("----------------");
        memberRepository.findById(1L);
        teamService.매번_새로운_트랜잭션_생성();
        System.out.println("----------------");
    }

    /**
     * (A)트랜잭션 적용 -> (B)트랜잭션 적용 (전파속성 REQUIRED_NEW)
     * A,B 서로 별도의 트랜잭션 이므로 B에서 예외발생 시 A에게 영향 주지 않음.
     */
    @Transactional
    public void call_REQUIRED_NEW_Tx_and_Exception() {
        System.out.println("----------------");
        memberRepository.save(new Member());
        try {
            teamService.매번_새로운_트랜잭션_생성_예외발생();
        } catch (Exception e) {
            System.out.println("잡았다.");
        }
        System.out.println("----------------");
    }

    /**
     * 트랜잭션 적용 -> 트랜잭션 적용 (전파속성 NOT_SUPPORTED)
     */
    @Transactional
    public void call_NOT_SUPPORTED_Tx() {
        System.out.println("----------------");
        memberRepository.findById(1L);
        teamService.기존_트랜잭션_중지_후_트랜잭션_없이_진행();
        System.out.println("----------------");
    }
}
