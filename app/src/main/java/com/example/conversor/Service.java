package com.example.conversor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("{param}")
    Call<String> convertValues(@Path("param") String param);

}
