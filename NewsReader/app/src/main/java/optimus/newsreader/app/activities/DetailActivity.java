package optimus.newsreader.app.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import optimus.newsreader.app.R;
import optimus.newsreader.app.utilisation.SharePreferenceManager;


public class DetailActivity extends ActionBarActivity {

    String link;
    String urlPage;
    private WebView myWebView;
    SharePreferenceManager sharePreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharePreferenceManager = SharePreferenceManager.getInstance(this);
        if(sharePreferenceManager.getBoolean("THEME_LIGHT",true)){
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        } else{
            setTheme(R.style.Theme_AppCompat);
        }

        setContentView(R.layout.activity_detail);

        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setBackgroundColor(0x00000000);
        myWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //làm cho nội dung của Web fit với chiều ngang của màn hình
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        link = getIntent().getStringExtra(Fragment1.LINK_RSS_DETAIL);
        urlPage = getIntent().getStringExtra(Fragment1.URL_PAGE);

        new RequestTask().execute(link);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public String getHttpRespond(String link){

        /*
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String result = null;
        try {
            response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);
                Log.w("response", responseString);
                result = getBodyContent(responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        */



        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(link);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        String resultBody = getBodyContent(result.toString());
        //nếu nền đen thì thay đổi font chữ thành màu trắng
        if(!sharePreferenceManager.getBoolean("THEME_LIGHT",true)){
            resultBody ="<font color=\"White\">" +result+ "</font>";
        }

        return resultBody;
    }


    public String getBodyContent(String parent){
        StringBuilder builder = new StringBuilder(parent);
        int start = 0, end = 0;

//        if(urlPage.equals("http://playpark.vn/rss.html")) {
//             start = builder.indexOf("<div id=\"UCNewsDetail1_Title\" style=\"font-size: 18px; font-weight: bold; padding-top: 20px; padding-bottom: 10px; clear: both\">");
//             end = builder.indexOf("<div class=\"viewsubcontent\">");
//
//        } else if(urlPage.equals("http://gamethu.vnexpress.net/rss/gt/diem-tin.rss")){
//            start = builder.indexOf("<h1 class=Title>");
//            end = builder.indexOf("<P class=Normal align=right>");
//
//        } else if(urlPage.equals("http://news.zing.vn/RSS/thi-truong.rss")) {
            start = builder.indexOf("<h1 itemprop=\"name\">");
            end = builder.indexOf("<p class=\"lastupdate\">");

//        } else if(urlPage.equals("https://vn.news.yahoo.com/rss/bongdatrongnuoc")) {
//            start = builder.indexOf("<p class=\"first\">");
//            end = builder.indexOf("<p><strong></strong></p>");
//
//        }

        return builder.substring(start, end);
    }


    private class RequestTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = Fragment1.createProgressDialog(DetailActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... url) {

            return getHttpRespond(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            myWebView.loadData(result, "text/html; charset=UTF-8", null);

            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

}
