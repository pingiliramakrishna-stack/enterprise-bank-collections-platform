package com.bank.collections.persistence.repository;

import java.util.Optional;

import com.bank.collections.persistence.entity.RequestControlEntity;

public interface RequestControlRepositoryCustom {

    Optional<RequestControlEntity> claimNextRequest();

}