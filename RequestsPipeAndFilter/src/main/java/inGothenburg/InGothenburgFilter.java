package inGothenburg;


import com.google.gson.Gson;
import models.OnWaterResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import java.io.IOException;
import java.sql.Connection;

public class InGothenburgFilter {


    private final static String IN_GOTHENBURG_API_KEY = "445c0302d49562";


    //This method makes a call to the locationiq API to check if the coordinates are located in Gothenburg.It returns true if the location is in Gothenburg
    public String inGothenburgFilter(Double latitude, Double longitude) {
        CloseableHttpClient client = HttpClients.createDefault();
        Gson gson = new Gson();
        String suburb = null;

        try {
            HttpGet request = new HttpGet("https://eu1.locationiq.com/v1/reverse.php?key=" + IN_GOTHENBURG_API_KEY + "&lat=" + latitude + "&lon=" + longitude + "&format=json");
            System.out.println("Http Request Type: " + request.getMethod());
            request.addHeader("accept", "application/json");

            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode + " inGothenburg");

            if (statusCode != 200) {
                throw new RuntimeException("failed to get status code" + statusCode);
            }
            HttpEntity httpEntity = response.getEntity();

            String outPut = EntityUtils.toString(httpEntity);
            JSONObject rootObject = new JSONObject(outPut);
            JSONObject object = rootObject.getJSONObject("address");
            System.out.println("address" + object);
            String s = object.getString("state");
            System.out.println("State: " + s);
            if (s.equals("Västra Götaland County")) { String jsonString = object.toString();
                if (jsonString.contains("suburb")) {
                    System.out.println(object.getString("suburb"));
                    suburb = object.getString("suburb");
                } else {
                    suburb = "uncategorised";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return suburb;
    }


}
