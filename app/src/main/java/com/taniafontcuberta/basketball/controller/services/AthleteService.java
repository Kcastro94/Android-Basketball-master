package com.taniafontcuberta.basketball.controller.services;

import com.taniafontcuberta.basketball.model.Athlete;
import com.taniafontcuberta.basketball.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by DAM on 8/3/17.
 */

public interface AthleteService {

    @GET("/api/atletas")
    Call<List<Athlete>> getAllAthletes(
            @Header("Authorization") String Authorization
    );

    @POST("api/atletas") // Se tiene que cambiar en un interfaz propia
    Call<Athlete> createAthlete(
            @Header("Authorization") String Authorization,
            @Body Athlete athlete);


    @PUT("api/atletas")
    Call<Athlete> updateAthlete(
            @Header("Authorization") String Authorization,
            @Body Athlete athlete);

    @DELETE("api/atletas/{id}")
    Call<Void> deleteAthlete(
            @Header("Authorization") String Authorization,
            @Path("id") Long id);



}
