package com.example.kvana.retrofitexample.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kvana on 5/10/17.
 */

public interface Contacts {

    @GET("/contacts")
    Call<String> getDetails();
}
