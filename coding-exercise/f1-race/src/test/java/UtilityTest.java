import com.agoda.utils.Utility;
import com.agoda.service.F1RaceService;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    @Test
    public void calculateDisplacementInNSeconds() throws Exception {
        F1RaceService race = new F1RaceService(1, 700.0);
        Double u = 44.0, a = 2.0, t = 2.0;
        Double displacement = Utility.calculateDisplacementAfterInterval(u, a, t);
        //v = ut + 1.2at^2
        assertEquals(displacement, ((Double) (u * t + 0.5 * a * t * t)));
    }
    @Test
    public void getSpeedAfterNSeconds() throws Exception {
        F1RaceService race = new F1RaceService(1, 700.0);
        Double u = 44.0, a = 2.0, t = 2.0;
        Double speed = Utility.getSpeedAfterInterval(u, a, t);
        //v = u + at
        assertEquals(speed, ((Double) (u + a * t)));
    }

    @Test
    public void calculateTimeInWhichTopSpeedIsAttained() throws Exception {
        F1RaceService race = new F1RaceService(1, 700.0);
        Double v = 44.0, u = 2.0, t = 2.0;
        Double time = Utility.calculateTimeInWhichTopSpeedIsAttained(v, u, t);
        //v = u + at
        //t = (v - u) / a
        assertEquals(time, ((Double) ((v - u) / t)));

    }


}