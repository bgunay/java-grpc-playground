package com.gnysoft.test.sec12;

import com.gnysoft.models.sec12.Money;
import com.gnysoft.models.sec12.WithdrawRequest;
import com.gnysoft.test.common.ResponseObserver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
    It is a class to demo changing executor
 */
public class Lec02ExecutorCallOptionsTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ExecutorCallOptionsTest.class);

    @Test
    public void executorDemo() {
        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                                     .setAccountNumber(1)
                                     .setAmount(30)
                                     .build();
        this.bankStub
                .withExecutor(Executors.newVirtualThreadPerTaskExecutor())
                .withdraw(request, observer);
        observer.await();
    }

}
