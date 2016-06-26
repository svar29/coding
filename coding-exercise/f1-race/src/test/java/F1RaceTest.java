import com.agoda.model.F1Car;
import com.agoda.service.F1Race;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class F1RaceTest {

    @Test
    public void useNitro() throws Exception {
        int i = 1;
        Double positionOfCurrentCar = -200.0;
        Double currentSpeed = 2.0;
        Double topSpeed = 5.0;
        Double acceleration  = 2.0;
        F1Car car = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);
        List<F1Car> f1Cars = new ArrayList<F1Car>();
        f1Cars.add(car);

        F1Race race = new F1Race(f1Cars, 700.0);
        // using nitro makes current speed double
        race.useNitro(car);
        assertEquals(true, car.getNitroUsed());
        assertEquals((Double) 4.0, car.getCurrentSpeed());

        // if using nitro crosses the top speed, current speed remains top speed
        car.setTopSpeed(3.0);
        car.setCurrentSpeed(2.0);
        car.setIsNitroUsed(false);
        race.useNitro(car);
        assertEquals((Double) 3.0, car.getCurrentSpeed());

        // if nitro is already used, current speed remains untouched
        car.setCurrentSpeed(2.0);
        race.useNitro(car);
        assertEquals((Double) 2.0, car.getCurrentSpeed());
    }

    @Test
    public void reduceSpeedIfCarNearBy() throws Exception {
        int i = 1;
        Double positionOfCurrentCar = 200.0;
        Double currentSpeed = 2.0;
        Double topSpeed = 5.0;
        Double acceleration  = 2.0;
        F1Car car1 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);

        i = 2;
        positionOfCurrentCar = 300.0;
        currentSpeed = 3.0;
        topSpeed = 5.0;
        acceleration  = 2.0;
        F1Car car2 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);
        List<F1Car> f1Cars = new ArrayList<F1Car>();
        f1Cars.add(car1);
        f1Cars.add(car2);
        F1Race race = new F1Race(f1Cars, 700.0);

        // if cars distance more than 10, no change in speed
        race.reduceSpeedIfCarNearBy(f1Cars);
        assertEquals((Double)2.0, f1Cars.get(0).getCurrentSpeed());
        assertEquals((Double)3.0, f1Cars.get(1).getCurrentSpeed());

        // reduce speed to 0.8*hf if distance less than 10.0
        car2.setCurrentPosition(205.0);
        race.reduceSpeedIfCarNearBy(f1Cars);
        Double hf = 0.8;
        assertEquals((Double)(2.0 * hf), f1Cars.get(0).getCurrentSpeed());
        assertEquals((Double)(3.0 * hf), f1Cars.get(1).getCurrentSpeed());

    }

    @Test
    public void refreshCarPositionAfterInterval() throws Exception {

        int i = 1;
        Double positionOfCurrentCar = 200.0;
        Double currentSpeed = 2.0;
        Double topSpeed = 50.0;
        Double acceleration  = 2.0;
        F1Car car1 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);

        i = 2;
        positionOfCurrentCar = 300.0;
        currentSpeed = 3.0;
        topSpeed = 50.0;
        acceleration  = 2.0;
        F1Car car2 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);
        List<F1Car> f1Cars = new ArrayList<F1Car>();
        f1Cars.add(car1);
        f1Cars.add(car2);
        F1Race race = new F1Race(f1Cars, 700.0);

        // test happy flow
        List<F1Car> f1CarsFinsihedRace = new ArrayList<F1Car>();
        Double currentRaceDuration = 24.0;
        Double interval = 2.0;
        race.refreshCarPositionAfterInterval(f1Cars, f1CarsFinsihedRace, currentRaceDuration, interval);
        assertEquals((Double) (2.0 + 2.0*2.0), car1.getCurrentSpeed());
        assertEquals((Double) (3.0 + 2.0*2.0), car2.getCurrentSpeed());
        assertEquals((Double) (200.0 + 2.0 * 2.0 + 0.5 * 2.0 * 2.0 * 2.0) , car1.getCurrentPosition());

        // car speed should not exceed top speed
        car1.setTopSpeed(5.0);
        race.refreshCarPositionAfterInterval(f1Cars, f1CarsFinsihedRace, currentRaceDuration, interval);
        assertEquals((Double) 5.0, car1.getCurrentSpeed());
        assertNotEquals((Double) currentRaceDuration, car1.getFinishTiming());
        assertEquals(0, f1CarsFinsihedRace.size());

        // finish the race for car1
        car1.setCurrentPosition(698.0);
        race.refreshCarPositionAfterInterval(f1Cars, f1CarsFinsihedRace, currentRaceDuration, interval);
        assertEquals((Double) currentRaceDuration, car1.getFinishTiming());
        assertEquals(1, f1CarsFinsihedRace.size());

    }

    @Test
    public void reAssessment() throws Exception {
        int i = 1;
        Double positionOfCurrentCar = 200.0;
        Double currentSpeed = 2.0;
        Double topSpeed = 50.0;
        Double acceleration  = 2.0;
        F1Car car1 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);

        i = 2;
        positionOfCurrentCar = 300.0;
        currentSpeed = 3.0;
        topSpeed = 50.0;
        acceleration  = 2.0;
        F1Car car2 = new F1Car(i, currentSpeed, positionOfCurrentCar, topSpeed, acceleration, false);
        List<F1Car> f1Cars = new ArrayList<F1Car>();
        f1Cars.add(car1);
        f1Cars.add(car2);
        F1Race race = new F1Race(f1Cars, 700.0);

        race.reAssessment(race.getF1CarsInRace(), race.getF1CarsFinishedRace(), 2.0);

        // happy case
        assertEquals(0, race.getF1CarsFinishedRace().size());
        assertEquals(2, race.getF1CarsInRace().size());
        assertEquals((Double) 2.0, race.getCurrentRaceDuration());

        //car1 finishes race
        car1.setCurrentPosition(699.0);
        race.reAssessment(race.getF1CarsInRace(), race.getF1CarsFinishedRace(), 2.0);
        assertEquals(1, race.getF1CarsFinishedRace().size());
        assertEquals(false, race.getF1CarsInRace().isEmpty());
        assertEquals((Double) 4.0, race.getCurrentRaceDuration());


        //car2 finishes race
        car2.setCurrentPosition(697.0);
        race.reAssessment(race.getF1CarsInRace(), race.getF1CarsFinishedRace(), 2.0);
        assertEquals(2, race.getF1CarsFinishedRace().size());
        assertEquals(true, race.getF1CarsInRace().isEmpty());
        assertEquals((Double) 6.0, race.getCurrentRaceDuration());


    }

    @Test
    public void simulateF1Race() throws Exception {
        F1Race race = new F1Race(5, 700.0);
        while(!race.getF1CarsInRace().isEmpty())
        {
            race.reAssessment(race.getF1CarsInRace(), race.getF1CarsFinishedRace(), 2.0);

        }
        race.printRaceOutCome();
    }
}