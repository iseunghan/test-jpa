package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
