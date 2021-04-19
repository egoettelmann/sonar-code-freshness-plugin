package lu.goettelmann.sonar.codefreshness.core;


import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class CodeFreshnessDataBuilder {

    private static final Logger LOGGER = Loggers.get(CodeFreshnessDataBuilder.class);

    private final CodeFreshnessComputer computer;

    private Long avgCommitDate;

    private Integer numLines;

    public CodeFreshnessDataBuilder(CodeFreshnessComputer computer) {
        this.computer = computer;
    }

    public CodeFreshnessDataBuilder add(Long commitDate, Integer numLines) {
        // If no existing data, we simply set it
        if (this.avgCommitDate == null) {
            this.avgCommitDate = commitDate;
            this.numLines = numLines;
            return this;
        }

        // Otherwise we sum the averages (weighted)
        Long existingSum = this.avgCommitDate * this.numLines;
        Long addedSum = commitDate * numLines;
        this.avgCommitDate = (existingSum + addedSum) / (this.numLines + numLines);
        this.numLines += numLines;
        return this;
    }

    public CodeFreshnessData build() {
        if (this.avgCommitDate == null || numLines == null) {
            LOGGER.warn("Missing file data to compute CodeFreshness");
            return null;
        }

        // Build data
        CodeFreshnessData data = new CodeFreshnessData();
        data.setAvgCommitDate(this.avgCommitDate);
        data.setNumLines(this.numLines);
        // Calculating ageInDays
        Integer ageInDays = computer.getAgeInDays(this.avgCommitDate);
        data.setAgeInDays(ageInDays);
        // Calculating rank
        Integer rank = computer.getRank(ageInDays);
        data.setRank(rank);
        return data;
    }

}
