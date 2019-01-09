package com.grpcspring.dao.db;

import com.grpcspring.dao.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * The type Dao query.
 */
@Repository
public class DaoQuery {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Add acc.
     *
     * @param acc the acc
     */
    public void addAcc(Account acc) {
        try {
            this.jdbcTemplate.update("INSERT INTO accounts(firstName, lastName, phone) VALUES (?,?,?)", acc.getFirstName(), acc.getLastName(), acc.getPhone());
        } catch (DuplicateKeyException e) {
            new RuntimeException("duplicate");
        }

    }

    /**
     * Gets acc.
     *
     * @param phone the phone
     * @return the acc
     */
    public Account getAcc(int phone) {
        String sql = "select * from accounts where phone = ?";
        try {
            return (Account) this.jdbcTemplate.queryForObject(sql, new Object[]{phone}, new BeanPropertyRowMapper(Account.class));

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
