# java_metrics_sensor
A tool to analyse Java code quality able to calculate the following metrics:
• Lines of code (LOC)
• Number of methods and number of public methods (NOM - NPM)
• Average method complexity (AMC)
• Cyclomatic complexity (CC)
• Weighted method per class (WMC)
• Cohesion among methods of class (CAM)
• Data Access metric (DAM)
• Measure of Aggregation (MOA)
• Response for a class (RFC)
• Lack of Cohesion in methods (LCOM3)
• Coupling between objects (CBO)
• Depth of Inheritance tree (DIT)
• Number of Children (NOC)

The software is built on top of ANTLR4 compiler generated with an open-source Java8 grammar.
It provides a JavaFX GUI and the possibility to print the results in three different .csv files, each one represents a depth level of the analysis: Overview, Classes and single methods.

This project is licensed under the terms of the Apache 2.0 License.
