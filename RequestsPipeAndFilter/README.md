# onLandFilter
Group 8
DIT355 Mini project


## Installation and set up
Clone the repository by entering the following the command into the terminal:
```
git@git.chalmers.se:courses/dit355/2019/group-8/onlandfilter.git
```

Using the terminal navigate to the client and build using Maven:
```
cd onlandfilter

mvn clean install
```
Execute the jar file to start publishing:
```
cd target
java -jar publisher.jar
```
If the user wishes to Execute the jar file with custom configurations
```
 Java -jar publisher.jar PORT [port] SINK [sink] SOURCE [source] IP [ip]
 ```
**PORT**: sets the port to the given argument. If no argument is given, then the port is set to the default value that is 1883.

**SINK**: The topic that we publish to. Changes the TOPIC_SINK variable to the given argument. If no argument is provided, then we use that default value which is "requests".

**SOURCE**: The topic that we subscribe to. Changes the TOPIC_SOURCE variable to the given argument. If no argument is provided, then the program will run with the default value for source which is “external”.

**IP**: Sets the IPADDRESS variable to the given argument. If no argument is provided, then we set it to the default value which is “localhost”.

Publisher will run even when the program are given arguments that are not valid or values that will make the publisher not connect to anything. It is up to the user to specify the correct arguments. 

You have to specify the string PORT before the actual value of port 
For example Java -jar publisher.jar PORT 1883
This is because then the user does not have to know a specific order and it makes the program more user friendly.


## Prerequirements
installed Java JDK 8 or above
installed maven 3.5 or above
mqtt broker (e.g. Mosquitto) installed, and running locally on default port 1883
