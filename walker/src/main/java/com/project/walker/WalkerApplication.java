package com.project.walker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan(basePackages = {"com.project.core"})
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.project"})
public class WalkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalkerApplication.class, args);
    }
}
