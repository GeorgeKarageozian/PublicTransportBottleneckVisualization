package models;

public class LatLon {

    private String originLatitude;
    private String originLongitude;
    private String originSuburb;
    private String destinationLatitude;
    private String destinationLongitude;
    private String destinationSuburb;

    public LatLon(String originLatitude, String originLongitude, String originSuburb, String destinationLatitude, String destinationLongitude, String destinationSuburb) {
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.originSuburb = originSuburb;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.destinationSuburb = destinationSuburb;
    }

    public String getOriginSuburb() {
        return originSuburb;
    }

    public String getDestinationSuburb() {
        return destinationSuburb;
    }

    public String getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(String lat) {
        this.originLongitude = lat;
    }

    public String getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(String lon) {
        this.originLongitude = lon;
    }

    public String getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public String getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }
}
