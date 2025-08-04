package com.yourorg.classroom.discovery;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@Configuration
@EnableEurekaServer
public class ServiceRegistry {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistry.class, args);
    }
}
