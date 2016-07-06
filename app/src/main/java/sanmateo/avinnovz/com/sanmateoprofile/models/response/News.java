package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class News implements Parcelable {

    private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    private String body;

    public News(int id, String createdAt, String updatedAt, String deletedAt, String title, String body) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.updatedAt);
        parcel.writeString(this.deletedAt);
        parcel.writeString(this.title);
        parcel.writeString(this.body);
    }

    public News(Parcel parcel) {
        this.id = parcel.readInt();
        this.createdAt = parcel.readString();
        this.updatedAt = parcel.readString();
        this.deletedAt = parcel.readString();
        this.title = parcel.readString();
        this.body = parcel.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
