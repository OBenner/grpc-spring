package com.grpcspring.service;

import com.grpcspring.dao.db.DaoQuery;
import com.grpcspring.dao.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The type Pilot service.
 */
@Service
@Slf4j
public class ExampleService {



    private DaoQuery daoQuery;

    /**
     * Instantiates a new Pilot service.
     *
     * @param daoQuery the dao query
     */
    @Autowired
    public ExampleService(DaoQuery daoQuery) {
        this.daoQuery = daoQuery;
    }


    /**
     * Post acc.
     *
     * @param account the account
     */
    public void postAcc(Account account) {
        this.daoQuery.addAcc(account);
    }

    /**
     * Gets acc.
     *
     * @param phone the phone
     * @return the acc
     */
    public Account getAcc(int phone) {
        log.info("fount by phone [{}]", phone);
        Account account = this.daoQuery.getAcc(phone);
        log.info("acc [{}]", account);
        if (account == null) {
            throw new RuntimeException("not Found");
        }
        return account;
    }
}
