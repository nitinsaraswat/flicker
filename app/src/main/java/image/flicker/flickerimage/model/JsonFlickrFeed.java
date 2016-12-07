package image.flicker.flickerimage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nitin on 07/12/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class JsonFlickrFeed implements Serializable {

    private ArrayList<Items> items;
    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }


}
