package com.agoda.service;

import com.agoda.utils.Utility;
import com.agoda.model.F1Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class F1Race
{
	private static final Logger LOGGER = LoggerFactory.getLogger(F1Race.class);

    // TODO these constants can be taken from config
    private static final Double HF = 0.8;
    private static final Double MIN_NEXT_CAR_DIFF = 10.0;

    private Double currentRaceDuration = 0.0;

	private Double lengthOfTrack;
	private List<F1Car> f1CarsInRace = new ArrayList<F1Car>();;
	private List<F1Car> f1CarsFinishedRace = new ArrayList<F1Car>();;


	public F1Race(Integer numberOfCars, Double lengthOfTrack) {
        this(getCarsListFromNumberOfCars(numberOfCars), lengthOfTrack);
    }

    public F1Race(List<F1Car> f1CarsInRace, Double lengthOfTrack) {
        this.f1CarsInRace = f1CarsInRace;
        this.lengthOfTrack = lengthOfTrack;
    }

    private static List<F1Car> getCarsListFromNumberOfCars(Integer numberOfCars) {
        Double positionOfLastCar = 0.0;
        List<F1Car> cars = new ArrayList<F1Car>();
        for (int i = 1; i <= numberOfCars; i++)
        {
            //TODO All these constants can be taken from config
            Double positionOfCurrentCar = positionOfLastCar - 200.0 * i;
            Double currentSpeed = 0.0;
            // converting km/hr to m/sec by multiplying by 9/18
            Double topSpeed = ((150.0 + 10.0 * i) * 5.0) / 18.0;
            Double acceleration  = 2.0 * i;
            F1Car car = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);

            cars.add(car);
            positionOfLastCar = positionOfCurrentCar;
        }
        return cars;
    }
    // entry point for race re assessment after every interval
    public void reAssessment(List<F1Car> f1CarsInRace, List<F1Car> f1CarsFinishedRace, Double interval)
	{
        // increase the race duration by interval
		this.currentRaceDuration = this.currentRaceDuration + interval;
		// evaluate the positions of car after interval based on speed, acceleration
        refreshCarPositionAfterInterval(f1CarsInRace, f1CarsFinishedRace, this.currentRaceDuration, interval);
        if (!f1CarsInRace.isEmpty()) {
            Collections.sort(f1CarsInRace, F1Car.sortByCurrentPosition);
            reduceSpeedIfCarNearBy(f1CarsInRace);
            useNitro(f1CarsInRace.get(0));
        }

	}

    public void refreshCarPositionAfterInterval(List<F1Car> f1CarsInRace, List<F1Car> f1CarsFinishedRace,
                                                Double currentRaceDuration, Double interval)
	{
		Iterator<F1Car> it = f1CarsInRace.iterator();
		while (it.hasNext())
		{
			F1Car car = it.next();
            // calculate the speed after interval
            Double speedAfterInterval = Utility.getSpeedAfterInterval(car.getCurrentSpeed(),
					car.getAcceleration(), interval);
            Double totalDisplacement;

			if (speedAfterInterval > car.getTopSpeed()) {
                // this means car must have attained top speed in middle of interval
				speedAfterInterval = car.getTopSpeed();
                // calculate the time in which max speed was attained as the displacement will be calculated differently
				Double timeInWhichMaxSpeedIsAttained = Utility.calculateTimeInWhichTopSpeedIsAttained(
						car.getTopSpeed(), car.getCurrentSpeed(), car.getAcceleration());
                //calculate displacement based on accelaration for above interval
                totalDisplacement = Utility.calculateDisplacementAfterInterval(car.getCurrentSpeed(),
						car.getAcceleration(), timeInWhichMaxSpeedIsAttained)
                        // constant speed for rest of the time
                        + Utility.calculateDistance(car.getTopSpeed(), interval - timeInWhichMaxSpeedIsAttained);
			} else {
                // uniform accelaration for full interval
				totalDisplacement = Utility.calculateDisplacementAfterInterval(
                        car.getCurrentSpeed(), car.getAcceleration(), interval);
			}
            // update the new car speed and position
            car.setCurrentSpeed(speedAfterInterval);
			car.setCurrentPosition(car.getCurrentPosition() + totalDisplacement);

            // remove car from track if reached finished line
            if (car.getCurrentPosition() > getLengthOfTrack()) {
				car.setFinishTiming(currentRaceDuration);
				// add this car to finished cars list
                f1CarsFinishedRace.add(car);
				it.remove();
			}
		}
	}

	public void reduceSpeedIfCarNearBy(List<F1Car> f1CarsInRace)
	{
		for (int i = 0; i < f1CarsInRace.size(); i++)
		{
			Double diffToNextCar = Double.POSITIVE_INFINITY;
			Double diffToPrevCar = Double.POSITIVE_INFINITY;

            // if it is not the last car
            if (i < f1CarsInRace.size() - 1) {
                diffToNextCar = f1CarsInRace.get(i).getCurrentPosition()
                        - f1CarsInRace.get(i + 1).getCurrentPosition();
            }
            // if it is not the first car
            if (i > 0) {
                diffToPrevCar = f1CarsInRace.get(i - 1).getCurrentPosition()
                        - f1CarsInRace.get(i).getCurrentPosition();
            }
            // if car within MIN_NEXT_CAR_DIFF of either front or back car, reduce the speed by HF
			if (Math.abs(diffToNextCar) <= MIN_NEXT_CAR_DIFF
					|| Math.abs(diffToPrevCar) <= MIN_NEXT_CAR_DIFF)
			{
				f1CarsInRace.get(i).setCurrentSpeed(HF * f1CarsInRace.get(i).getCurrentSpeed());
			}
		}
	}

    public void useNitro(F1Car car)
	{
        if (!car.getNitroUsed())
		{
            LOGGER.info("Using nitro for car" + car.getId());
            Double speedAfterNitro = car.getCurrentSpeed() * 2;
			// don't exceed car's top speed
            if (speedAfterNitro > car.getTopSpeed())
			{
				speedAfterNitro = car.getTopSpeed();
			}
			car.setCurrentSpeed(speedAfterNitro);
            car.setIsNitroUsed(true);
		}
	}

    public void printRaceOutCome()
    {
        for (F1Car car : f1CarsFinishedRace)
        {
            LOGGER.info(" Car {} : Finish Speed {} , Finish Time {} , At Position {} \n", car.getId(),
                    car.getCurrentSpeed(), car.getFinishTiming(), car.getCurrentPosition());
        }
    }

	private Double getLengthOfTrack()
	{
		return lengthOfTrack;
	}

    public List<F1Car> getF1CarsInRace() {
        return f1CarsInRace;
    }

    public List<F1Car> getF1CarsFinishedRace() {
        return f1CarsFinishedRace;
    }

    public Double getCurrentRaceDuration() {
        return currentRaceDuration;
    }
}
