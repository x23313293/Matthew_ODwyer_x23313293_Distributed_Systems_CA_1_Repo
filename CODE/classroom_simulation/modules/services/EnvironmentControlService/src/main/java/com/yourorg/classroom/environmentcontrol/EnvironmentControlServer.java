package com.yourorg.classroom.environmentcontrol;

import com.yourorg.classroom.proto.EnvironmentControlGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EnvironmentControlServer {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentControlServer.class);
    private static final int PORT = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create and start the server
        Server server = ServerBuilder.forPort(PORT)
                .addService(new EnvironmentControlServiceImpl())
                .addService(ProtoReflectionService.newInstance()) // Optional: for server reflection
                .build()
                .start();

        logger.info("Server started, listening on {}", PORT);

        // Add a shutdown hook to gracefully stop the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            System.err.println("Server shut down");
        }));

        // Block the main thread until the server is terminated
        server.awaitTermination();
    }
}
