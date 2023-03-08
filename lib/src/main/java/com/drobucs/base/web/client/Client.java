package com.drobucs.base.web.client;

import okhttp3.OkHttpClient;

public class Client {
    private static OkHttpClient client;
    public synchronized static OkHttpClient getInstance() {
        if (client == null) {
            return client = new OkHttpClient();
        }
        return client;
    }
}
