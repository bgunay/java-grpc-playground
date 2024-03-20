package com.gnysoft.test.sec13;

import com.gnysoft.models.sec13.BalanceCheckRequest;
import com.gnysoft.models.sec13.BankServiceGrpc;
import com.gnysoft.models.sec13.Money;
import com.gnysoft.models.sec13.WithdrawRequest;
import com.gnysoft.test.common.ResponseObserver;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcSSLTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(GrpcSSLTest.class);

    @Test
    public void clientWithSSLTest() {
        var channel = NettyChannelBuilder.forAddress("localhost", 6565)
                                         .sslContext(clientSslContext())
                                         .build();
        var stub = BankServiceGrpc.newBlockingStub(channel);
        var request = BalanceCheckRequest.newBuilder()
                                         .setAccountNumber(1)
                                         .build();
        var response = stub.getAccountBalance(request);
        log.info("{}", response);
        channel.shutdownNow();
    }

    @Test
    public void streaming() {
        var channel = NettyChannelBuilder.forAddress("localhost", 6565)
                                         .sslContext(clientSslContext())
                                         .build();
        var stub = BankServiceGrpc.newStub(channel);
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(30)
                .build();
        var observer = ResponseObserver.<Money>create();
        stub.withdraw(request, observer);
        observer.await();

        channel.shutdownNow();
    }

}
