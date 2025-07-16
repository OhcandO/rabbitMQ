package com.koji.rabbitmq1.stream.constant;

/**
 */
public interface MessageState {
    int ENQUEUED = 0;
    int SEND = 1;
    int RECEIVE = 2;
    int COMPLETE = 3;
    int ABORTED = 9;
    int REJECTED = 99;
}
