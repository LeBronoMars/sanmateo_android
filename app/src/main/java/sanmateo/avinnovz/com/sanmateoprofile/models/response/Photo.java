package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by ctmanalo on 8/3/16.
 */
@Data
public class Photo {

    private String id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    @SerializedName("image_url") private String imageUrl;
    private String description;
}
