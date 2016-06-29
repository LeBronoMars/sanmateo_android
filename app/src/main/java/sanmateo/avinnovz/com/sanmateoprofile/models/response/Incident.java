package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class Incident {

    @SerializedName("reporter_id") private int reportedId;
    @SerializedName("reporter_name") private String reporterName;
    @SerializedName("reporter_contact_no") private String reporterContactNo;
    @SerializedName("reporter_email") private String reporterEmail;
    @SerializedName("reporter_address") private String reportedPicAddress;
    @SerializedName("reporter_pic_url") private String reporterPicUrl;
    @SerializedName("incident_id") private int incidentId;
    private String images;
    private double latitude;
    private double longitude;
    @SerializedName("incident_address") private String incidentAddress;
    @SerializedName("incident_description") private String incidentDescription;
    @SerializedName("incident_status") private String incidentStatus;
    @SerializedName("incident_type") private String incidentType;
    private String remarks;
    @SerializedName("incident_date_reported") private String incidentDateReported;
    @SerializedName("incident_date_updated") private String incidentDateUpdated;
    private String status;

    public int getReportedId() {
        return reportedId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterContactNo() {
        return reporterContactNo;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public String getImages() {
        return images;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getIncidentDateReported() {
        return incidentDateReported;
    }

    public String getIncidentDateUpdated() {
        return incidentDateUpdated;
    }

    public String getStatus() {
        return status;
    }

    public String getReportedPicAddress() {
        return reportedPicAddress;
    }

    public String getReporterPicUrl() {
        return reporterPicUrl;
    }
}
