package com.github.egoettelmann.sonar.codefreshness.core;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class CodeFreshnessComputer {

    private final int basePeriod;

    private final float growthFactor;

    private final long referenceTimestamp;

    /**
     * Instantiates a CodeFreshness computer.
     *
     * @param basePeriod the base period (in days)
     * @param growthFactor the growth factor between each period defining the rank
     * @param referenceTimestamp the reference timestamp (e.g. {@link Instant#now()})
     */
    public CodeFreshnessComputer(int basePeriod, float growthFactor, long referenceTimestamp) {
        this.basePeriod = basePeriod;
        this.growthFactor = growthFactor;
        this.referenceTimestamp = referenceTimestamp;
    }

    /**
     * Gets the age of a timestamp in days.
     *
     * @param timestampInMillis timestamp in milliseconds
     * @return the age in days
     */
    public Integer getAgeInDays(long timestampInMillis) {
        LocalDate today = Instant.ofEpochMilli(this.referenceTimestamp)
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
     * @return the rank
     */
    public Integer getRank(long ageInDays) {
        // Calculating the value using our magic formula
        double x = (double)ageInDays / this.basePeriod;
        double value = MathUtils.log(x, this.growthFactor) + 1;
        int rank = (int) Math.ceil(value);
        // Negative values are of rank 1
        if (rank <= 0) {
            return 1;
        }
        // Values above 5 are of rank 5
        return Math.min(rank, 5);
    }

}
