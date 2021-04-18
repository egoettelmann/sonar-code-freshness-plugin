package lu.goettelmann.sonar.codefreshness.core;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class ComputationUtils {

    private ComputationUtils() {
        // No constructor for utils class
    }

    /**
     * Gets the age of a timestamp in days.
     *
     * This method uses {@link Instant#now()}, which is probably correct when using days.
     * For more fine grained periods, it may be better to use a common value at analysis level.
     *
     * @param timestampInMillis timestamp in milliseconds
     * @return the age in days
     */
    public static Integer getAgeInDays(long timestampInMillis) {
        LocalDate today = Instant.now()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate date = Instant.ofEpochMilli(timestampInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return ((int) ChronoUnit.DAYS.between(date, today));
    }

    /**
     * Calculates the CodeFreshness rank.
     *
     * The rank corresponds to a period during which the code was updated the last time.
     * The higher the rank (older the code is), the longer the period.
     * The base period defines the duration of the first period (rank A).
     * The growth factor defines how much longer a period is in comparison to the previous one.
     *
     * @param ageInDays the age in days of the code
     * @param basePeriod the base period (in days)
     * @param growthFactor the growth factor between each period defining the rank
     * @return the rank
     */
    public static Integer getRank(long ageInDays, int basePeriod, int growthFactor) {
        // Calculating the value using our magic formula
        double value = logN(ageInDays / basePeriod, growthFactor) + 1;
        int rank = (int) Math.ceil(value);
        // Negative values are of rank 1
        if (rank <= 0) {
            return 1;
        }
        // Values above 5 are of rank 5
        return Math.min(rank, 5);
    }

    /**
     * Calculates the log in base N.
     *
     * @param x the values
     * @param n the base
     * @return the logN of x
     */
    public static double logN(long x, int n) {
        // Calculate logN indirectly using log() method
        return Math.log(x) / Math.log(n);
    }

}
