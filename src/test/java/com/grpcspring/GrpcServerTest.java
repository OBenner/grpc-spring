package com.grpcspring;

import grpcspring.AccountServiceGrpc;
import grpcspring.GetAccRequest;
import com.grpcspring.dao.model.Account;
import com.grpcspring.grpc.AccGrpcServiceImpl;
import com.grpcspring.service.ExampleService;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrpcSpringApplicationTests.class)
public class GrpcServerTest {

    @Mock
    private ExampleService service;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {

    }
    /*
    /**
     * This rule manages automatic graceful shutdown for the registered servers and channels at the
     * end of test.
     */

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    static Account ACCOUNT = new Account("name", "lastname", 0);

    @Test
    public void getAcc() throws IOException {

        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new AccGrpcServiceImpl(service)).build().start());

        AccountServiceGrpc.AccountServiceBlockingStub blockingStub = AccountServiceGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        Mockito.when(service.getAcc(0)).thenReturn(ACCOUNT);
        grpcspring.Account account =
                blockingStub.getAcc(GetAccRequest.newBuilder()
                        .setPhone("0")
                        .build());

        assertEquals(ACCOUNT.getFirstName(), account.getFirstName());

    }

    //TODO
    @Test(expected = RuntimeException.class)
    public void getAccNotFound() throws IOException {

        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new AccGrpcServiceImpl(service)).build().start());


        AccountServiceGrpc.AccountServiceBlockingStub blockingStub = AccountServiceGrpc.newBlockingStub(
                // Create a client channel and register for automatic graceful shutdown.
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        Mockito.when(service.getAcc(1)).thenReturn(null);
//        try{
        grpcspring.Account account =
                blockingStub.getAcc(GetAccRequest.newBuilder()
                        .setPhone("1")
                        .build());

//        }catch (Exception e){
//            assertEquals("not Found", e.getMessage());
//        }

    }


}
