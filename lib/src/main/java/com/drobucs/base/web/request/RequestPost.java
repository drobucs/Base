package com.drobucs.base.web.request;

import okhttp3.FormBody;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("unused")
public class RequestPost extends Request {
    protected final Map<String, String> params;

    public RequestPost(@NotNull String url) {
        super(url);
        params = new HashMap<>();
    }

    public RequestPost setParams(@NotNull String name, @NotNull String value) {
        params.put(name, value);
        return this;
    }

    @Override
    protected okhttp3.Request getRequest() {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return new okhttp3.Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }
}
