package com.makan.app.web;

import com.makan.app.app.WebConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connector {

    private static Connector sConnector;
    private static final String API_KEY_DEVICE_ID ="DeviceId";

    private Connector() {

    }

    public static Connector getInstance() {

        if (sConnector == null) {
            sConnector = new Connector();
        }

        return sConnector;
    }

    public retrofit2.Retrofit getConnector() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        Request request = chain.request().newBuilder().build();
                        return chain.proceed(request);

                    }
                })
                .build();
        return new Retrofit.Builder()
                .baseUrl(WebConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }


    public void clearConnection() {
        sConnector = null;
    }

}
