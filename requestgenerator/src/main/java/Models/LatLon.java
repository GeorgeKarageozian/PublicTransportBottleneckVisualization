package Models;

public class LatLon {

    private Double originLatitude;
    private Double originLongitude;
    private String originSuburb;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private String destinationSuburb;


    public LatLon(Double originLatitude, Double originLongitude, String originSuburb, Double destinationLatitude, Double destinationLongitude, String destinationSuburb) {
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

    public Double getOriginLatitude() {
        return originLatitude;
    }


    public Double getOriginLongitude() {
        return originLongitude;
    }



    public Double getDestinationLatitude() {
        return destinationLatitude;
    }



    public Double getDestinationLongitude() {
        return destinationLongitude;
    }


}
