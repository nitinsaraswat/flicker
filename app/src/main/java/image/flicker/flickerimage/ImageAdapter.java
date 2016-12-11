package image.flicker.flickerimage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.ArrayList;
import java.util.List;

import image.flicker.flickerimage.model.Items;


/**
 * Created by Nitin on 07/11/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Items> listOfMedia;
    ViewHolder holder;
    int pos;

    // Constructor
    public ImageAdapter(Context c, List<Items> listOfImage) {
        mContext = c;
        listOfMedia = listOfImage;


    }

    public int getCount() {
        return listOfMedia.size();
    }

    public Object getItem(int position) {
        return listOfMedia.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        pos = position;
        View v=null;

        if (convertView == null) {
            LayoutInflater mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mInflator.inflate(R.layout.image_adapter, null,false);


        }
        else{
            v = convertView;
        }

        holder = new ViewHolder();
        holder.imgView = (ImageView) v.findViewById(R.id.picture);
        holder.userName = (TextView) v.findViewById(R.id.textName);
        holder.pBar = (ProgressBar) v.findViewById(R.id.progress);
        holder.imgView.setTag(listOfMedia.get(position).getMedia().getM());

        if(listOfMedia.get(position).getTags()!=null && !listOfMedia.get(position).getTags().equals(""))
            holder.userName.setText(listOfMedia.get(position).getTags());
        else
            holder.userName.setText(listOfMedia.get(position).getTitle());
        List<Bitmap> bitmaps = MemoryCacheUtils.findCachedBitmapsForImageUri(holder.imgView.getTag().toString(),
                ImageLoader.getInstance().getMemoryCache());
        holder.pBar.setVisibility(View.VISIBLE);
        if (bitmaps.size() != 0) {
            loadImage(holder.imgView.getTag().toString(), holder.imgView, false, holder.pBar,position);
        }else{
            loadImage(holder.imgView.getTag().toString(), holder.imgView, true, holder.pBar,position);
        }
        holder.imgView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        mContext);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete image ?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here

                        Log.d("Postion ",position+"");
                        listOfMedia.remove(position);
                        //MainActivity.listOfItemsStatic.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();


                return true;
            }
        });
        return v;
    }





    public static class ViewHolder {
        public TextView userName;
        public ImageView imgView;
        public ProgressBar pBar;
    }







    public void loadImage(final String imageUri, final ImageView associateImgVw, Boolean loadAsync, final ProgressBar progressBar,final int position) {
        if (!loadAsync) {
            if(imageUri.equalsIgnoreCase(associateImgVw.getTag().toString())) {
                Bitmap loadedImage = ImageLoader.getInstance().loadImageSync(imageUri);
                associateImgVw.setImageBitmap(loadedImage);
                holder.pBar.setVisibility(View.GONE);
                associateImgVw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (imageUri.equalsIgnoreCase(associateImgVw.getTag().toString())) {
                            Intent myIntent = new Intent(mContext, PhotoPagerActivity.class);
                            ArrayList<Items> itemArrlist = (ArrayList<Items>) listOfMedia;
                            myIntent.putExtra("list",itemArrlist);
                            myIntent.putExtra("position",position);
                            mContext.startActivity(myIntent);

                        }
                    }
                });
            }
        } else {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(imageUri, associateImgVw, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    //associateImgVw.setImageResource(R.drawable.no_image);
                    progressBar.setVisibility(LinearLayout.INVISIBLE);
                }

                @Override
                public void onLoadingComplete(final String imageUri, View view, Bitmap loadedImage) {
                    if(imageUri.equals(associateImgVw.getTag().toString())) {

                        associateImgVw.setVisibility(View.VISIBLE);
                        associateImgVw.setImageBitmap(loadedImage);
                        progressBar.setVisibility(View.GONE);
                        associateImgVw.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (imageUri.equalsIgnoreCase(associateImgVw.getTag().toString())) {
                                    Intent myIntent = new Intent(mContext, PhotoPagerActivity.class);
                                    //myIntent.putExtra("imageUri", imageUri); //Optional parameters
                                    ArrayList<Items> itemArrlist = (ArrayList<Items>) listOfMedia;
                                    myIntent.putExtra("list",itemArrlist);
                                    myIntent.putExtra("position",position);
                                    mContext.startActivity(myIntent);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }
    }





}


