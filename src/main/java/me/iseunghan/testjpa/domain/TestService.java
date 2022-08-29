package me.iseunghan.testjpa.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TestService {

    public void 트랜잭션_X_메소드() {
        System.out.println("트랜잭션_X_메소드");
    }

    @Transactional
    public void REQUIRED_메소드() {
        System.out.println("REQUIRED_메소드");
    }
}
