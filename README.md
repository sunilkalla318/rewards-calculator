# rewards-calculator
A spring boot application that calculated the rewards. 
This application has one endpoint exposed at /rewards/calculateRewards.
The end point takes all the transactions that needs a reward calculation in a json format(Please refer to the sample attached under main/resources/Sample.txt) and returns a List of Rewards json with coustomerId and their rewards.


To build the app:
```
./gradlew build
```

To run the app use the following command:
```
./gradlew bootRun
```

Application runs on default 8080 port and url for [SWAGGER](http://localhost:8080/swagger-ui.html)

