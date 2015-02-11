package com.dabkick.choosefriends;

import java.util.ArrayList;

import com.dabkick.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends BaseAdapter {

	TextView friendName;
	ImageView displayPic,tick;
	Context context;
	Handler handler;
	Activity activity;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	boolean clicked[] = new boolean[10];
	int count=0;
	
	private static String[] dbObjects = {
		"http://www.stunningmesh.com/wp-content/uploads/2011/06/stunningmesh-ipad-wallpapers-61-small.jpg",
		"http://www.desktopaper.com/wp-content/uploads/beautiful-lovely-puppy-small-dog-wallpaper.jpg",
		"http://animal-backgrounds.com/file/1082/600x338/16:9/cute-and-funny-puppies-small-dog-wallpaper--wallpaper_273272404.jpg",
		"http://2.bp.blogspot.com/-1ok1OhzA-8Y/UCOWAKU746I/AAAAAAAAAu0/qZoa4JYhmgE/s1600/10558905.jpg",
		"http://hdwallpaperia.com/wp-content/uploads/2013/11/Pink-Flowers-Love-Wallpaper-640x400.jpg",
		"https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg",
		"http://zwallpaper.biz/hinhanh/anhto/14110Two-samll-hearts-pink-and-white-mobile-wallpaper.jpg",
		"http://hdwallpaperia.com/wp-content/uploads/2013/11/Small-Waterfall-Wallpaper.jpg",
		"http://4.bp.blogspot.com/-mFpy4m4Y4BI/U0g_H_l4NZI/AAAAAAAAB6c/ZjuENaQWbCg/s1600/BaTsH6iIgAAFYum.jpg",
		"http://wallko.com/wp-content/uploads/2014/02/Beautiful-and-Lovely-White-Flower-690x388.jpg" };

	private static String[] dbNames = { "Preethika Shenoy ", "Liya Anand ",
		"Kumar Gaurav ", "Amit Srivastav ", "Shwetha Sudheendra ",
		"Balaji Krishnan ", "Srinivas ", "Michael ", "Dabby ", "Sunil " };

    public CustomListViewAdapter(Context context, boolean[] clicked, int count)
    {
        super();
        this.context = context;
        handler = new Handler();
        activity = (Activity) context;
        imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.placeholder).cacheInMemory()
				.cacheOnDisc().build();
		for (int i = 0; i < getCount(); i++) {
			
			this.clicked[i]=clicked[i];
			
		}
		this.count = count;
    }

	@Override
	public int getCount() {
		return dbNames.length;
	}

	@Override
	public String getItem(int position) {
		return dbNames[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.list_items, parent,false);
			friendName = (TextView) row.findViewById(R.id.friend_name);
			displayPic = (ImageView) row.findViewById(R.id.icon);
			tick = (ImageView) row.findViewById(R.id.radio_icon);
		}
		friendName.setText(dbNames[position]);  
		loadImage(position, displayPic);
		colorItems(position);
		return row;
	}

	private void colorItems(int position) {
		if (count>0){
				if (clicked[position]){
					tick.setImageResource(R.drawable.friends_selected);
					friendName.setTextColor(context.getResources().getColor(
							R.color.blue));
				}
		}
	}

	private void loadImage(final int position, final ImageView displayPic) {
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... dataObjects) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						imageLoader.displayImage(dbObjects[position],
								displayPic, options);
					}
				});
				return null;
			}
		}.execute(dbObjects[position]);
	}

}
