package optimus.newsreader.app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import optimus.newsreader.app.loaderImage.LoaderImageFagment;
import optimus.newsreader.app.rss.RssHandler;
import optimus.newsreader.app.rss.RssItem;


/**
 * playpark.vn
 */
public class Fragment1 extends LoaderImageFagment implements AdapterView.OnItemClickListener {

    public static final String LINK_RSS_DETAIL = "LINK_RSS_DETAIL";
	public static final String URL_PAGE = "urlWebPage";

    protected ListView listView;
    private List<RssItem> rssItems = new ArrayList<RssItem>();
    private RssHandler rssHandler = new RssHandler();
    protected String urlPage;


	public Fragment1() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_one, container,
				false);

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


    protected class LoadRss extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog;

        private List<RssItem> rssItems1 = new ArrayList<RssItem>();
        private RssHandler rssHandler1 = new RssHandler();

        public LoadRss(List<RssItem> items, RssHandler handler) {

            rssHandler1 = handler;
            rssItems1 = items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = Fragment1.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }


        @Override
        protected Void doInBackground(String... strings) {

            rssHandler1.feedRss(strings[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            rssItems1.addAll(rssHandler1.getListRssItem());
            if(!rssItems1.isEmpty()) {
                ListViewAdapter adapter = new ListViewAdapter(getActivity(), rssItems1);
                listView.setAdapter(adapter);
            }

            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }


    /**
     * custom ProgressDialog không có title, không có phần message, chỉ có icon
     * @param mContext
     * @return ProgressDialog
     */
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (Exception e) {
        }
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.progress_dialog);
        // dialog.setMessage(Message);
        return dialog;
    }

}
