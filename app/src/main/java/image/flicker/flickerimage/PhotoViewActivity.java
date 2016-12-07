package image.flicker.flickerimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;


public class PhotoViewActivity extends AppCompatActivity {

    private ImageButton deleteButton;
    private String mediaUrl,directoryId,groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        PhotoView photoView = (PhotoView)findViewById(R.id.photoView);

        String imageUrl = getIntent().getExtras().getString("imageUri");
        ImageLoader.getInstance().displayImage(imageUrl,photoView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


