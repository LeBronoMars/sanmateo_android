package sanmateo.avinnovz.com.sanmateoprofile.models.others;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AppMarker {

    double latitude;
    double longitude;
    String title;
    String snippet;
    String imageUrl;

    public AppMarker(LatLng latLng, String title, String snippet, String imageUrl) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.title = title;
        this.snippet = snippet;
        this.imageUrl = imageUrl;
    }

    public AppMarker(double latitude, double longitude, String title, String snippet, String imageUrl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.snippet = snippet;
        this.imageUrl = imageUrl;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public void setLatlng(LatLng latlng) {
        this.latitude = latlng.latitude;
        this.longitude = latlng.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
