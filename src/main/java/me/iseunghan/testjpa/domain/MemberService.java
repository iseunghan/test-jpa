package me.iseunghan.testjpa.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private TeamService teamService;

    public void catchException() {
        System.out.println("@@@ memberService catchException");
        teamService.exception();
//        try {
//            teamService.exception();
//        } catch (RuntimeException e) {
//            System.out.println("잡았다!!");
//        }
    }
}
