package image.flicker.flickerimage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Nitin on 07/12/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrJsonObject  implements Serializable {
    private JsonFlickrFeed jsonFlickrFeed;
    public JsonFlickrFeed getJsonFlickrFeed() {
        return jsonFlickrFeed;
    }

    public void setJsonFlickrFeed(JsonFlickrFeed jsonFlickrFeed) {
        this.jsonFlickrFeed = jsonFlickrFeed;
    }
}
