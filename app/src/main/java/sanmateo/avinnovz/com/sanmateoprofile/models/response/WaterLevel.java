package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 7/10/16.
 */
public class WaterLevel {

    private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String udpatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    @SerializedName("water_level") private double waterLevel;
    private String alert;

    public WaterLevel(int id, String createdAt, String udpatedAt, String deletedAt, double waterLevel, String alert) {
        this.id = id;
        this.createdAt = createdAt;
        this.udpatedAt = udpatedAt;
        this.deletedAt = deletedAt;
        this.waterLevel = waterLevel;
        this.alert = alert;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUdpatedAt() {
        return udpatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public String getAlert() {
        return alert;
    }
}
