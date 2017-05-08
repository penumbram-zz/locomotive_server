package app.rest.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;

/**
 * Created by tolgacaner on 07/05/2017.
 */
public class GameMapGenerator {


    private static final Double r_earth = Double.valueOf(6378000); //meters


    public static List<Prize> getPrizes(Game game) {
        List<Prize> result = new ArrayList<>();

        Double latitude = game.getLatitude();
        Double longitude = game.getLongitude();
        Double radius = game.getRadius();

        Random random = new Random();

        Double rangeLatMin = latitude  + (-radius / r_earth) * (180 / Math.PI); //longitude + (dx / r_earth) * (180 / pi) / cos(latitude * pi/180);
        Double rangeLatMax = latitude  + (radius / r_earth) * (180 / Math.PI);

        Double rangeLonMin = longitude + (-radius / r_earth) * (180 / Math.PI) / cos(latitude * Math.PI/180); //
        Double rangeLonMax = longitude + (radius / r_earth) * (180 / Math.PI) / cos(latitude * Math.PI/180);

        while (result.size() < 10) {
            Double randomLatValue = rangeLatMin + (rangeLatMax - rangeLatMin) * random.nextDouble();
            Double randomLonValue = rangeLonMin + (rangeLonMax - rangeLonMin) * random.nextDouble();
            if (DistanceCalculator.distance(latitude,longitude,randomLatValue, randomLonValue) < radius) {
                Prize prize = new Prize(randomLatValue,randomLonValue);
                result.add(prize);
            }
        }
        return result;
    }
}
