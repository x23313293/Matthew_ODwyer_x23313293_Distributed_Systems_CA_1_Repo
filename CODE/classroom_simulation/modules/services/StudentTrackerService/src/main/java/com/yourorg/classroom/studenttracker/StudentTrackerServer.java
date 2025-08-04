package com.yourorg.classroom.studenttracker;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class StudentTrackerServer {
    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerServer.class);
    private final int port = 50051; // Port on which the server will listen
    private final Server server;

    public StudentTrackerServer() {
        // Initialize the server with the service implementation
        server = ServerBuilder.forPort(port)
                .addService(new StudentTrackerServiceImpl())
                .build();
    }

    public void start() throws IOException {
        // Start the server
        server.start();
        logger.info("Server started, listening on " + port);

        // Add a shutdown hook to gracefully stop the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            StudentTrackerServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final StudentTrackerServer server = new StudentTrackerServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
