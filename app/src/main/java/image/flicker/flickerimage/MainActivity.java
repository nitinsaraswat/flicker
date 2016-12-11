package image.flicker.flickerimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import image.flicker.flickerimage.model.Items;
import image.flicker.flickerimage.model.JsonFlickrFeed;
import image.flicker.flickerimage.network.NetworkManager;
import image.flicker.flickerimage.util.Constants;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public GridView gridView;
    public static List<Items> listOfItemsStatic;
    Toolbar toolbar;
    EditText edSearchText;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.gridView);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        edSearchText = (EditText) toolbar.findViewById(R.id.search_view);
        search = (ImageView)toolbar.findViewById(R.id.search_clear);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edSearchText.getText()!=null && !edSearchText.getText().toString().equals(""))
                    searchByTag();
                else
                    Toast.makeText(MainActivity.this,"Please type something to search",Toast.LENGTH_LONG);
            }
        });
        context = this;
        if(NetworkManager.nManager.checkAndShowNetworkAlert(this)) // check network status and show toast
            new LoadImagesFromFlickrTask().execute("");
    }

    private void searchByTag(){
        new LoadImagesFromFlickrTask().execute(edSearchText.getText().toString());
    }



    class LoadImagesFromFlickrTask extends AsyncTask<String, Integer, List> {
        private ProgressDialog progressDialog;
        private Integer totalCount, currentIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading images from Flickr. Please wait...");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(String.format("Loading images from Flickr %s/%s. Please wait...", values[0], values[1]));
        }

        @Override
        protected List doInBackground(String... params) {

            String tag = params[0];

            List<Items> result = new ArrayList();
            try {
                String urlConstatnt=Constants.FLICKR_PUBLIC_URL;

                if(tag!=null && !tag.equals("")){
                    urlConstatnt = Constants.FLICKR_PUBLIC_URL_TAG_SEARCH+tag;
                }
                URL url = new URL(urlConstatnt);
                URLConnection connection = url.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String jsonString  = builder.toString();
                // need to replace some character due to bad json return from flickr
                if(jsonString.toString().contains("jsonFlickrFeed")){
                    jsonString = jsonString.replace("jsonFlickrFeed","");
                    jsonString =jsonString.replace("(","");
                    jsonString =jsonString.replace(")","");
                }
                ObjectMapper obj = new ObjectMapper();
                JsonFlickrFeed jsonFlickrFeed = obj.readValue(jsonString,JsonFlickrFeed.class);
                result = jsonFlickrFeed.getItems();


            }catch(Exception ex){
                Log.d("URL",ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(List s) {
            progressDialog.dismiss();
            List<Items> listOfItems = (ArrayList<Items>)s;
            listOfItemsStatic = (ArrayList<Items>)s;
            ImageAdapter imgAdapter = new ImageAdapter(context,listOfItemsStatic);
            gridView.setAdapter(imgAdapter);
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listOfItemsStatic!=null && listOfItemsStatic.size()>0){
            ImageAdapter imgAdapter = new ImageAdapter(context,listOfItemsStatic);
            gridView.setAdapter(imgAdapter);
        }
    }

}
