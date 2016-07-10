package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class WaterLevelSingleton {

    private ArrayList<WaterLevel> waterLevels = new ArrayList<>();

    private static WaterLevelSingleton WATER_LEVEL = new WaterLevelSingleton();

    private WaterLevelSingleton() {}

    public static WaterLevelSingleton getInstance() {
        return WATER_LEVEL;
    }

    public ArrayList<WaterLevel> getWaterLevels() {
        return waterLevels;
    }

    public void setIncidents(ArrayList<WaterLevel> waterLevels) {
        this.waterLevels.clear();
        this.waterLevels.addAll(waterLevels);
    }
}
