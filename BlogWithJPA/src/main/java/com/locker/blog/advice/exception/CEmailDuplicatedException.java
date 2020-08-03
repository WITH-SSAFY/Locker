package com.locker.blog.advice.exception;

public class CEmailDuplicatedException extends RuntimeException {
    public CEmailDuplicatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CEmailDuplicatedException(String msg) {
        super(msg);
    }

    public CEmailDuplicatedException() {
        super();
    }
}