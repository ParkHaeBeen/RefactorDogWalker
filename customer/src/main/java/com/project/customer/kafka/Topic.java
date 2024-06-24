package com.project.customer.kafka;

public enum Topic {
    RESERVE("reserve_request");

    Topic(final String name) {
        this.name = name;
    }

    private String name;
}
