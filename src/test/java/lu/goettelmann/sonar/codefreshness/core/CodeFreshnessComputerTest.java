package lu.goettelmann.sonar.codefreshness.core;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Function;

public class CodeFreshnessComputerTest {

    @Test
    public void testAgeInDays() {
        // Helper method to convert readable LocalDate to timestamp
        Function<LocalDate, Long> toTimestamp = (date) -> date.atStartOfDay()
                .atZone(ZoneId.of("UTC"))
                .toInstant()
                .toEpochMilli();

        long referenceTimestamp = toTimestamp.apply(LocalDate.of(2021, 3, 15));
        CodeFreshnessComputer computer = new CodeFreshnessComputer(0, 0, referenceTimestamp);

        Long value0 = toTimestamp.apply(LocalDate.of(2021, 3, 15));
        Assert.assertEquals(Integer.valueOf(0), computer.getAgeInDays(value0));

        Long value1 = toTimestamp.apply(LocalDate.of(2021, 3, 1));
        Assert.assertEquals(Integer.valueOf(14), computer.getAgeInDays(value1));

        Long value2 = toTimestamp.apply(LocalDate.of(2021, 1, 1));
        Assert.assertEquals(Integer.valueOf(73), computer.getAgeInDays(value2));

        Long value3 = toTimestamp.apply(LocalDate.of(2020, 3, 15));
        Assert.assertEquals(Integer.valueOf(365), computer.getAgeInDays(value3));

        Long valueNegative = toTimestamp.apply(LocalDate.of(2021, 4, 1));
        Assert.assertEquals(Integer.valueOf(-17), computer.getAgeInDays(valueNegative));
    }

    @Test
    public void testRankWithBasePeriod30AndGrowthFactor2() {
        int basePeriod = 30; // 1 month
        float growthFactor = 2;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(30));
        // 1 month
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(31));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(60));
        // 2 months
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(61));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(120));
        // 4 months
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(121));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(240));
        // 8 months
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(241));
    }

    @Test
    public void testRankWithBasePeriod90AndGrowthFactor2() {
        int basePeriod = 90; // 3 months
        float growthFactor = 2;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(90));
        // 3 months
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(91));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(180));
        // 6 months
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(181));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(360));
        // 1 year
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(361));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(720));
        // 2 years
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(721));
    }

    @Test
    public void testRankWithBasePeriod180AndGrowthFactor2() {
        int basePeriod = 180; // 6 months
        float growthFactor = 2;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(90));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(180));
        // 6 months
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(181));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(360));
        // 1 year
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(361));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(720));
        // 2 years
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(721));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(1440));
        // 4 years
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(1441));
    }

    @Test
    public void testRankWithBasePeriod30AndGrowthFactor2point5() {
        int basePeriod = 30; // 1 month
        float growthFactor = 2.5f;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(30));
        // 1 month
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(31));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(75));
        // 2.5 months
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(76));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(187));
        // ~ 6 months
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(188));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(468));
        // ~ 15 months
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(469));
    }

    @Test
    public void testRankWithBasePeriod90AndGrowthFactor2point5() {
        int basePeriod = 90; // 3 months
        float growthFactor = 2.5f;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(90));
        // 3 months
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(91));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(225));
        // 7.5 months
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(226));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(562));
        // ~ 18 months
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(563));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(1406));
        // ~ 4 years
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(1407));
    }

    @Test
    public void testRankWithBasePeriod180AndGrowthFactor2point5() {
        int basePeriod = 180; // 6 months
        float growthFactor = 2.5f;
        CodeFreshnessComputer computer = new CodeFreshnessComputer(basePeriod, growthFactor, System.currentTimeMillis());

        Assert.assertEquals(Integer.valueOf(1), computer.getRank(10));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(90));
        Assert.assertEquals(Integer.valueOf(1), computer.getRank(180));
        // 6 months
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(181));
        Assert.assertEquals(Integer.valueOf(2), computer.getRank(450));
        // 15 months
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(451));
        Assert.assertEquals(Integer.valueOf(3), computer.getRank(1125));
        // ~ 3 years
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(1126));
        Assert.assertEquals(Integer.valueOf(4), computer.getRank(2812));
        // ~ 8 years
        Assert.assertEquals(Integer.valueOf(5), computer.getRank(2813));
    }

}
