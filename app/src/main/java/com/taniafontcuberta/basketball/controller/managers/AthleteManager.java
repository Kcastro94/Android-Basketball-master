package com.taniafontcuberta.basketball.controller.managers;

import android.util.Log;

import com.taniafontcuberta.basketball.controller.services.AthleteService;
import com.taniafontcuberta.basketball.controller.services.PlayerService;
import com.taniafontcuberta.basketball.model.Athlete;
import com.taniafontcuberta.basketball.model.Player;
import com.taniafontcuberta.basketball.util.CustomProperties;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DAM on 8/3/17.
 */

public class AthleteManager {

    private static AthleteManager ourInstance;
    private List<Athlete> athletes;
    private Retrofit retrofit;
    private AthleteService athleteService;

    private AthleteManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        athleteService = retrofit.create(AthleteService.class);
    }

    public static AthleteManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new AthleteManager();
        }

        return ourInstance;
    }

    /* GET - GET ALL PLAYER */

    public synchronized void getAllAthletes(final AthleteCallback athleteCallback) {
        Call<List<Athlete>> call = athleteService.getAllAthletes(UserLoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<List<Athlete>>() {
            @Override
            public void onResponse(Call<List<Athlete>> call, Response<List<Athlete>> response) {
                athletes = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    athleteCallback.onSuccess(athletes);
                } else {
                    athleteCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Athlete>> call, Throwable t) {
                Log.e("AthleteManager->", "getAllAthletes->ERROR: " + t);

                athleteCallback.onFailure(t);
            }
        });
    }

    /* POST - CREATE PLAYER */

    public synchronized void createAthlete(final AthleteCallback athleteCallback,Athlete athlete) {
        Call<Athlete> call = athleteService.createAthlete(UserLoginManager.getInstance().getBearerToken(), athlete);
        call.enqueue(new Callback<Athlete>() {
            @Override
            public void onResponse(Call<Athlete> call, Response<Athlete> response) {
                int code = response.code();

                if (code == 200 || code == 201) {
                    //playerCallback.onSuccess1(apuestas1x2);
                    Log.e("Athlete->", "createAthlete: OK" + 100);

                } else {
                    athleteCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Athlete> call, Throwable t) {
                Log.e("AthleteManager->", "createManager: " + t);

                athleteCallback.onFailure(t);
            }
        });
    }

    /* PUT - UPDATE PLAYER */
    public synchronized void updateAthlete(final AthleteCallback athleteCallback, Athlete athlete) {
        Call <Athlete> call = athleteService.updateAthlete(UserLoginManager.getInstance().getBearerToken() ,athlete);
        call.enqueue(new Callback<Athlete>() {
            @Override
            public void onResponse(Call<Athlete> call, Response<Athlete> response) {
                int code = response.code();

                if (code == 200 || code == 201) {
                    Log.e("Athlete->", "updateAthlete: OOK" + 100);

                } else {
                    athleteCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Athlete> call, Throwable t) {
                Log.e("AthleteManager->", "updateAthlete: " + t);

                athleteCallback.onFailure(t);
            }
        });
    }

    /* DELETE - DELETE PLAYER */
    public synchronized void deleteAthlete(final AthleteCallback athleteCallback, Long id) {
        Call <Void> call = athleteService.deleteAthlete(UserLoginManager.getInstance().getBearerToken() ,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();

                if (code == 200 || code == 201) {
                    Log.e("Athlete->", "Deleted: OK");

                } else {
                    athleteCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AthleteManager->", "deleteAthlete: " + t);

                athleteCallback.onFailure(t);
            }
        });
    }
}
