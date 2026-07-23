package com.bank.collections.persistence.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bank.collections.persistence.entity.RequestControlEntity;

@Repository
public class RequestControlRepositoryImpl
        implements RequestControlRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RequestControlEntity> claimNextRequest() {

        String sql = """
                SELECT *
                FROM REQUEST_CONTROL
                WHERE STATUS='RECEIVED'
                ORDER BY REQUEST_CREATED_DATE
                FETCH FIRST 1 ROW ONLY
                FOR UPDATE SKIP LOCKED
                """;

        List<RequestControlEntity> results =
                entityManager.createNativeQuery(sql,
                        RequestControlEntity.class)
                        .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }

}