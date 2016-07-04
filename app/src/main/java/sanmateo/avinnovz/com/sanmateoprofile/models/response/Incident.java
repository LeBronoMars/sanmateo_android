package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class Incident implements Parcelable {

    @SerializedName("reporter_id") private int reporterId;
    @SerializedName("reporter_name") private String reporterName;
    @SerializedName("reporter_contact_no") private String reporterContactNo;
    @SerializedName("reporter_email") private String reporterEmail;
    @SerializedName("reporter_address") private String reportedAddress;
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

    public int getReporterId() {
        return reporterId;
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

    public String getReportedAddress() {
        return reportedAddress;
    }

    public String getReporterPicUrl() {
        return reporterPicUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.reporterId);
        parcel.writeString(this.reporterName);
        parcel.writeString(this.reporterContactNo);
        parcel.writeString(this.reporterEmail);
        parcel.writeString(this.reportedAddress);
        parcel.writeString(this.reporterPicUrl);
        parcel.writeInt(this.incidentId);
        parcel.writeString(this.images);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeString(this.incidentAddress);
        parcel.writeString(this.incidentDescription);
        parcel.writeString(this.incidentStatus);
        parcel.writeString(this.incidentType);
        parcel.writeString(this.remarks);
        parcel.writeString(this.incidentDateReported);
        parcel.writeString(this.incidentDateUpdated);
        parcel.writeString(this.status);
    }

    public Incident(Parcel in) {
        this.reporterId = in.readInt();
        this.reporterName = in.readString();
        this.reporterContactNo = in.readString();
        this.reporterEmail = in.readString();
        this.reportedAddress = in.readString();
        this.reporterPicUrl = in.readString();
        this.incidentId = in.readInt();
        this.images = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.incidentAddress = in.readString();
        this.incidentDescription = in.readString();
        this.incidentStatus = in.readString();
        this.incidentType = in.readString();
        this.remarks = in.readString();
        this.incidentDateReported = in.readString();
        this.incidentDateUpdated = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Incident createFromParcel(Parcel in) {
            return new Incident(in);
        }

        public Incident[] newArray(int size) {
            return new Incident[size];
        }
    };
}
