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
package lu.goettelmann.sonar.codefreshness.settings;

import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.Arrays;
import java.util.List;

public class CodeFreshnessProperties {

    public static final String BASE_PERIOD = "sonar.codeFreshness.basePeriod";
    public static final String GROWTH_FACTOR = "sonar.codeFreshness.growthFactor";
    public static final String CATEGORY = "Code Freshness";
    public static final String SUB_CATEGORY_RATIO = "Code Freshness Ratio Computation";

    private CodeFreshnessProperties() {
        // only statics
    }

    public static List<PropertyDefinition> getProperties() {
        return Arrays.asList(
                // The base period
                PropertyDefinition.builder(BASE_PERIOD)
                        .name("Base period")
                        .description("The duration (in days) of the first rating period (rating A)")
                        .type(PropertyType.INTEGER)
                        .defaultValue(String.valueOf(90))
                        .onQualifiers(Qualifiers.PROJECT)
                        .category(CATEGORY)
                        .subCategory(SUB_CATEGORY_RATIO)
                        .build(),
                // The growth factor
                PropertyDefinition.builder(GROWTH_FACTOR)
                        .name("Growth factor")
                        .description("The growth factor applied between each rating period")
                        .type(PropertyType.FLOAT)
                        .defaultValue(String.valueOf(2.0))
                        .onQualifiers(Qualifiers.PROJECT)
                        .category(CATEGORY)
                        .subCategory(SUB_CATEGORY_RATIO)
                        .build()
        );
    }

}
