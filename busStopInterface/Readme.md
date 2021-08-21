# Prerequirements

- installed Java JDK 8 or above
- installed maven 3.5 or above
- mqtt broker (e.g. Mosquitto) installed, and running locally on default port 1883

# Install

- checkout repository to your machine
- open a terminal, traverse to the main root of the repository, and run ```mvn clean install```
- after successful install, the jar file for execution is called publisher.jar and can be found in the target directory
- traverse to the target directory and execute ```java -jar publisher.jar``` in order to send a hello world message through mqtt and exit

# Comments

- when exeucting the jar file, some warning messages may show up. They are nothing to worry about.

 # Basic API information
 
API Base URL https://api.resrobot.se

 # How To Get An API Key
 - Go to the https://www.trafiklab.se/ Platform .
 - Click the project  and select or create the project for which you want to get API key.
 - Chosse a new name for your project and  select APIer ReseRebort.
 - Choose keywords for your new project . ...
 - Click Save.

 # Using an API key
 Pass the API key into a REST API call as a query parameter with the following format. Replace API_KEY with your API key,

key=API_KEY

`BufferedReader bfReader = new BufferedReader(new FileReader("Api_Key "))`

# Notes for 'publisher' machine
1. Make sure mosquitto is not running
2. Connect to the same wifi (using one moblie phone's hotspot) of other computers
3. Replace 'localhost' in the code with the ip address of the computer which runs the mosquitto