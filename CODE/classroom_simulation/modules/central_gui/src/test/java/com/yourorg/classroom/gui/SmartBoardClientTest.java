package com.yourorg.classroom.gui;

import com.yourorg.classroom.smartboard.SmartBoardServiceImpl;
import com.yourorg.classroom.proto.SmartBoardServiceGrpc;
import com.yourorg.classroom.proto.SmartBoardServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.Server;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SmartBoardClientTest {

    private static final String UNIQUE_SERVER_NAME = InProcessServerBuilder.generateName();

    private static Server server;
    private static ManagedChannel channel;
    private static SmartBoardClient client;

    @BeforeAll
    static void setUpAll() throws Exception {
        // Start in-process server with your real service implementation
        server = InProcessServerBuilder
                .forName(UNIQUE_SERVER_NAME)
                .directExecutor()
                .addService(new SmartBoardServiceImpl())
                .build()
                .start();

        // Set up channel to talk to the server
        channel = InProcessChannelBuilder
                .forName(UNIQUE_SERVER_NAME)
                .directExecutor()
                .build();

        // Create client pointing to stub backed by this channel
        client = new SmartBoardClient(/* accept ManagedChannel or stub injection */);
        client.setStub(SmartBoardServiceGrpc.newBlockingStub(channel));
    }

    @AfterAll
    static void tearDownAll() {
        if (channel != null) {
            channel.shutdownNow();
        }
        if (server != null) {
            server.shutdownNow();
        }
    }

    @Test
    void turnOn_ShouldReturnSuccessMessage() {
        SmartBoardServiceOuterClass.TurnOnResponse resp = client.turnOn("board42");
        assertTrue(resp.getSuccess());
        assertEquals("Smart board board42 turned on", resp.getMessage());
    }

    @Test
    void turnOff_ShouldReturnSuccessMessage() {
        SmartBoardServiceOuterClass.TurnOffResponse resp = client.turnOff("board42");
        assertTrue(resp.getSuccess());
        assertEquals("Smart board board42 turned off", resp.getMessage());
    }

    @Test
    void adjustVolume_ShouldReturnSuccessMessage() {
        SmartBoardServiceOuterClass.AdjustVolumeResponse resp = client.adjustVolume("board42", 65);
        assertTrue(resp.getSuccess());
        assertEquals("Volume adjusted to 65 for smart board board42", resp.getMessage());
    }

    @Test
    void getStatus_ShouldReturnIsOnAndVolume() {
        SmartBoardServiceOuterClass.GetStatusResponse resp = client.getStatus("board42");
        assertTrue(resp.getIsOn());
        assertEquals(65, resp.getVolumeLevel());  // assuming service updates state
    }
}
