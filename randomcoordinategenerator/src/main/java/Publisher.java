import generator.generator;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Publisher {

    private final static String TOPIC = "requests";

    private final static String BROKER = "tcp://localhost:1883";

    private final static String USER_ID = "randomCoordinatePublisher";

    private final IMqttClient middleware;

    public Publisher() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
    }

    public static void main(String[] args) throws MqttException {
        Publisher p = new Publisher();
        generator g = new generator();

        Timer timer = new Timer();

        TimerTask timertask = new TimerTask(){
            @Override
            public void run(){
                String randomCoordinates;
                try {
                    randomCoordinates = g.getCoordinate().toString();
                    p.sendMessage(randomCoordinates);
                } catch( MqttException e){
                    e.printStackTrace();
                }

                }
            };
            timer.schedule(timertask,2000, 2000);
        }
       
    

    private void close() throws MqttException {
        middleware.disconnect();
        middleware.close();
    }

    private void sendMessage(String payLoad) throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payLoad.getBytes());
        middleware.publish(TOPIC, message);
    }

}