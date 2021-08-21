package se.gu.cse.dit355.example;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RequestGenerator {

    // different sized areas
    // largest - OUTER
    // smallest - CENTRE

    final double OUTER_START_LATITUDE = 57.565193;
    final double OUTER_START_LONGITUDE = 11.32426;
    final double OUTER_END_LATITUDE = 58.123424;
    final double OUTER_END_LONGITUDE = 13.029501;

    final double MIDDLE_START_LATITUDE = 57.685918;
    final double MIDDLE_START_LONGITUDE = 11.890759;
    final double MIDDLE_END_LATITUDE = 57.74037;
    final double MIDDLE_END_LONGITUDE = 12.023547;

    final double CENTRE_START_LATITUDE = 57.691702;
    final double CENTRE_START_LONGITUDE = 11.932653;
    final double CENTRE_END_LATITUDE = 57.705588;
    final double CENTRE_END_LONGITUDE = 11.986142;

    // this is entered as a command line argument.

    // the default is 'standard' and the special case is 'special'
     String REQUEST_CASE;


    //private final long DEVICE_ID;
    private long deviceId;
    private long requestId;

    // different purposes for travelling
    String[] purposes = new String[3];

    public RequestGenerator(long deviceId) {
        purposes[0] = "work";
        purposes[1] = "school";
        purposes[2] = "leisure";
        requestId = 1;
        this.deviceId = deviceId;
        this.REQUEST_CASE = "standard";
    }

    public String getREQUEST_CASE() {
        return REQUEST_CASE;
    }

    public void setREQUEST_CASE(String REQUEST_CASE) {
        this.REQUEST_CASE = REQUEST_CASE;
    }

    public RequestGenerator(long deviceId, String requestCase) {
        purposes[0] = "work";
        purposes[1] = "school";
        purposes[2] = "leisure";
        requestId = 1;
        this.deviceId = deviceId;
        this.REQUEST_CASE = requestCase;
    }

    // All origin coordinates will be for the entire region, for both cases
    public JsonObject getRandomOrigin() {
        JsonObject origin = new JsonObject();
        // creates a random longitude and latitude, between the two parameters
        double latitude = ThreadLocalRandom.current().nextDouble(OUTER_START_LATITUDE, OUTER_END_LATITUDE);
        double longitude = ThreadLocalRandom.current().nextDouble(OUTER_START_LONGITUDE, OUTER_END_LONGITUDE);

        origin.addProperty("latitude", latitude);
        origin.addProperty("longitude", longitude);
        origin.addProperty("suburb", "");
        return origin;
    }

    // Standard case - the destination coordinates will be all over. However, there is less chance of a coordinate in the outer region.
    // Special case - the destination coordinates are generated just in the centre.
    public JsonObject getRandomDestination() {
        JsonObject destination = new JsonObject();
        double latitude;
        double longitude;

        if (this.REQUEST_CASE.equals("special")) {
            latitude = ThreadLocalRandom.current().nextDouble(CENTRE_START_LATITUDE, CENTRE_END_LATITUDE);
            longitude = ThreadLocalRandom.current().nextDouble(CENTRE_START_LONGITUDE, CENTRE_END_LONGITUDE);
        } else {
            int a = ThreadLocalRandom.current().nextInt(1, 4);
            if (a == 1) {
                latitude = ThreadLocalRandom.current().nextDouble(OUTER_START_LATITUDE, OUTER_END_LATITUDE);
                longitude = ThreadLocalRandom.current().nextDouble(OUTER_START_LONGITUDE, OUTER_END_LONGITUDE);
            } else {
                latitude = ThreadLocalRandom.current().nextDouble(MIDDLE_START_LATITUDE, MIDDLE_END_LATITUDE);
                longitude = ThreadLocalRandom.current().nextDouble(MIDDLE_START_LONGITUDE, MIDDLE_END_LONGITUDE);
            }
        }
        destination.addProperty("latitude", latitude);
        destination.addProperty("longitude", longitude);
        destination.addProperty("suburb", "");
        return destination;
    }
    
    // this is for both types of requests, adds: timeStamp, deviceId, requestId, purpose and issuance
    public JsonObject generateRequestsData() throws IOException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = timestamp.toString();
        Long epochTime = new Date().getTime();

        // the purpose is always 'leisure' for the special case
        int randomPurpose;
        if (this.REQUEST_CASE.equals("special")) {
            randomPurpose = 2;
        } else {
            // randomly selects a purpose from the list
            randomPurpose = ThreadLocalRandom.current().nextInt(0, purposes.length);
        }

        String purpose = purposes[randomPurpose];

        // builds the message as a JSON object
        JsonObject requestObject = new JsonObject();

        requestObject.addProperty("deviceId", this.deviceId);
        requestObject.addProperty("requestId", this.requestId);
        requestObject.addProperty("timeOfDeparture", time);
        requestObject.addProperty("purpose", purpose);
        requestObject.addProperty("issuance", epochTime);

        JsonObject origin = getRandomOrigin();
        requestObject.add("origin", origin);
        JsonObject destination = getRandomDestination();
        requestObject.add("destination", destination);

        requestId++;

        return requestObject;
    }


}
