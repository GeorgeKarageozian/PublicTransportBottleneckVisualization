import java.io.IOError;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.json.JSONObject;

public class connection implements MqttCallback {

    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private final static String TOPIC_SOURCE = "requests";

    private final static String TOPIC_SINK = "inGothenburg";

    private final static String BROKER = "tcp://localhost:1883";

    private final static String USER_ID = "inGothenburg";

    private final IMqttClient middleware;

    private static String message;

    String key = "404432a2c3f3e4";

    public connection() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);

    }

    public static void main(String[] args) throws MqttException, InterruptedException {
        InGothenburg inGothenburg = new InGothenburg();
        connection connection = new connection();
        connection.subscribeToMessages();
    }

    private void subscribeToMessages() {
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
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage incoming) throws Exception {
        message = incoming.toString();
        InGothenburg inGothenburg = new InGothenburg();
        JSONObject object = new JSONObject();
        object.put("latitude", inGothenburg.getLatitudeDouble(message));
        object.put("longitude", inGothenburg.getLongitudeDouble(message));
        System.out.println(object);

        Boolean b = inGothenburgFilter(inGothenburg.getLongitude(message), inGothenburg.getLatitude(message));
        if (b == true) {
            System.out.println("In Gothenburg, publishing");
            publishJSON(object);
        } else {
            System.out.println("Not in Gothenburg");
        }
    }

    public Boolean inGothenburgFilter(String longitude, String latitude) {
        CloseableHttpClient client = HttpClients.createDefault();
        Gson gson = new Gson();
        Boolean inGothenburg = null;
        try {
            HttpGet request = new HttpGet("https://eu1.locationiq.com/v1/reverse.php?key=" + key + "&lat=" + latitude + "&lon=" + longitude + "&format=json");
            System.out.println("Http Request Type: " + request.getMethod());
            request.addHeader("accept", "application/json");

            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);

            if (statusCode != 200) {
                throw new RuntimeException("failed to get status code" + statusCode);
            }
            HttpEntity httpEntity = response.getEntity();

            String outPut = EntityUtils.toString(httpEntity);
            JSONObject rootObject = new JSONObject(outPut);
            JSONObject object = rootObject.getJSONObject("address");
            String s = object.getString("county");
            System.out.println("City: " + s);

            if (s.equals("Göteborg")) {
                inGothenburg = true;

            } else {
                inGothenburg = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return inGothenburg;
    }

    public void publishJSON(JSONObject json) throws MqttPersistenceException, MqttException {
        MqttMessage outgoing = new MqttMessage();
        outgoing.setPayload(json.toString().getBytes());
        middleware.publish(TOPIC_SINK, outgoing);

    }

}