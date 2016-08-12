package sanmateo.avinnovz.com.sanmateoprofile.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ctmanalo on 8/9/16.
 */
public class GalleryPhoto {

    @SerializedName("id") private String galleryId;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at")  private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    @SerializedName("image_url") private String imageUrl;
    private String description;

    public GalleryPhoto() {};

    public GalleryPhoto(String galleryId, String createdAt, String updatedAt, String deletedAt,
                        String title, String imageUrl, String description) {
        this.galleryId = galleryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
