package com.daitangroup.daitanuniversityandroid.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory class to create Retrofit services.
 */
public class RetrofitFactory {

    private static final String END_POINT = "https://api.github.com/";

    /**
     * Creates a Retrofit service from an arbitrary class (clazz)
     *
     * @param clazz Java interface of the Retrofit service
     * @return a Retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }
}