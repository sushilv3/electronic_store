package com.sushil.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException() {
        super("Bad Requests");
    }
}
