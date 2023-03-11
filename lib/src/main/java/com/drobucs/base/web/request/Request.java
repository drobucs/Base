package com.drobucs.base.web.request;

import com.drobucs.base.error.Error;
import com.drobucs.base.error.ErrorMessage;
import com.drobucs.base.function.FunctionIOE;
import com.drobucs.base.web.client.Client;
import com.drobucs.web.apps.histology.users.RegisterResult;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("unused")
public abstract class Request {
    protected final String url;

    public Request(@NotNull String url) {
        this.url = url;
    }

    private RequestResult<RegisterResult> executeRegisterResult(@NotNull okhttp3.Request request) {
        return execute(
                request,
                body ->   {
                    try {
                        return RequestResult.noErrors(RegisterResult.valueOf(body.string()));
                    } catch (IllegalArgumentException e) {
                        return new RequestResult<>(null, Error.EXCEPTION_ERROR, e.getMessage());
                    }
                }
        );
    }

    private RequestResult<Integer> executeInteger(@NotNull okhttp3.Request request) {
        return execute(
                request,
                body -> handlerNumberFormatException(body, Integer::parseInt)
        );
    }

    private RequestResult<Long> executeLong(@NotNull okhttp3.Request request) {
        return execute(
                request,
                body -> handlerNumberFormatException(body, Long::parseLong)
        );
    }

    private <R> RequestResult<R> handlerNumberFormatException(ResponseBody body, FunctionIOE<String, R> handler)
            throws IOException {
        try {
            return new RequestResult<>(handler.apply(body.string()), Error.NO_ERRORS, ErrorMessage.NO_ERRORS);
        } catch (NumberFormatException e) {
            return new RequestResult<>(null, Error.ERROR,
                    "NumberFormatException: " + e.getMessage());
        }
    }

    @NotNull
    protected <R> RequestResult<R> execute(@NotNull okhttp3.Request request,
                                           FunctionIOE<ResponseBody, RequestResult<R>> handler) {
        OkHttpClient client = Client.getInstance();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new RequestResult<>(null, Error.BAD_RESPONSE,
                        "Response code is " + response.code() + ".");
            }
            ResponseBody body = response.body();
            if (body != null) {
                return handler.apply(body);
            } else {
                return new RequestResult<>(null, Error.NULL_OBJECT_ERROR,
                        "Response body is null.");
            }
        } catch (IOException e) {
            return new RequestResult<>(null, Error.ERROR, "IOException: " + e.getMessage());
        }
    }

    public RequestResult<Integer> executeInteger() {
        return executeInteger(getRequest());
    }

    public RequestResult<Long> executeLong() {
        return executeLong(getRequest());
    }
    public RequestResult<RegisterResult> executeRegisterResult() {
        return executeRegisterResult(getRequest());
    }
    protected abstract okhttp3.Request getRequest();
}
