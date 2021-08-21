package models;

public class OnWaterResponse {
    private String query;
    private String request_id;
    private double lat;
    private double lon;
    private String water;

    public OnWaterResponse(String query, String request_id, double lat, double lon, String water) {
        this.query = query;
        this.request_id = request_id;
        this.lat = lat;
        this.lon = lon;
        this.water = water;
    }


    public String getWater() {
        return water;
    }
}
