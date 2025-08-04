package com.yourorg.classroom.smartboard;

import com.yourorg.classroom.proto.SmartBoardServiceGrpc;
import com.yourorg.classroom.proto.SmartBoardServiceOuterClass;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartBoardServiceImpl extends SmartBoardServiceGrpc.SmartBoardServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SmartBoardServiceImpl.class);

    @Override
    public void turnOn(SmartBoardServiceOuterClass.TurnOnRequest request,
                       StreamObserver<SmartBoardServiceOuterClass.TurnOnResponse> responseObserver) {
        logger.info("Turning on the smart board.");
        SmartBoardServiceOuterClass.TurnOnResponse response = SmartBoardServiceOuterClass.TurnOnResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Smart board is now ON.")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void turnOff(SmartBoardServiceOuterClass.TurnOffRequest request,
                        StreamObserver<SmartBoardServiceOuterClass.TurnOffResponse> responseObserver) {
        logger.info("Turning off the smart board.");
        SmartBoardServiceOuterClass.TurnOffResponse response = SmartBoardServiceOuterClass.TurnOffResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Smart board is now OFF.")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void adjustVolume(SmartBoardServiceOuterClass.AdjustVolumeRequest request,
                             StreamObserver<SmartBoardServiceOuterClass.AdjustVolumeResponse> responseObserver) {
        logger.info("Adjusting volume to level: {}", request.getVolumeLevel());
        SmartBoardServiceOuterClass.AdjustVolumeResponse response = SmartBoardServiceOuterClass.AdjustVolumeResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Volume adjusted successfully.")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStatus(SmartBoardServiceOuterClass.GetStatusRequest request,
                          StreamObserver<SmartBoardServiceOuterClass.GetStatusResponse> responseObserver) {
        logger.info("Fetching smart board status.");
        SmartBoardServiceOuterClass.GetStatusResponse response = SmartBoardServiceOuterClass.GetStatusResponse.newBuilder()
                .setIsOn(true)
                .setVolumeLevel(75)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
