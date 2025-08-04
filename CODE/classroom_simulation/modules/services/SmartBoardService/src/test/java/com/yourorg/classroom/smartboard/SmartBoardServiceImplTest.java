package com.yourorg.classroom.smartboard;

import com.yourorg.classroom.proto.SmartBoardServiceGrpc;
import com.yourorg.classroom.proto.SmartBoardServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SmartBoardServiceImplTest {

    private SmartBoardServiceImpl service;
    private ManagedChannel channel;
    private SmartBoardServiceGrpc.SmartBoardServiceBlockingStub blockingStub;
    private SmartBoardServiceGrpc.SmartBoardServiceStub asyncStub;

    @BeforeEach
    void setUp() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        service = new SmartBoardServiceImpl();

        // Set up in-process server
        InProcessServerBuilder.forName(serverName)
                .directExecutor()
                .addService(service)
                .build()
                .start();

        // Set up in-process channel
        channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();

        blockingStub = SmartBoardServiceGrpc.newBlockingStub(channel);
        asyncStub = SmartBoardServiceGrpc.newStub(channel);
    }

    @Test
    void testTurnOn() {
        SmartBoardServiceOuterClass.TurnOnRequest request = SmartBoardServiceOuterClass.TurnOnRequest.newBuilder()
                .setDeviceId("board123")
                .build();

        StreamObserver<SmartBoardServiceOuterClass.TurnOnResponse> responseObserver = mock(StreamObserver.class);
        service.turnOn(request, responseObserver);

        verify(responseObserver, times(1)).onNext(any(SmartBoardServiceOuterClass.TurnOnResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }

    @Test
    void testTurnOff() {
        SmartBoardServiceOuterClass.TurnOffRequest request = SmartBoardServiceOuterClass.TurnOffRequest.newBuilder()
                .setDeviceId("board123")
                .build();

        StreamObserver<SmartBoardServiceOuterClass.TurnOffResponse> responseObserver = mock(StreamObserver.class);
        service.turnOff(request, responseObserver);

        verify(responseObserver, times(1)).onNext(any(SmartBoardServiceOuterClass.TurnOffResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }

    @Test
    void testAdjustVolume() {
        SmartBoardServiceOuterClass.AdjustVolumeRequest request = SmartBoardServiceOuterClass.AdjustVolumeRequest.newBuilder()
                .setDeviceId("board123")
                .setVolumeLevel(75)
                .build();

        StreamObserver<SmartBoardServiceOuterClass.AdjustVolumeResponse> responseObserver = mock(StreamObserver.class);
        service.adjustVolume(request, responseObserver);

        verify(responseObserver, times(1)).onNext(any(SmartBoardServiceOuterClass.AdjustVolumeResponse.class));
        verify(responseObserver, times(1)).onCompleted();
    }

    @Test
    void testGetStatus() {
        SmartBoardServiceOuterClass.GetStatusRequest request = SmartBoardServiceOuterClass.GetStatusRequest.newBuilder()
                .setDeviceId("board123")
                .build();

        StreamObserver<SmartBoardServiceOuterClass.GetStatusResponse> responseObserver = mock(StreamObserver.class);
        service.getStatus(request, responseObse
