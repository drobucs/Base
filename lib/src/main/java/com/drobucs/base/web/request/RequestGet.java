package com.drobucs.base.web.request;
import org.jetbrains.annotations.NotNull;
@SuppressWarnings("unused")
public class RequestGet extends Request {

    public RequestGet(@NotNull String url) {
        super(url);
    }

    @Override
    protected okhttp3.Request getRequest() {
        return  new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
