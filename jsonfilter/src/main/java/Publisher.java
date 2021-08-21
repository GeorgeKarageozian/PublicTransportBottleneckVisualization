import org.eclipse.paho.client.mqttv3.*;

public class Publisher {

    private final static String TOPIC = "test";

    private final static String BROKER = "tcp://localhost:1883";

    private final static String USER_ID = "test-publisher";

    private final IMqttClient middleware;

    public Publisher() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
    }

    private void sendMessage(String payLoad) throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payLoad.getBytes());
        middleware.publish(TOPIC, message);

    }

    private void close() throws MqttException {
        middleware.disconnect();
        middleware.close();
    }
}
