package optimus.newsreader.app.navigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import optimus.newsreader.app.R;
import optimus.newsreader.app.utilisation.SharePreferenceManager;
import optimus.newsreader.app.utilisation.ThemeManager;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;
    SharePreferenceManager sharePreferenceManager;

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;
        sharePreferenceManager = SharePreferenceManager.getInstance(context);
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

	
		DrawerItemHolder drawerHolder;
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view
					.findViewById(R.id.drawer_itemName);

			drawerHolder.toggleButton = (ToggleButton) view
					.findViewById(R.id.toggleButton);

			drawerHolder.title = (Button) view.findViewById(R.id.drawerTitle);

			drawerHolder.headerLayout = (LinearLayout) view
					.findViewById(R.id.headerLayout);
			drawerHolder.itemLayout = (LinearLayout) view
					.findViewById(R.id.itemLayout);
			drawerHolder.toggleLayout = (LinearLayout) view
					.findViewById(R.id.toggleLayout);

			view.setTag(drawerHolder);

		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

		if (dItem.isToggleButton()) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.toggleLayout.setVisibility(LinearLayout.VISIBLE);

            if(sharePreferenceManager.getBoolean("THEME_LIGHT",true)){
                drawerHolder.toggleButton.setChecked(true);
            } else {
                drawerHolder.toggleButton.setChecked(false);
            }
            drawerHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        sharePreferenceManager.putBoolean("THEME_LIGHT", true);
                        ThemeManager.changeToTheme((Activity) context, ThemeManager.THEME_LIGHT);
                    } else {
                        sharePreferenceManager.putBoolean("THEME_LIGHT", false);
                        ThemeManager.changeToTheme((Activity)context, ThemeManager.THEME_DARK);
                    }
                }
            });
		} else if (dItem.getTitle() != null) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.toggleLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.title.setText(dItem.getTitle());

		} else {
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.toggleLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.ItemName.setText(dItem.getItemName());
		}

		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName;
        Button title;
		LinearLayout headerLayout, itemLayout, toggleLayout;
		ToggleButton toggleButton;
	}
}