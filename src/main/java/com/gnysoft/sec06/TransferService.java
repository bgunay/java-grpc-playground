package com.gnysoft.sec06;

import com.gnysoft.models.sec06.TransferRequest;
import com.gnysoft.models.sec06.TransferResponse;
import com.gnysoft.models.sec06.TransferServiceGrpc;
import com.gnysoft.sec06.requesthandlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }

}
