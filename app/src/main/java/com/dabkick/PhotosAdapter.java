/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dabkick;

import com.dabkick.choosefriends.ChooseFriends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotosAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	ImageView iv;
	private Context c;
	private static final int[] ids = { R.layout.viewpager_item, R.layout.blank };

	public PhotosAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		c = context;
	}

	@Override
	public int getCount() {
		return ids.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			if (position == 0) {
				convertView = mInflater.inflate(R.layout.viewpager_item, null);
				iv = (ImageView) convertView.findViewById(R.id.photos);

				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						c.startActivity(new Intent(c, ChooseFriends.class));
					}
				});
				
			} else if (position == 1) {
				convertView = mInflater.inflate(R.layout.blank, null);
			}
		}
		// ((ImageView) convertView.findViewById(R.id.photos)) );

		/*
		 * if (position != 0) { if (position == 1) { final ImageButton button =
		 * (ImageButton) convertView.findViewById(R.id.button);
		 * button.setBackgroundResource(R.drawable.abc_ab_share_pack_holo_dark);
		 * button.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { System.out.print("clicked");
		 * Log.i("hola", "hi");
		 * 
		 * //SIPInterface.init(); // Intent intent = new Intent(c,
		 * LinphoneMiniActivity.class); //
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | //
		 * Intent.FLAG_ACTIVITY_CLEAR_TOP ); // // c.startActivity(intent);
		 * 
		 * } }); } else { final ImageButton button = (ImageButton)
		 * convertView.findViewById(R.id.button);
		 * button.setBackgroundResource(R.drawable.photos_icon_red_v70);
		 * button.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { System.out.print("clicked");
		 * Log.i("hola", "hi");
		 * 
		 * Intent intent = new Intent(c, ImageGridActivity.class);
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		 * Intent.FLAG_ACTIVITY_CLEAR_TOP );
		 * 
		 * c.startActivity(intent);
		 * 
		 * } });
		 * 
		 * } } else { ((ImageView) convertView.findViewById(R.id.imageView1))
		 * .setImageResource(R.drawable.swipe_v70); }
		 */
		return convertView;
	}

}
