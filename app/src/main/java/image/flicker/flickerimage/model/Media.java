package image.flicker.flickerimage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Nitin on 07/12/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Media implements Serializable {
    private String m;
    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }


}
