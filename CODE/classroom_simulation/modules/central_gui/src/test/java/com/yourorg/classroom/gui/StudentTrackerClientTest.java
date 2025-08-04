package com.yourorg.classroom.gui;

import com.yourorg.classroom.proto.StudentTrackerServiceGrpc;
import com.yourorg.classroom.proto.StudentTrackerServiceOuterClass.*;
import com.yourorg.classroom.studenttracker.StudentTrackerServiceImpl;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentTrackerClientTest {

    private static final String SERVER_NAME = InProcessServerBuilder.generateName();
    private static Server inProcessServer;
    private static ManagedChannel channel;
    private static StudentTrackerClient client;

    @BeforeAll
    static void setup() throws Exception {
        // Start a lightweight in-process server with your real service implementation
        inProcessServer = InProcessServerBuilder
            .forName(SERVER_NAME)
            .directExecutor()
            .addService(new StudentTrackerServiceImpl())
            .build()
            .start();

        channel = InProcessChannelBuilder
            .forName(SERVER_NAME)
            .directExecutor()
            .build();

        // Construct your client and inject the stub
        client = new StudentTrackerClient();
        client.setStub(StudentTrackerServiceGrpc.newBlockingStub(channel));
    }

    @AfterAll
    static void teardown() {
        channel.shutdownNow();
        inProcessServer.shutdownNow();
    }

    @Test
    void registerStudent_shouldReturnSuccess() {
        StatusResponse resp = client.registerStudent("stu123", "Alice Doe");

        assertNotNull(resp);
        assertTrue(resp.getMessage().contains("registered"));
    }

    @Test
    void markAttendance_shouldReturnConfirmation() {
        StatusResponse resp = client.markAttendance("stu123", true);

        assertNotNull(resp);
        assertTrue(resp.getMessage().contains("Attendance"));
    }

    @Test
    void getEngagementReport_shouldReturnEnrollmentReport() {
        EnrollmentReport report = client.getEngagementReport("stu123");

        assertNotNull(report);
        // At this point, since no users are being tracked, entries list may be empty
        assertNotNull(report.getEntriesList());
    }
}
