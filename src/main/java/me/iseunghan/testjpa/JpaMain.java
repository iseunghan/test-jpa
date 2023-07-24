package me.iseunghan.testjpa;

import me.iseunghan.testjpa.domain.Member;
import me.iseunghan.testjpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        // Transaction start
        tx.begin();

        try {
            Team team = new Team("test Team");
//            em.persist(team);
//            em.flush();

            Member member = new Member("member", team);
            Member member2 = new Member("member2", team);

            team.addMember(member);
            team.addMember(member2);

            System.out.println("------------------1------------------");
            em.persist(team);
            System.out.println("------------------------------------");

            System.out.println("------------------2------------------");
            tx.commit();    // 이 시점에 쿼리가 날라감. (flush가 실행됨)
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
