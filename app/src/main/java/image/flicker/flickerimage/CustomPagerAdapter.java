package image.flicker.flickerimage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import image.flicker.flickerimage.model.Items;
import uk.co.senab.photoview.PhotoView;

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<Items> listOfMedia;


    public CustomPagerAdapter(Context context, List<Items> listOfImage) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listOfMedia = listOfImage;
    }

    @Override
    public int getCount() {
        return listOfMedia.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.imageView);
        //imageView.setImageResource(listOfMedia[position]);
        ImageLoader.getInstance().displayImage(listOfMedia.get(position).getMedia().getM(),imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
