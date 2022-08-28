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

}
