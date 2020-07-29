package com.mhp.coding.challenges.retry.core.entities;

import org.springframework.retry.*;
import org.springframework.retry.listener.RetryListenerSupport;

import java.util.logging.Logger;

public class RetryOperations extends RetryListenerSupport {

    private static final Logger log = Logger.getLogger(RetryOperations.class.getName());

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T,E> callback, Throwable throwable){
        log.info("onClose");

        super.close(context, callback, throwable);
    }

    @Override
    public <T,E extends Throwable> void onError(RetryContext context, RetryCallback<T,E> callback, Throwable throwable) {
        log.info("onError");

        super.onError(context, callback, throwable);
    }

    @Override
    public <T,E extends Throwable> boolean open (RetryContext context, RetryCallback<T,E> callback) {
        log.info("onOpen");

        return super.open(context, callback);
    }



}
