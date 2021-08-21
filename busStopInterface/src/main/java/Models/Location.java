package Models;

public class Location {

    String id;
    String extId;
    String name;
    float lon;
    float lat;
    int weight;
    int products;

    public Location(String id, String extId, String name, float lon, float lat, int weight, int products) {
        this.id = id;
        this.extId = extId;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.weight = weight;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }
}
