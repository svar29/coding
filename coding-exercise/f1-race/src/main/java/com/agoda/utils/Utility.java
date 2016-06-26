package com.agoda.utils;

public class Utility {

    // s = ut + 0.5at^2
    public static Double calculateDisplacementAfterInterval(Double speed, Double acceleration, Double interval) {
        return speed * interval + 0.5 * acceleration * interval * interval;
    }

    // v = u + at
    public static Double getSpeedAfterInterval(Double initialSpeed, Double acceleration, Double interval) {
        return initialSpeed + acceleration * interval;
    }

    // t = (v-u) / a
    public static Double calculateTimeInWhichTopSpeedIsAttained(Double topSpeed, Double initialSpeed, Double acceleration) {
        return (topSpeed - initialSpeed) / acceleration;
    }

    // d = s * t
    public static Double calculateDistance(Double speed, Double time) {
        return speed * time;
    }

}
