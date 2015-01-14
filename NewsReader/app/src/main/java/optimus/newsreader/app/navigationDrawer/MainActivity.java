package optimus.newsreader.app.navigationDrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import optimus.newsreader.app.R;
import optimus.newsreader.app.activities.Fragment1;
import optimus.newsreader.app.activities.Fragment2;
import optimus.newsreader.app.activities.Fragment3;
import optimus.newsreader.app.activities.Fragment4;
import optimus.newsreader.app.utilisation.ThemeManager;


public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private CustomDrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);


        // Initializing
        mDrawerList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to mDrawerList
        mDrawerList.add(new DrawerItem("Loại báo")); // adding a header to the list
        mDrawerList.add(new DrawerItem("Xã hội", "http://news.zing.vn/RSS/xa-hoi.rss"));
        mDrawerList.add(new DrawerItem("Thế giới", "http://news.zing.vn/RSS/the-gioi.rss"));
        mDrawerList.add(new DrawerItem("Thị trường", "http://news.zing.vn/RSS/thi-truong.rss"));
        mDrawerList.add(new DrawerItem("Thể thao", "http://news.zing.vn/RSS/the-thao.rss"));
        mDrawerList.add(new DrawerItem("Sống trẻ",  "http://news.zing.vn/RSS/song-tre.rss"));
        mDrawerList.add(new DrawerItem("Pháp luật", "http://news.zing.vn/RSS/phap-luat.rss"));
        mDrawerList.add(new DrawerItem("Tin sách", "http://news.zing.vn/RSS/the-gioi-sach.rss"));
        mDrawerList.add(new DrawerItem("Âm nhạc",  "http://news.zing.vn/RSS/am-nhac.rss"));
        mDrawerList.add(new DrawerItem("Phim ảnh",  "http://news.zing.vn/RSS/phim-anh.rss"));
        mDrawerList.add(new DrawerItem("Thời trang",  "http://news.zing.vn/RSS/thoi-trang.rss"));
        mDrawerList.add(new DrawerItem("Công nghệ", "http://news.zing.vn/RSS/cong-nghe.rss"));
        mDrawerList.add(new DrawerItem("Xe 360",  "http://news.zing.vn/RSS/oto-xe-may.rss"));
        mDrawerList.add(new DrawerItem("Khám phá",  "http://news.zing.vn/RSS/kham-pha.rss"));
        mDrawerList.add(new DrawerItem("Du lịch",  "http://news.zing.vn/RSS/du-lich.rss"));

        mDrawerList.add(new DrawerItem("Cài đặt")); // adding a header to the list
        mDrawerList.add(new DrawerItem(true)); // adding a toggle button to the list
        mDrawerList.add(new DrawerItem("About", null));

        mAdapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                mDrawerList);

        mDrawerListView.setAdapter(mAdapter);

        mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(!hasConnection()){
            showDialogInfoInternet();
        } else {

            if (savedInstanceState == null) {
                SelectItem(1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 1:
                fragment = new Fragment1();
                args.putString(Fragment1.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 2:
                fragment = new Fragment2();
                args.putString(Fragment2.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 3:
                fragment = new Fragment3();
                args.putString(Fragment3.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 4:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 5:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 6:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 7:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 8:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 9:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 10:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 11:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 12:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;

            case 13:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 14:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 15:
                fragment = new Fragment4();
                args.putString(Fragment4.URL_PAGE, mDrawerList.get(possition)
                        .getUrlPage());
                break;
            case 16:
                //Tiêu đề "Tùy chỉnh"
                break;
            case 17:
                //Toggle button
                break;
            case 18:
                showDialogAbout();
                break;
            default:
                break;
        }

        if(possition!=18) {
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                    .commit();
        }

        mDrawerListView.setItemChecked(possition, true);
        setTitle(mDrawerList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerListView);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mDrawerList.get(position).getTitle() == null) {
                SelectItem(position);
            }

        }
    }





    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title, int icon, int message, boolean checkInternet) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            args.putInt("icon", icon);
            args.putInt("message", message);
            args.putBoolean("checkInternet", checkInternet);
            frag.setArguments(args);

            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");
            int icon = getArguments().getInt("icon");
            int message = getArguments().getInt("message");
            final boolean checkInternet = getArguments().getBoolean("checkInternet");

            return new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Dialog)
                    .setIcon(icon)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    if(checkInternet){
                                        ((MainActivity) getActivity()).finish();
                                    }
                                }
                            }
                    )
                    .create();
        }
    }

    void showDialogAbout() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.title_about, android.R.drawable.ic_dialog_info, R.string.message_about, false);
        newFragment.show(getFragmentManager(), "dialog");
    }

    void showDialogInfoInternet() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.title_info_internet, android.R.drawable.ic_dialog_alert, R.string.message_info_internet,true);
        newFragment.show(getFragmentManager(), "dialog");
    }


    /**
     * Checks if the device has Internet connection.
     *
     * @return <code>true</code> if the phone is connected to the Internet.
     */
    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
