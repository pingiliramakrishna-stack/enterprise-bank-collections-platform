package com.bank.collections.persistence.worker.processor;

import com.bank.collections.persistence.entity.RequestControlEntity;

public interface RequestProcessor {

    void process(RequestControlEntity request) throws Exception;

}