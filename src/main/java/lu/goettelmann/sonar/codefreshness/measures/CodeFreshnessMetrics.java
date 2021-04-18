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

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.List;

import static java.util.Arrays.asList;

public class CodeFreshnessMetrics implements Metrics {

    public static final Metric<Integer> CODE_FRESHNESS_AVG = new Metric.Builder("code_freshness_avg", "Average code age", Metric.ValueType.INT)
            .setDescription("Average age of your code (in days)")
            .setDirection(Metric.DIRECTION_BETTER)
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SCM)
            .create();

    public static final Metric<Integer> CODE_FRESHNESS_RATING = new Metric.Builder("code_freshness_rating", "Code Freshness Rating", Metric.ValueType.RATING)
            .setDescription("Freshness rating of your code")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SCM)
            .create();

    public static final Metric<Integer> CODE_FRESHNESS_DATA = new Metric.Builder("code_freshness_data", "Code Freshness Data", Metric.ValueType.DATA)
            .setDescription("Code freshness data")
            .setDirection(Metric.DIRECTION_NONE)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_SCM)
            .setHidden(true)
            .create();

    @Override
    public List<Metric> getMetrics() {
        return asList(CODE_FRESHNESS_DATA, CODE_FRESHNESS_AVG, CODE_FRESHNESS_RATING);
    }

}
