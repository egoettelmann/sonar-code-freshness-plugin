/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package lu.goettelmann.sonar.codefreshness.measures;

import lu.goettelmann.sonar.codefreshness.core.CodeFreshnessData;
import lu.goettelmann.sonar.codefreshness.core.CodeFreshnessDataBuilder;
import lu.goettelmann.sonar.codefreshness.settings.CodeFreshnessProperties;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class CodeFreshnessMeasureComputer implements MeasureComputer {

    private static final Logger LOGGER = Loggers.get(CodeFreshnessMeasureComputer.class);

    private static final String SEPARATOR = ";";

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return def.newDefinitionBuilder()
                .setInputMetrics(
                        CoreMetrics.LAST_COMMIT_DATE.key(),
                        CoreMetrics.LINES.key()
                )
                .setOutputMetrics(
                        CodeFreshnessMetrics.CODE_FRESHNESS_DATA.key(),
                        CodeFreshnessMetrics.CODE_FRESHNESS_AVG.key(),
                        CodeFreshnessMetrics.CODE_FRESHNESS_RATING.key()
                )
                .build();
    }

    @Override
    public void compute(MeasureComputerContext context) {
        // Extracting properties from settings
        String basePeriodSetting = context.getSettings().getString(CodeFreshnessProperties.BASE_PERIOD);
        int basePeriod = Integer.parseInt(basePeriodSetting != null ? basePeriodSetting : "3");
        String growthFactorSetting = context.getSettings().getString(CodeFreshnessProperties.GROWTH_FACTOR);
        int growthFactor = Integer.parseInt(growthFactorSetting != null ? growthFactorSetting : "2");
        LOGGER.info("Computing CodeFreshness with basePeriod='{}' and growthFactor='{}'", basePeriod, growthFactor);

        // Building data for file
        if (context.getComponent().getType() == Component.Type.FILE) {
            Measure lastCommitDate = context.getMeasure(CoreMetrics.LAST_COMMIT_DATE.key());
            Measure numLines = context.getMeasure(CoreMetrics.LINES.key());
            if (lastCommitDate == null || numLines == null) {
                LOGGER.warn("Missing file data to compute CodeFreshness");
                return;
            }
            // Building data and adding it to context
            CodeFreshnessData data = new CodeFreshnessDataBuilder(basePeriod, growthFactor)
                    .add(lastCommitDate.getLongValue(), numLines.getIntValue())
                    .build();
            this.save(data, context);
            return;
        }

        // Building data for component (directory)
        CodeFreshnessDataBuilder builder = new CodeFreshnessDataBuilder(basePeriod, growthFactor);
        for (Measure childrenMeasure : context.getChildrenMeasures(CodeFreshnessMetrics.CODE_FRESHNESS_DATA.key())) {
            // Deserializing data of child measure
            String serializedData = childrenMeasure.getStringValue();
            if (serializedData == null) {
                LOGGER.warn("Child measure has no data to compute CodeFreshness");
                continue;
            }
            String[] values = serializedData.split(SEPARATOR);
            if (values.length < 2) {
                LOGGER.warn("Child measure data cannot be used to compute CodeFreshness");
                continue;
            }
            builder = builder.add(Long.parseLong(values[0]), Integer.parseInt(values[1]));
        }
        CodeFreshnessData data = builder.build();

        if (data == null) {
            LOGGER.warn("Component has no data for CodeFreshness");
            return;
        }
        this.save(data, context);
    }

    private void save(CodeFreshnessData data, MeasureComputerContext context) {
        String serializedData = data.getAvgCommitDate()
                + SEPARATOR
                + data.getNumLines();
        context.addMeasure(CodeFreshnessMetrics.CODE_FRESHNESS_DATA.key(), serializedData);
        context.addMeasure(CodeFreshnessMetrics.CODE_FRESHNESS_AVG.key(), data.getAgeInDays());
        context.addMeasure(CodeFreshnessMetrics.CODE_FRESHNESS_RATING.key(), data.getRank());
        LOGGER.info("Added CodeFreshness data '{}' for '{}'", data, context.getComponent().getKey());
    }

}
