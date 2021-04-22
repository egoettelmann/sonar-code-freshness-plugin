package com.github.egoettelmann.sonar.codefreshness.core;

public class CodeFreshnessData {

    private Long avgCommitDate;

    private Integer numLines;

    private Integer ageInDays;

    private Integer rank;

    CodeFreshnessData() {
        // Default constructor is package private: builder should be used
    }

    public Long getAvgCommitDate() {
        return avgCommitDate;
    }

    public void setAvgCommitDate(Long avgCommitDate) {
        this.avgCommitDate = avgCommitDate;
    }

    public Integer getNumLines() {
        return numLines;
    }

    public void setNumLines(Integer numLines) {
        this.numLines = numLines;
    }

    public Integer getAgeInDays() {
        return ageInDays;
    }

    public void setAgeInDays(Integer ageInDays) {
        this.ageInDays = ageInDays;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "CodeFreshnessData{" +
                "avgCommitDate=" + avgCommitDate +
                ", numLines=" + numLines +
                ", ageInDays=" + ageInDays +
                ", rank=" + rank +
                '}';
    }
}
