package com.grpcspring.web;

import com.grpcspring.dao.model.Account;
import com.grpcspring.service.ExampleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * The type Controller.
 */
@RestController
@Slf4j
@RequestMapping("/accounts")
public class Controller {

    @Autowired
    private ExampleService exampleService;

    /**
     * Post.
     *
     * @param account the account
     */
    @PostMapping("/create")
    @ApiOperation("create account")
    public void post(@RequestBody Account account) {
        log.info("in [{}]", account);
        this.exampleService.postAcc(account);
    }


    /**
     * Get mono.
     *
     * @param phone the phone
     * @return the mono
     */
    @GetMapping("/get")
    @ApiOperation("get account")
    public Mono<Account> get(@RequestParam int phone) {
        return Mono.just(this.exampleService.getAcc(phone));
    }
}
