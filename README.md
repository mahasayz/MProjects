# Weather Applications
### Introduction
This is a small command-line application that fetches weather data on the basis of cities taken user input.
The weather data is fetched from (http://openweather.org). The data from the site has been serialized to Java Model Objects which has been thereafter altered as per user needs.

### Features
The features are pretty straight-forward. At this initial phase users are provided with limited functinality, namely:
- View Configuration : Users can control the weather report that they see for each city via the properties file
- Sorting : Users can sort their results as per desired field

### Getting Started
* Ensure your machine has Java Runtime Environment([JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre6-downloads-1637595.html)) installed
* Copy the JAR file provided to a location along-with the weather-conf.props file
* Run the JAR file along-with the desired parameters or type `java -jar WeatherApp.jar --help` for help

### Examples
* Configuring output results via properties file
 * In the weather-conf.props file just set the value corresponding to the field you would like to see in the result as `true`
 ```
 #weather-conf.props
 Country=true
 Condition=false
 ```
* Searching for cities
  * `java -jar WeatherApp.jar sydney tokyo`
* Searching for cites sorted by temperature
  * `java -jar WeatherApp.jar sydney tokyo --sort=temp`
* Help on Usage
  * `java -jar WeatherApp.jar --help`
