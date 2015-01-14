package optimus.newsreader.app.loaderImage;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import optimus.newsreader.app.R;
import optimus.newsreader.app.rss.RssItem;

/**
 * Created by N56 on 5/10/2014.
 */
public class LoaderImageFagment extends Fragment {

    protected DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();


    /**
     * Adapter Rss List
     */
    protected class ListViewAdapter extends ArrayAdapter<RssItem> {

        private Context mContext;
        private List<RssItem> rssItems = new ArrayList<RssItem>();
        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        public ListViewAdapter(Context context, List<RssItem> items) {
            super(context, R.layout.rss_item, items);
            // TODO Auto-generated constructor stub
            mContext = context;
            rssItems = items;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            ViewHolder holder = null;

            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                rowView = inflater.inflate(R.layout.rss_item, parent, false);

                holder = new ViewHolder();
                holder.title = (TextView) rowView.findViewById(R.id.textView_title);
                holder.image = (ImageView) rowView.findViewById(R.id.imageView);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }


            RssItem obj = rssItems.get(position);

            holder.title.setText(obj.getTitle());
            imageLoader.displayImage(obj.getUrlImage(), holder.image, options, animateFirstListener);

            return rowView;
        }

        protected class ViewHolder {
            protected TextView title;
            protected ImageView image;
        }

    }



    protected static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
