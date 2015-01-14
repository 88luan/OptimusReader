package optimus.newsreader.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import optimus.newsreader.app.R;
import optimus.newsreader.app.rss.RssHandler;
import optimus.newsreader.app.rss.RssItem;


/**
 * gamethu.vnexpress.net
 */
public class Fragment2 extends Fragment1 {


    private List<RssItem> rssItems = new ArrayList<RssItem>();
    private RssHandler rssHandler = new RssHandler();

	public Fragment2()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.fragment_layout_two,container, false);

        urlPage = getArguments().getString(URL_PAGE);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))    //Tròn ở 4 góc
                .build();

        new LoadRss(rssItems,rssHandler).execute(urlPage);

        return view;
	}


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String linkRssItem = rssItems.get(i).getLink();

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(LINK_RSS_DETAIL, linkRssItem);
        intent.putExtra(URL_PAGE, urlPage);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
