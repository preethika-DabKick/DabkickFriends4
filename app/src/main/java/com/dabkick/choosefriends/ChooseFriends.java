package com.dabkick.choosefriends;

import com.dabkick.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChooseFriends extends Activity {

	RelativeLayout rlFriends, rlAddress, rlGroups;
	TextView textFriends, textAddress, textGroups;
	
	Drawable rounded_corners_left_only_blue;
	Drawable rounded_corners_left_only_white;
	Drawable rounded_corners_right_only_blue;
	Drawable rounded_corners_right_only_white;
	
	Drawable non_rounded_corners_only_blue;
	Drawable non_rounded_corners_only_white;
	
	int blue;
	int white;
	
	FriendsFragment friendFragment;
	
	FragmentManager fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		setContentView(R.layout.choose_friends);
		
		
		
		init();
		drawablesInit();
		friendsFragmentInit();
		
		Log.d("DabKick", "choose friends init done ");
		
		rlFriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onFriendsTabClick();
				friendsFragmentInit();
			}
		});

		rlAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				friendFragment.setRetainInstance(true);
				onAddressBookTabClick();
				addressFragmentInit();
			}
		});

		rlGroups.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				friendFragment.setRetainInstance(true);
				onGroupsTabClick();
				groupsFragmentInit();
			}
		});
	}
	
	private void addressFragmentInit() {
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.choose_friends_result_frame_layout, new AddressFragment());
		fragmentTransaction.commit();
	}
	
	private void groupsFragmentInit() {
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.choose_friends_result_frame_layout, new GroupsFragment());
		fragmentTransaction.commit();
	}
	
	private void onFriendsTabClick() {
		rlFriends.setBackground(rounded_corners_left_only_blue);
		textFriends.setTextColor(white);
		rlAddress.setBackground(non_rounded_corners_only_white);
		textAddress.setTextColor(blue);
		rlGroups.setBackground(rounded_corners_right_only_white);
		textGroups.setTextColor(blue);
	}

	private void friendsFragmentInit() {
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.choose_friends_result_frame_layout, friendFragment);
		fragmentTransaction.commit();
		friendFragment.setRetainInstance(true);
	}

	private void drawablesInit() {
		rounded_corners_left_only_blue = getResources().getDrawable(R.drawable.rounded_corners_left_only_blue);
		rounded_corners_left_only_white = getResources().getDrawable(R.drawable.rounded_corners_left_only_white);
		rounded_corners_right_only_blue = getResources().getDrawable(R.drawable.rounded_corners_right_only_blue);
		rounded_corners_right_only_white = getResources().getDrawable(R.drawable.rounded_corners_right_only_white);
		non_rounded_corners_only_blue = getResources().getDrawable(R.drawable.non_rounded_corners_only_blue);
		non_rounded_corners_only_white = getResources().getDrawable(R.drawable.non_rounded_corners_only_white);
		
		blue = getResources().getColor(R.color.blue);
		white = getResources().getColor(R.color.white);
	}

	private void init() {
		rlFriends = (RelativeLayout) findViewById(R.id.friends);
		rlAddress = (RelativeLayout) findViewById(R.id.address);
		rlGroups = (RelativeLayout) findViewById(R.id.groups);
		textFriends = (TextView) findViewById(R.id.friends_text);
		textAddress = (TextView) findViewById(R.id.address_text);
		textGroups = (TextView) findViewById(R.id.groups_text);
		friendFragment=new FriendsFragment();
		friendFragment.setRetainInstance(true);
	}

	private void onAddressBookTabClick() {
		rlFriends.setBackground(rounded_corners_left_only_white);
		textFriends.setTextColor(blue);
		rlAddress.setBackground(non_rounded_corners_only_blue);
		textAddress.setTextColor(white);
		rlGroups.setBackground(rounded_corners_right_only_white);
		textGroups.setTextColor(blue);
	}

	private void onGroupsTabClick() {
		rlFriends.setBackground(rounded_corners_left_only_white);
		textFriends.setTextColor(blue);
		rlAddress.setBackground(non_rounded_corners_only_white);
		textAddress.setTextColor(blue);
		rlGroups.setBackground(rounded_corners_right_only_blue);
		textGroups.setTextColor(white);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition( android.R.anim.fade_in,R.anim.choose_card_top_down);
	}

}
