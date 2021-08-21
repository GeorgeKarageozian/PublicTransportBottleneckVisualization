# RequestGenerator
Group 8
DIT355 Mini project

## Subscription
You can subscribe to the topic **'external'** to get randomly generated trip requests every half second.
You can subscribe to two different scenarios under the same topic:
- **'standard'** - this represents a regular morning on a normal weekday. The requests will be for work, school and leisure. Generally, requests will be for all over the region, but the requests tend to be for travellers looking to move a bit closer to the centre.
- **'special'** - this represents a special event. All the requests will be for leisure and the destinations will be concentrated in the centre of Gothenburg.


## Installation and set up

Clone the repository by entering the following the command into the terminal:
```
git clone git@git.chalmers.se:courses/dit355/2019/group-8/requestgenerator.git
```

Using the terminal navigate to the client and build using Maven:
```
cd requestgenerator
mvn clean install
```

Execute the jar file to start publishing:
```
cd target
java -jar publisher.jar
```
If the user wishes to Execute the jar file with custom configurations
```
Java -jar publisher.jar PORT [port] TOPIC [topic] IP [ip] CASE [scenario]
```
## Explanation of valid arguments:
**PORT**: sets the port to the given argument. If no argument is given, then the port is set to the default value that is 1883.

**TOPIC**: The topic that we publish to. Changes the TOPIC_SINK variable to the given argument. If no argument is provided, then we use that default value which is “external”.

**IP**: Sets the IPADDRESS variable to the given argument. If no argument is provided, then we set it to the default value which is “localhost”.

**CASE**: Sets the scenario variable to the given argument. If no argument is provided, then the default scenario will be used which is “standard”. The other possible case is "special".


Publisher will run even when the program are given arguments that are not valid or values that will make the publisher not connect to anything. It is up to the user to specify the correct arguments. 

You have to specify the string PORT before the actual value of port 
For example Java -jar publisher.jar PORT 1883


## Prerequirements

installed Java JDK 8 or above
installed maven 3.5 or above
mqtt broker (e.g. Mosquitto) installed, and running locally on default port 1883


