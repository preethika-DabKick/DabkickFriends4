package com.dabkick.freinds;

import java.util.List;

import com.dabkick.R;
import com.dabkick.homepage.HomePageActivity;
import com.dabkick.livesession.InteractLiveActivity;
import com.dabkick.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends ArrayAdapter<ItemRow> {

	List<ItemRow> data;
	Context context;
	int layoutResID, count = 0;
	int currPosition;
	RelativeLayout no, yes;
	TextView tv;
	Handler handler;
	ImageView currImage;
	Activity activity;
	ImageLoader imageLoader;
	DisplayImageOptions options;

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
			"http://wallko.com/wp-content/uploads/2014/02/Beautiful-and-Lovely-White-Flower-690x388.jpg",
			"" };

	private static String[] dbNames = { "Preethika Shenoy ", "Liya Anand ",
			"Kumar Gaurav ", "Amit Srivastav ", "Shwetha Sudheendra ",
			"Balaji Krishnan ", "Srinivas ", "Michael ", "Dabby ", "Sunil ", "" };

	private static String[] dbAge = { "27f ", "26f ", "27f ", "25m ", "25f ",
			"30m ", "32m ", "32m ", "13m ", "29m ", "" };

	private static String[] dbDabNames = { "Pritz ", "Liya ", "Gaurav ",
			"Amit ", "Shwetha ", "Balaji ", "Srinivas ", "Michael ", "Dabby ",
			"Sunil ", "" };

	private static int dbDabs[] = { 1000, 700, 20, 900, 300, 500, 500, 400,
			1500, 1200, 0 };

	private static String[] dbAddress = { "Mangalore ", "Kerala ", "Delhi ",
			"Jharkhand ", "Mumbai ", "Chennai ", " ", "Cupertino ",
			"Cupertino ", "Udupi ", "" };

	public ItemAdapter(Context context, int layoutResourceId,
			List<ItemRow> data, Handler handler) {
		super(context, layoutResourceId, data);
		this.handler = handler;
		this.data = data;
		this.context = context;
		this.layoutResID = layoutResourceId;
		activity = (Activity) context;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	public void setCurrPosition(int currPosition) {
		this.currPosition = currPosition;
		if (currPosition > 0) {

		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.d("Pritz", "position " + position);
		NewsHolder holder = null;
		View row = convertView;
		holder = null;

		if (row == null) {
			Log.d("Pritz", "row is null");
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResID, parent, false);

			holder = new NewsHolder();

			holder.itemName = (TextView) row
					.findViewById(R.id.example_itemname);
			holder.itemDab = (TextView) row.findViewById(R.id.item_dab);
			holder.icon = (ImageView) row.findViewById(R.id.example_image);
			holder.remove = (TextView) row.findViewById(R.id.swipe_button1);
			holder.interactLive = (RelativeLayout) row
					.findViewById(R.id.interact);
			holder.friendBand = (RelativeLayout) row
					.findViewById(R.id.friendLayout);
			holder.full = (RelativeLayout) row.findViewById(R.id.full);
			holder.full.getLayoutParams().height = convertDipToPixels(125,
					context);
			row.setTag(holder);

		} else {
			Log.d("Pritz", "row is not null");
			holder = (NewsHolder) row.getTag();
		}

		currImage = holder.icon;

		if (position == 9)
			holder.full.getLayoutParams().height = convertDipToPixels(250,
					context);
		else
			holder.full.getLayoutParams().height = convertDipToPixels(125,
					context);

		holder.itemName.setText(dbNames[position] + " | " + dbAge[position]);
		holder.itemDab.setText(dbDabNames[position] + " | " + dbDabs[position]
				+ " Dabs \n" + dbAddress[position]);

		holder.remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Remove friend?", Toast.LENGTH_SHORT)
						.show();
			}
		});

		holder.interactLive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				interactLive(position);
			}
		});

		loadImage(position, holder);

		holder.friendBand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return row;
	}

	public static int convertDipToPixels(float dips, Context context) {
		return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
	}

	public void interactLive(int position) {
		final Dialog d = new Dialog(context);
		final String name = dbNames[position];
		final String url = dbObjects[position];
		d.setContentView(R.layout.alert_dialog);
		tv = (TextView) d.findViewById(R.id.interact_live_message);
		tv.setText(name + " - Proceed?");
		d.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		no = (RelativeLayout) d.findViewById(R.id.layout_no);
		yes = (RelativeLayout) d.findViewById(R.id.layout_yes);

		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				d.dismiss();
			}
		});

		yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(context, InteractLiveActivity.class);
				intent.putExtra("Name", name);
				intent.putExtra("url", url);
				activity.startActivity(intent);
				activity.overridePendingTransition(R.anim.flip_in,
						R.anim.flip_out);*/
				d.dismiss();
				Intent intent = new Intent(context, HomePageActivity.class);
				intent.putExtra("Activity", Utils.INTERACT_LIVE);
				activity.overridePendingTransition(android.R.anim.fade_in, R.anim.choose_card_top_down);
				activity.startActivity(intent);
				//activity.finish();
			}
		});

		d.show();
	}

	private void loadImage(final int position, final NewsHolder holder) {
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... dataObjects) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						imageLoader.displayImage(dbObjects[position],
								holder.icon, options);
					}
				});
				return null;
			}
		}.execute(dbObjects[position]);
	}

	static class NewsHolder {

		TextView itemName, itemDab;
		ImageView icon;
		TextView remove;
		RelativeLayout interactLive;
		Button button3;
		RelativeLayout friendBand, full;
	}

}
