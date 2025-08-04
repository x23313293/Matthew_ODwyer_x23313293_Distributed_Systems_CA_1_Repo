package com.yourorg.classroom.environmentcontrol;

import com.yourorg.classroom.proto.EnvironmentControlGrpc;
import com.yourorg.classroom.proto.EnvironmentControlOuterClass;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentControlServiceImpl extends EnvironmentControlGrpc.EnvironmentControlImplBase {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentControlServiceImpl.class);

    @Override
    public void setTemperature(EnvironmentControlOuterClass.SetTemperatureRequest request,
                               StreamObserver<EnvironmentControlOuterClass.SetTemperatureResponse> responseObserver) {
        // Implement the logic to set the temperature
        logger.info("Setting temperature to: {}°C", request.getTemperature());

        EnvironmentControlOuterClass.SetTemperatureResponse response = EnvironmentControlOuterClass.SetTemperatureResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void setHumidity(EnvironmentControlOuterClass.SetHumidityRequest request,
                             StreamObserver<EnvironmentControlOuterClass.SetHumidityResponse> responseObserver) {
        // Implement the logic to set the humidity
        logger.info("Setting humidity to: {}%", request.getHumidity());

        EnvironmentControlOuterClass.SetHumidityResponse response = EnvironmentControlOuterClass.SetHumidityResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEnvironmentStatus(EnvironmentControlOuterClass.GetEnvironmentStatusRequest request,
                                      StreamObserver<EnvironmentControlOuterClass.GetEnvironmentStatusResponse> responseObserver) {
        // Implement the logic to get the current environment status
        logger.info("Fetching current environment status");

        EnvironmentControlOuterClass.GetEnvironmentStatusResponse response = EnvironmentControlOuterClass.GetEnvironmentStatusResponse.newBuilder()
                .setTemperature(22.5)
                .setHumidity(60)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
