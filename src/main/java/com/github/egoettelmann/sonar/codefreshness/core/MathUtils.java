package com.github.egoettelmann.sonar.codefreshness.core;

public class MathUtils {

    private MathUtils() {
        // Utils classes cannot be instantiated
    }

    /**
     * Calculates the log for a given base.
     *
     * @param x the values
     * @param n the base
     * @return the logN of x
     */
    public static double log(double x, float n) {
        // Calculate logN indirectly using log() method
        return Math.log(x) / Math.log(n);
    }

}
