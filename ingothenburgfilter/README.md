# inGothenburgFilter
Group 8
DIT355 Mini project


## Installation and set up
Clone the repository by entering the following the command into the terminal:
```
git@git.chalmers.se:courses/dit355/2019/group-8/ingothenburgfilter.git
```

Using the terminal navigate to the client and build using Maven:
```
cd ingothenburgfilter
mvn clean install
```
Execute the jar file to start publishing:
```
cd target
java -jar connection.jar PORT [portnumber] Sink [SINK_TOPIC] SOURCE [SOURCE_TOPIC]
> Can take no parameters and will then run with the default port and topics 



```

The default port for mosquito is 1883.
The default topic Sink is "requests".  The default topic Source is  "external". 


## Prerequirements
installed Java JDK 8 or above
installed maven 3.5 or above
mqtt broker (e.g. Mosquitto) installed, and running locally on default port 1883

