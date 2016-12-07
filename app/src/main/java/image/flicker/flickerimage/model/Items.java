package image.flicker.flickerimage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Nitin on 07/12/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Items implements Serializable {

    private Media media;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }


}
