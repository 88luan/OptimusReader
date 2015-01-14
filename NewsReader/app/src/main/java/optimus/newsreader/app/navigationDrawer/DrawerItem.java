package optimus.newsreader.app.navigationDrawer;

public class DrawerItem {

	String ItemName;
    String urlPage;

    String title;
	boolean isToggleButton;

	public DrawerItem(String itemName, String url) {
		this.ItemName = itemName;
        this.urlPage = url;
	}

	public DrawerItem(boolean isToggleButton) {
		this(null, null);
		this.isToggleButton = isToggleButton;
	}

	public DrawerItem(String title) {
		this(null, null);
		this.title = title;
	}

    public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isToggleButton() {
		return isToggleButton;
	}

}
