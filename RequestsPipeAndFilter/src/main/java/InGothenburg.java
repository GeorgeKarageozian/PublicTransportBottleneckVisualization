import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class InGothenburg {
    public InGothenburg() {

    }

    private final static String IN_GOTHENBURG_API_KEY = "445c0302d49562";


    public String getOriginLatitude(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Double originLatitudeDouble = jsonObject.getDouble("originLatitude");
        String originLatitude = Double.toString(originLatitudeDouble);
        return originLatitude;

    }

    public String getOriginLongitude(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Double originLongitudeDouble = jsonObject.getDouble("originLongitude");
        String originLongitude = Double.toString(originLongitudeDouble);
        return originLongitude;
    }

    public String getDestinationLatitude(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Double destinationLatitudeDouble = jsonObject.getDouble("destinationLatitude");
        String destinationLatitude = Double.toString(destinationLatitudeDouble);
        return destinationLatitude;
    }

    public String getDestinationLongitude(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Double destinationLongitudeDouble = jsonObject.getDouble("destinationLongitude");
        String destinationLongitude = Double.toString(destinationLongitudeDouble);
        return destinationLongitude;
    }
}

