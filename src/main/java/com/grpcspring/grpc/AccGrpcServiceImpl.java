
package com.grpcspring.grpc;


import grpcspring.AccountServiceGrpc;
import grpcspring.CreateAccResponse;
import grpcspring.GetAccRequest;

import com.grpcspring.dao.model.Account;
import com.grpcspring.service.ExampleService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The type Acc grpc exampleService.
 */
@GrpcService
@Slf4j
public class AccGrpcServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {

    private ExampleService exampleService;



    /**
     * Instantiates a new Acc grpc exampleService.
     *
     * @param exampleService the exampleService
     */
    @Autowired
    public AccGrpcServiceImpl(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Override
    public void getAcc(GetAccRequest request, StreamObserver<grpcspring.Account> responseObserver) {
       Account account = new Account();
        log.info("req gRPC [{}]", request.getPhone());
        int phone = Integer.parseInt(request.getPhone());
        log.info("phone [{}]", phone);

        try {
            account = this.exampleService.getAcc(phone);
            log.info("found acc [{}]", account);
        } catch (Exception e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
        grpcspring.Account acc = accToGrpc(account);
        responseObserver.onNext(acc);

        responseObserver.onCompleted();

    }

    @Override
    public void createAcc(grpcspring.Account request, StreamObserver<CreateAccResponse> responseObserver) {
        log.info("req gRPC [{}]", request);
        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setPhone(request.getPhone());

        try {
            this.exampleService.postAcc(account);

        } catch (Exception e) {
            responseObserver.onError(Status.ALREADY_EXISTS
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }

        responseObserver.onNext(CreateAccResponse.newBuilder()
                .setStatus("Success")
                .build());
        responseObserver.onCompleted();
    }


    private grpcspring.Account accToGrpc(Account account) {
        return grpcspring.Account.newBuilder()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setPhone(account.getPhone())
                .build();
    }
}
