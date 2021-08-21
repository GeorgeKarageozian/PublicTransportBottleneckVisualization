package generator;

import java.util.concurrent.ThreadLocalRandom;

public class generator {
    final double END_LATITUDE = 58.267498702361046;
    final double START_LATITUDE = 57.344271907026375;
    final double START_LONGITUDE = 11.511293511596591;
    final double END_LONGITUDE = 12.399812798705966;

public generator(){

}


public double getRandomLatidude (){
    double originLatitude = ThreadLocalRandom.current().nextDouble(START_LATITUDE, END_LATITUDE);
return originLatitude;

}
public double getRandomLongitude() {
    double originLongitude = ThreadLocalRandom.current().nextDouble(START_LONGITUDE, END_LONGITUDE);
return originLongitude;
}

public String getCoordinate (){
    double latitude = getRandomLatidude();
    double longitude = getRandomLongitude();
    String coordinate = "{\"longitude\":"+ longitude +
            ",\"latitude\":"+ latitude +"}";
    return coordinate;

}

    public static void main(String[] args) {
        generator g = new generator();
        String S = g.getCoordinate();
        System.out.println(S);
    }
}
