package optimus.newsreader.app.rss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by N56 on 5/10/2014.
 */
public class RssHandler {

    private List<RssItem> rssItems = new ArrayList<RssItem>();
    private RssItem item = new RssItem();

    public void feedRss(String urlString) {

        try {
            URL url = new URL(urlString);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            if(getInputStream(url) != null) {
                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                            if (item == null) {
                                item = new RssItem();
                            }
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                item.setTitle(xpp.nextText()); //extract the headline
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem)
                                item.setLink(xpp.nextText()); //extract the link of article
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                String desc = xpp.nextText();

                                item.setDescription(getTextDescription(desc));
                                item.setUrlImage(getUrlImage(desc));
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        rssItems.add(item);
                        //hủy item sau đó tạo lại item mới, nếu không hủy item thì rssItems chỉ add 1 item (ở cuối cùng)
                        item = null;
                    }

                    eventType = xpp.next(); //move to next element
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<RssItem> getListRssItem() {
        return rssItems;
    }


    private InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    private String getTextDescription(String description) {
        String desc = null;
        int start = description.indexOf("</a> ") + 5;
        desc = description.substring(start);
        return desc;
    }


    private String getUrlImage(String description) {
        String img = null;
        //parse description for any image or video links
        if (description.contains("<img ")) {
            img = description.substring(description.indexOf("<img "));
            String cleanUp = img.substring(0, img.indexOf(">") + 1);
            img = img.substring(img.indexOf("src=") + 5);
            int indexOf = img.indexOf("'");
            if (indexOf == -1) {
                indexOf = img.indexOf("\"");
            }
            img = img.substring(0, indexOf);

        }
        return img;
    }


    private Bitmap decodeBitmap(String url) {
        Bitmap img = null;
        if (url != null) {
            try {
                URL feedImage = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) feedImage.openConnection();
                InputStream is = conn.getInputStream();
                img = BitmapFactory.decodeStream(is);
            } catch (IOException e) {

            }
        }
        return img;
    }

}
