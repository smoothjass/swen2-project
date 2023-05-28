package fhtw.swen2.duelli.duvivie.swen2project.Daos;

import jakarta.persistence.EntityManagerFactory;

public class LogDao {
    private final EntityManagerFactory entityManagerFactory;

    public LogDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
