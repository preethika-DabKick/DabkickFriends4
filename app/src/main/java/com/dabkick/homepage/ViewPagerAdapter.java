package com.dabkick.homepage;

import com.dabkick.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	Context context;
	String[] rank;
	String[] country;
	String[] population;
	int[] flag;
	LayoutInflater inflater;
	TextView txtrank;
	TextView txtcountry;
	TextView txtpopulation;
	ImageView imgflag;

	public ViewPagerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView;
		if (position == 0) {
			itemView = inflater.inflate(R.layout.viewpager_item, container,
					false);
			imgflag = (ImageView) itemView.findViewById(R.id.photos);
			
		} else {
			itemView = inflater.inflate(R.layout.blank, container,
					false);
		}
		((ViewPager) container).addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);
	}
}

/*
 * notify = (RelativeLayout) findViewById(R.id.layout_notify); media =
 * (RelativeLayout) findViewById(R.id.layout_text);
 * notify.setVisibility(View.INVISIBLE); notification = (ImageView)
 * findViewById(R.id.notify_image); notify_text = (TextView)
 * findViewById(R.id.notify_text);
 * notification.setImageResource(R.drawable.notify_on);
 * notify_text.setText("3 dab sessions live now");
 */

/*
 * if (b){ media.setVisibility(View.VISIBLE);
 * notify.setVisibility(View.INVISIBLE); b=false; } else{
 * notify.setVisibility(View.VISIBLE);
 * media.setVisibility(View.INVISIBLE); b=true; }
 */

/*
 * notify.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) {
 * 
 * } });
 */

