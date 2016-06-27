package com.agoda;

import com.agoda.service.F1RaceService;

public class F1Race {
    public static void main(String[] args) {
        F1RaceService race = new F1RaceService(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
        while(!race.getF1CarsInRace().isEmpty())
        {
            race.reAssessment(race.getF1CarsInRace(), race.getF1CarsFinishedRace(), 2.0);

        }
        race.printRaceOutCome();
    }
}
