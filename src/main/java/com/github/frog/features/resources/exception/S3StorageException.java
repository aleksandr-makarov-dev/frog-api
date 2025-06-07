package com.github.frog.features.resources.exception;

public class S3StorageException extends RuntimeException {
    public S3StorageException(Throwable e) {
        super(e);
    }
}
