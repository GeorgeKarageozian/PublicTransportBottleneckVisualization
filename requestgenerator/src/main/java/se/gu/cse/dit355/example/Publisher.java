package se.gu.cse.dit355.example;

import Models.LatLon;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
//import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Publisher implements MqttCallback {

    private static String TOPIC = "external";
    private static String PORT = "1883";
    private static String IPADDRESS = "localhost";
    private static String CASE = "standard";

    private static String BROKER = "tcp://" + IPADDRESS + ":" + PORT;

    private final static String USER_ID = "requests-publisher";
    final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private final IMqttClient middleware;

   static RequestGenerator requestGenerator;

    public Publisher(RequestGenerator r) throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);
        this.requestGenerator = r;
        //requestGenerator = new RequestGenerator(2);
    }

    private void close() throws MqttException {
        middleware.disconnect();
        middleware.close();
    }

    public void subscribeToMessage() {
      /*  THREAD_POOL.submit(() -> {
            try {
                middleware.subscribe(TOPIC_SOURCE);

            } catch (MqttSecurityException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });*/
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage incoming) throws Exception {
        /*Gson gson = new Gson();
        //RequestGenerator requestGenerator = new RequestGenerator(2);

        String message = incoming.toString();
        incoming.setQos(1);
        System.out.println(message);
        LatLon latLon = gson.fromJson(message, LatLon.class);

        //JsonObject jsonObject = requestGenerator.generateRequests(latLon.getOriginLatitude(), latLon.getOriginLongitude(), latLon.getOriginSuburb(), latLon.getDestinationLatitude(), latLon.getDestinationLongitude(), latLon.getDestinationSuburb());
        JsonObject jsonObject = requestGenerator.generateRequests(latLon.getOriginLatitude(), latLon.getOriginLongitude(), latLon.getOriginSuburb(), latLon.getDestinationLatitude(), latLon.getDestinationLongitude(), latLon.getDestinationSuburb());
        System.out.println(jsonObject.toString());
        //publishMessage(jsonObject);
        sendMessage(jsonObject.toString(), TOPIC);*/


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


    private void sendMessage(String payLoad, String topic) throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payLoad.getBytes());
        message.setQos(0);
        System.out.println(message);
        middleware.publish(topic, message);
    }

    /*

    public static void takeCmdArgs(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].contains("sink") || arguments[i].contains("Sink")) {
                TOPIC = arguments[i + 1];
                System.out.println("Publishing to topic: " + TOPIC);
            } else if (arguments[i].contains("port") || arguments[i].contains("PORT")) {
                PORT = arguments[i + 1];
                System.out.println("Running on port: " + PORT);
            } else if (arguments[i].contains("IP") || arguments[i].contains("ip")) {
                IPADDRESS = arguments[i + 1];
                System.out.println("Publishing to machine with ip address: " + IPADDRESS);
            } else if (arguments[i].contains("CASE") || arguments[i].contains("case")) {
                CASE = arguments[i + 1];
                System.out.println("Current case is " + CASE);
            } else if (arguments == null){
                System.out.println("Running default config settings");

            }

            System.out.println(PORT);
            System.out.println(IPADDRESS);
            System.out.println(CASE);

        }
        BROKER = "tcp://" + IPADDRESS + ":" + PORT;
    }
    */

    public static void main(String[] args) throws MqttException, IOException {

    //    takeCmdArgs(args);
        String argPort;
        String sink;
        String source;
        String IP;
        String argCase;

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("port") || args[i].contains("PORT")) {
                argPort = args[i + 1];
                PORT = argPort;
                System.out.println("running on port " + argPort);
            } else if (args[i].contains("topic") || args[i].contains("TOPIC")) {
                sink = args[i + 1];
                TOPIC = sink;
                System.out.println("sink topic: " + sink);
            }  else if (args == null) {
                TOPIC = "external";

                PORT = "1883";
                IPADDRESS = "localhost";
                System.out.println("Running default Topics, IP Address and port number");
            } else if (args[i].contains("IP") || args[i].contains("ip")) {
                IP = args[i + 1];
                IPADDRESS = IP;
                System.out.println("IP address: " + IP);
            }
            else if (args[i].contains("CASE") || args[i].contains("case")) {
                argCase = args[i + 1];
                CASE = argCase;
                System.out.println("Case: " + CASE);
            }
        }
        BROKER = "tcp://" + IPADDRESS + ":" + PORT;
        System.out.println(BROKER);
        //Connection connection = new Connection();
        System.out.println(TOPIC);
        System.out.println(IPADDRESS);
        System.out.println(CASE);
        //System.out.println(TOPIC_SOURCE);
        //RequestGenerator r = new RequestGenerator(2, CASE);
        long deviceID = ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE);
        RequestGenerator r = new RequestGenerator(deviceID, CASE);
       // requestGenerator.REQUEST_CASE = CASE;
        Publisher publisher = new Publisher(r);


        Timer timer = new Timer();



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String randomRequest;
                try {
                    randomRequest = r.generateRequestsData().toString();
                    publisher.sendMessage(randomRequest, TOPIC);
                    System.out.println("Current case:" + requestGenerator.getREQUEST_CASE());
                    System.out.println(BROKER);
                } catch (IOException | MqttException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(timerTask, 500, 500);


/*        Thread thread = new Thread(() -> {
            Timer timer = new Timer();
            RequestGenerator r = new RequestGenerator(2);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    String randomRequest;
                    try {
                        randomRequest = r.generateRequestsData().toString();
                        publisher.sendMessage(randomRequest, TOPIC);
                    } catch (IOException | MqttException e) {
                        e.printStackTrace();
                    }
                }
            };

            timer.schedule(timerTask, 500, 500);
        });

        thread.start();*/


    }

}