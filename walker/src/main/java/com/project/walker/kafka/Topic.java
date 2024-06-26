package com.project.walker.kafka;


public enum Topic {
    RESERVE("dogWalker_reserve"),
    NOTICE("dogWalker_notice"),
    ;

    public final String groupName;

    Topic(final String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
