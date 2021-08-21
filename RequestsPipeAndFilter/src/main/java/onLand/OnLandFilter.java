package onLand;


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

public class OnLandFilter {

    private final static String ON_WATER_API_KEY = "vmm_Gmszgg4sGGi8rXTu";
   // private final static String IN_GOTHENBURG_API_KEY = "445c0302d49562";



    public static boolean onWater(Double longitude, Double latitude) {
        CloseableHttpClient client = HttpClients.createDefault();
        Gson gson = new Gson();

        Boolean onWater = null;


        try {

            HttpGet request = new HttpGet("https://api.onwater.io/api/v1/results/" + latitude + "," + longitude + "?access_token=" + ON_WATER_API_KEY);
            System.out.println("Http Request Type: " + request.getMethod());

            request.addHeader("accept", "application/json");



            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode + "onWater");

            if (statusCode != 200) {
                throw new RuntimeException("failed to get status code: " + statusCode);
            }

            HttpEntity httpEntity = response.getEntity();



            String outPut = EntityUtils.toString(httpEntity);
            OnWaterResponse onWaterResponse = gson.fromJson(outPut, OnWaterResponse.class);
            String tempBool = onWaterResponse.getWater();
            //System.out.println(tempBool);




            if (tempBool.equals("true")) {
                onWater = true;
            } else {

                onWater = false;
            }





        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            client.getConnectionManager().shutdown();
        }



        return onWater;

    }




}
