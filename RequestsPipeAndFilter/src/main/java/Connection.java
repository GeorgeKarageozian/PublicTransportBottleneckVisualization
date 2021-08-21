import com.google.gson.Gson;
import com.google.gson.JsonObject;
import handleJson.HandleJson;
//import http.HttpRequests;
import onLand.OnLandFilter;
import inGothenburg.InGothenburgFilter;
import models.LatLon;
import models.RequestGeneratorMessage;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import sun.nio.ch.ThreadPool;

import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Connection implements MqttCallback {

    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private static String TOPIC_SOURCE = "external";

    private static String TOPIC_SINK = "requests";

    private static String IPAddress = "localhost";

    private static String PORT = "1883";

    private static String BROKER = "tcp://localhost:" + PORT;

    private final static String USER_ID = "onLand";

    private final IMqttClient middleware;

    private static Queue<String> connectionQ = new LinkedList<>();


    public Connection() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);
    }


    public static void main(String[] args) throws MqttException, InterruptedException {


        String argPort;
        String sink;
        String source;
        String IP;

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("port") || args[i].contains("PORT")) {
                argPort = args[i + 1];
                PORT = argPort;
                System.out.println("running on port " + argPort);
            } else if (args[i].contains("SINK") || args[i].contains("sink")) {
                sink = args[i + 1];
                TOPIC_SINK = sink;
                System.out.println("sink topic: " + sink);
            } else if (args[i].contains("source") || args[i].contains("SOURCE")) {
                source = args[i + 1];
                TOPIC_SOURCE = source;
                System.out.println("source topic: " + source);
            } else if (args == null) {
                TOPIC_SOURCE = "external";
                TOPIC_SINK = "requests";
                PORT = "1883";
                IPAddress = "localhost";
                System.out.println("Running default Topics, IP Address and port number");
            } else if (args[i].contains("IP") || args[i].contains("ip")) {
                IP = args[i + 1];
                IPAddress = IP;
                System.out.println("IP address: " + IP);
            }
        }
        BROKER = "tcp://" + IPAddress + ":" + PORT;
        System.out.println(BROKER);
        Connection connection = new Connection();
        System.out.println(TOPIC_SINK);
        System.out.println(TOPIC_SOURCE);


        connection.subscribeToMessage();

        OnLandFilter onLandFilter = new OnLandFilter();
        InGothenburgFilter inGothenburgFilter = new InGothenburgFilter();
        Gson gson = new Gson();
        Timer timer = new Timer();

        Thread thread = new Thread(() -> {

            //Adding a timeTask as fault tolerance to make a delay by 8 seconds in order not to get error from the onWater API
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        //Added a queue to not lose the data received
                        if (!connectionQ.isEmpty()) {
                            RequestGeneratorMessage rgm = gson.fromJson(connectionQ.poll(), RequestGeneratorMessage.class);
                            System.out.println(rgm.getDestination().getLatitude());
                            //Checking if the origin and destination coordinates are in Gothenburg
                            String checkOrigin = inGothenburgFilter.inGothenburgFilter(rgm.getOrigin().getLatitude(), rgm.getOrigin().getLongitude());
                            String checkDestination = inGothenburgFilter.inGothenburgFilter(rgm.getDestination().getLatitude(), rgm.getDestination().getLongitude());
                            if (checkOrigin != null && checkDestination != null) {
                                System.out.println("In Gothenburg! \n");
                                System.out.println("Checking if on water...");
                                rgm.getOrigin().setSuburb(checkOrigin);
                                rgm.getDestination().setSuburb(checkDestination);
                                System.out.println("inGothenburg obj" + rgm.getOrigin().getSuburb());

                                Boolean originOnWater = onLandFilter.onWater(rgm.getOrigin().getLongitude(), rgm.getOrigin().getLatitude());
                                Boolean destinationOnWater = onLandFilter.onWater(rgm.getDestination().getLongitude(), rgm.getDestination().getLatitude());

                                if (!originOnWater && !destinationOnWater) {
                                    System.out.println("Is not on water. Publishing!");

                                    try {
                                        connection.publishOnLand(rgm);
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("Locations are not on land!");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            };

            timer.schedule(timerTask, 2000, 8000);
        });

        thread.start();


    }

    public void subscribeToMessage() {
        THREAD_POOL.submit(() -> {
            try {
                middleware.subscribe(TOPIC_SOURCE);

            } catch (MqttSecurityException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost!");
        try {
            middleware.disconnect();
            middleware.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = mqttMessage.toString();
        mqttMessage.setQos(0);
        //System.out.println(message);
        connectionQ.add(message);

        //System.out.println("queue in messagearrived"+ connectionQ);

/*
        BlockingQueue<Thread> blockingQueue = new LinkedBlockingQueue<>();



       Thread t =  new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String s = q.poll();
                LatLon latLon = gson.fromJson(s, LatLon.class);
                Boolean onWater = HttpRequests.onWater(latLon.getLon(), latLon.getLat());
                System.out.println(latLon.getLat());
                System.out.println(onWater);


                if (onWater == false) {
                    System.out.println("Is not on water. Publishing!");
                    try {
                        publishOnLand(latLon);*/

        //connectionQ.add(message);


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public void publishOnLand(RequestGeneratorMessage requestGeneratorMessage) throws MqttPersistenceException, MqttException {
        JSONObject root = new JSONObject();
        JSONObject origin = new JSONObject();
        JSONObject destination = new JSONObject();
        //creating origin innerclass for json object
        origin.put("latitude", requestGeneratorMessage.getOrigin().getLatitude());
        origin.put("longitude", requestGeneratorMessage.getOrigin().getLongitude());
        origin.put("suburb", requestGeneratorMessage.getOrigin().getSuburb());

        //creating destination inner class for json object
        destination.put("latitude", requestGeneratorMessage.getDestination().getLatitude());
        destination.put("longitude", requestGeneratorMessage.getDestination().getLongitude());
        destination.put("suburb", requestGeneratorMessage.getDestination().getSuburb());

        //adding attributes to the root json object
        root.put("deviceId", requestGeneratorMessage.getDeviceId());
        root.put("requestId", requestGeneratorMessage.getRequestId());
        root.put("origin", origin);
        root.put("destination", destination);
        root.put("timeOfDeparture", requestGeneratorMessage.getTimeOfDeparture());
        root.put("purpose", requestGeneratorMessage.getPurpose());
        root.put("issuance", requestGeneratorMessage.getIssuance());


        MqttMessage outgoing = new MqttMessage();
        outgoing.setQos(1);
        outgoing.setPayload(root.toString().getBytes());
        System.out.println("THE PUBLISHED OBJECT" + root);
        middleware.publish(TOPIC_SINK, outgoing);

    }
}
