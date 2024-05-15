package com.example.projectfitlyp4.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApiService {
    @GET("breakfast.php")
        // This should be the endpoint to your API
    Call<List<Food>> getBreakfast();

    @GET("lunch.php")
    Call<List<Food>> getLunch();

    @GET("dinner.php")
    Call<List<Food>> getDinner();
}
