package com.example.kvana.retrofitexample.network;

import com.example.kvana.retrofitexample.model.Contact;
import com.example.kvana.retrofitexample.util.StringConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kvana on 5/10/17.
 */

public class RetrofitHandler {

    private static RetrofitHandler ourInstance = new RetrofitHandler();

    public static RetrofitHandler getInstance() {
        return ourInstance;
    }

    private Retrofit ipApiService = new Retrofit.Builder()
            .baseUrl("http://api.androidhive.info")
            .addConverterFactory(StringConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Contacts service = ipApiService.create(Contacts.class);

    private RetrofitHandler() {
    }

    public Call<String> getContactList() {
        return service.getDetails();
    }
}
