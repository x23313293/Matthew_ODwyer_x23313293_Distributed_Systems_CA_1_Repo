package com.yourorg.classroom.environmentcontrol;

import com.yourorg.classroom.proto.EnvironmentControlGrpc;
import com.yourorg.classroom.proto.EnvironmentControlOuterClass;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class EnvironmentControlServiceImplTest {

    private EnvironmentControlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EnvironmentControlServiceImpl();
    }

    @Test
    void testSetTemperature() {
        // Arrange
        EnvironmentControlOuterClass.SetTemperatureRequest request = 
            EnvironmentControlOuterClass.SetTemperatureRequest.newBuilder()
                .setTemperature(22.5)
                .build();
        StreamObserver<EnvironmentControlOuterClass.SetTemperatureResponse> responseObserver = mock(StreamObserver.class);

        // Act
        service.setTemperature(request, responseObserver);

        // Assert
        verify(responseObserver, times(1)).onNext(any(EnvironmentControlOuterClass.SetTemperatureResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }

    @Test
    void testSetHumidity() {
        // Arrange
        EnvironmentControlOuterClass.SetHumidityRequest request = 
            EnvironmentControlOuterClass.SetHumidityRequest.newBuilder()
                .setHumidity(60)
                .build();
        StreamObserver<EnvironmentControlOuterClass.SetHumidityResponse> responseObserver = mock(StreamObserver.class);

        // Act
        service.setHumidity(request, responseObserver);

        // Assert
        verify(responseObserver, times(1)).onNext(any(EnvironmentControlOuterClass.SetHumidityResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }

    @Test
    void testGetEnvironmentStatus() {
        // Arrange
        EnvironmentControlOuterClass.GetEnvironmentStatusRequest request = 
            EnvironmentControlOuterClass.GetEnvironmentStatusRequest.newBuilder().build();
        StreamObserver<EnvironmentControlOuterClass.GetEnvironmentStatusResponse> responseObserver = mock(StreamObserver.class);

        // Act
        service.getEnvironmentStatus(request, responseObserver);

        // Assert
        verify(responseObserver, times(1)).onNext(any(EnvironmentControlOuterClass.GetEnvironmentStatusResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }
}
