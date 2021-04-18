SonarQube CodeFreshness Plugin [![Build Status](https://travis-ci.com/egoettelmann/sonar-code-freshness-plugin.svg?branch=develop)](https://travis-ci.com/egoettelmann/sonar-code-freshness-plugin)
==========

An SonarQube plugin to measure the code freshness of a project, compatible with SonarQube 8.x.

Goal of this plugin
-------------------

This plugin is intended to provide some metrics about the freshness of your code.
It gives you some insights about when the last change on your code happened and rates your code based on that.

### Background

Code should continuously change over time, not only to implement new features but also to follow:
 - new advancements in terms of technologies and libraries
 - evolutions of coding standards and best practices
 - and most importantly, the progress of a team's experience and skills

As stated by Uncle Bob (Robert C. Martin):
> Always leave the code you’re editing a little better than you found it

Very basically, this means that code that doesn't change, could already be interpreted as a code smell.

### Metrics

By adding the plugin to your SonarQube installation, you will have access to following additional measures (under the **SCM** section):
 - `Average age of your code`, the average number of days passed between the last analysis and the last commit on the corresponding section of the code.
   The average is a weighted average, that takes into account the number of lines of code.
 - `Freshness rating of your code`, the rating based on the previous average calculation.
   The *older* your code is, the *worse* the rating will be (between `A` and `E`).
   Checkout the [Configuration](#configuration) section to get further details.


Installation
------------

Download the latest `jar` file, copy it to your SonarQube's `extensions/plugins/` directory, and re-start SonarQube.

You can start a new analysis to see the results under the **Measures** tab (section **SCM**).

### Configuration

The **Freshness rating** can be configured through the 2 following properties (at global or project level):
 - `basePeriod`, the duration (in days) of the first rating period (rating A). Default: `90`.
 - `growthFactor`, the growth factor applied between each rating period. Default: `2.0`.

The idea behind the **Freshness rating** is to cluster your code into periods (depending on the age of the last change).
Each period is associated to a rating (the *older* the code, the *worse* the rating will be).
The lengths of the periods are increasing the older the periods are.

#### Default configuration

By default, the configuration will produce ratings defined as follows:

| Freshness rating | Lower bound | Upper bound |
| :---: | :----------- | :------------ |
| **A** | `-`          | `<=  90 days` |
| **B** | `> 90 days`  | `<= 180 days` |
| **C** | `> 180 days` | `<= 360 days` |
| **D** | `> 360 days` | `<= 720 days` |
| **E** | `> 720 days` | `-`           |

Simply put:
 - code with an average age of less or equal to 3 months is rated `A`
 - code with an average age of more than 2 years is rated `E`

More precisely, the following formula is used to determined the rating:

![formula](https://render.githubusercontent.com/render/math?math=f(x,%20a,%20N)%20=%20log_{N}({x%20\over%20a})%2b1)

with:
 - `x`, the calculated average age of the code
 - `a`, the `basePeriod`
 - `N`, the `growthFactor`


Development
-----------

The codebase has been forked from [sonar-custom-plugin-example](https://github.com/SonarSource/sonar-custom-plugin-example), and follows the structure of this example project.


### Building

To build the plugin JAR file, call:

```
mvn clean package
```

The JAR will be packaged to `target/sonar-code-freshness-plugin-VERSION.jar`.
Copy this to your SonarQube's `extensions/plugins/` directory, and re-start SonarQube.
