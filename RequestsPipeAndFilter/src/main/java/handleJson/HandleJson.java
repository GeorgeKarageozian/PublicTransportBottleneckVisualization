package handleJson;

import org.json.JSONObject;

public class HandleJson {

    public HandleJson() {

    }

    public double getLatitude(String json) {

        JSONObject jsonObject = new JSONObject(json);

        String latitude = jsonObject.getString("latitude");

        double lat = Double.parseDouble(latitude);

        return lat;

    }

    public double getLongitude(String json) {
        JSONObject jsonObject = new JSONObject(json);

        String longitude = jsonObject.getString("longitude");

        double lon = Double.parseDouble(longitude);


        return lon;
    }
}
