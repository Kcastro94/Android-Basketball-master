package com.taniafontcuberta.basketball.controller.managers;

import com.taniafontcuberta.basketball.model.Athlete;

import java.util.List;

/**
 * Created by DAM on 8/3/17.
 */

public interface AthleteCallback {
    void onSuccess(List<Athlete> athleteList
    );
    void onSucces();

    void onFailure(Throwable t);
}
