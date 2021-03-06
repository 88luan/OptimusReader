package optimus.newsreader.app.utilisation;

import android.app.Activity;
import android.content.Intent;

import optimus.newsreader.app.R;

public class ThemeManager
{
	private static int sTheme;

	public final static int THEME_DARK = 1;
	public final static int THEME_LIGHT = 2;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	public static void changeToTheme(Activity activity, int theme)
	{
		sTheme = theme;
		activity.finish();

		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/** Set the theme of the activity, according to the configuration. */
	public static void onActivityCreateSetTheme(Activity activity)
	{
		switch (sTheme)
		{
		default:
            activity.setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
            break;
		case THEME_DARK:
            activity.setTheme(R.style.Theme_AppCompat);
			break;
		case THEME_LIGHT:
			activity.setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
			break;
		}
	}

}
