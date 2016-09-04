package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 9/4/16.
 */
public class ForReviewIncident {

    @SerializedName("uploader_id") int uploaderId;
    @SerializedName("uploader_name") String uploaderName;
    @SerializedName("uploader_email") String uploaderEmail;
    @SerializedName("uploader_contact_no") String uploaderContact;
    @SerializedName("uploader_gender") String uploaderGender;
    @SerializedName("uploader_address") String uploaderAddress;
    @SerializedName("uploader_pic_url") String uploaderPicUrl;
    @SerializedName("reporter_id") int reportedId;
    @SerializedName("reporter_name") String reporterName;
    @SerializedName("reporter_email") String reporterEmail;
    @SerializedName("reporter_address") String reporterAddress;
    @SerializedName("reporter_contact_no") String reporterContactNo;
    @SerializedName("reporter_gender") String reporterGender;
    @SerializedName("reporter_pic_url") String reporterPicUrl;
    @SerializedName("incident_id") int incidentId;
    @SerializedName("report_status") String reportStatus;
    @SerializedName("created_at") String createdAt;
    @SerializedName("remarks") String remarks;
    @SerializedName("incident_type") String incidentType;
    @SerializedName("incident_description") String incidentDescription;
    @SerializedName("incident_images") String incidentImages;
    @SerializedName("incident_status") String incidentStatus;
    @SerializedName("incident_address") String incidentAddress;
    @SerializedName("incident_latitude") long incidentLatitude;
    @SerializedName("incident_longitude") long incidentLongitude;

    public int getUploaderId() {
        return uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public String getUploaderContact() {
        return uploaderContact;
    }

    public String getUploaderGender() {
        return uploaderGender;
    }

    public String getUploaderAddress() {
        return uploaderAddress;
    }

    public String getUploaderPicUrl() {
        return uploaderPicUrl;
    }

    public int getReportedId() {
        return reportedId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getReporterAddress() {
        return reporterAddress;
    }

    public String getReporterContactNo() {
        return reporterContactNo;
    }

    public String getReporterGender() {
        return reporterGender;
    }

    public String getReporterPicUrl() {
        return reporterPicUrl;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public String getIncidentImages() {
        return incidentImages;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public long getIncidentLatitude() {
        return incidentLatitude;
    }

    public long getIncidentLongitude() {
        return incidentLongitude;
    }
}
