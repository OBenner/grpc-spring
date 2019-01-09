package com.grpcspring.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String firstName;
    private String lastName;
    private int phone;

}
