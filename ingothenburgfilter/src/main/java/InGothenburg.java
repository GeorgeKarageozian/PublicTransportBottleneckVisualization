import org.json.JSONObject;

public class InGothenburg {
    public InGothenburg() {

    }

    public String getLongitude(String json) {

        String longitude = json.substring(13, json.indexOf(",") - 1);

        return longitude;
    }

    public String getLatitude(String json) {

        String latitude = json.substring(json.lastIndexOf(":") + 1, json.length() - 1);

        return latitude;

    }

    public double getLatitudeDouble(String json) {

        JSONObject jsonObject = new JSONObject(json);

        Double latitude = jsonObject.getDouble("latitude");


        return latitude;

    }

    public double getLongitudeDouble(String json) {
        JSONObject jsonObject = new JSONObject(json);

        Double longitude = jsonObject.getDouble("longitude");


        return longitude;
    }
}

