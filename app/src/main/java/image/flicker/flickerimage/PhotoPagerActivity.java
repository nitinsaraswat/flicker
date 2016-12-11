package image.flicker.flickerimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import image.flicker.flickerimage.model.Items;

/**
 * Created by Nitin on 09/12/16.
 */

public class PhotoPagerActivity extends AppCompatActivity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    ArrayList<Items> listOfItems;
    TextView titleView;
    ImageButton deleteBtn;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.BLACK);
        titleView = (TextView)toolbar.findViewById(R.id.titleView);
        deleteBtn = (ImageButton)toolbar.findViewById(R.id.deleteBtn);
        mContext=this;

        Intent i = getIntent();
        listOfItems = (ArrayList<Items>) i.getSerializableExtra("list");
        int postion1 = i.getIntExtra("position",0);

        mCustomPagerAdapter = new CustomPagerAdapter(this,MainActivity.listOfItemsStatic);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title = "";
                if(listOfItems.get(position).getTags()!=null && !listOfItems.get(position).getTags().equals(""))
                    title = listOfItems.get(position).getTags();
                else
                    title = listOfItems.get(position).getTitle();

                titleView.setText(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteThisMedia();
            }
        });





        mViewPager.setCurrentItem(postion1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void deleteThisMedia(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!!");
        builder.setMessage("Are you sure to delete image ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        int i = mViewPager.getCurrentItem();
                        MainActivity.listOfItemsStatic.remove(i);
                        mCustomPagerAdapter = new CustomPagerAdapter(mContext,MainActivity.listOfItemsStatic);
                        mViewPager.setAdapter(mCustomPagerAdapter);
                        mViewPager.setCurrentItem(i);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
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
