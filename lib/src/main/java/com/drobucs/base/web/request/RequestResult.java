package com.drobucs.base.web.request;

import com.drobucs.base.error.Error;
import org.jetbrains.annotations.NotNull;
@SuppressWarnings("unused")
public class RequestResult<T> {

    private final T result;
    private final Error error;
    private final String message;


    public RequestResult(T result, @NotNull Error error, @NotNull String message) {
        this.result = result;
        this.error = error;
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public boolean haveErrors() {
        return error != Error.NO_ERRORS;
    }
}
