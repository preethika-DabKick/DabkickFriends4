package com.dabkick.livesession;

import com.dabkick.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InteractLiveActivity extends Activity {

	ImageView end, profilePic;
	RelativeLayout leaveMessage, waitingLayout;
	TextView friendName;
	String name, url;

	private String TAG = "AutoSlider";

	boolean isStart = true;

	float conatinerX1;

	float containerX2;
	Bitmap bm;

	float scrollBarWidth;
	FrameLayout scrollBar;

	TranslateAnimation moveLefttoRight;

	TranslateAnimation moveRightToLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interact_live);
		
		Log.d("DabKick", "Interactlive");

		init();
		retrieveValues();
		friendName.setText(name);
		setImage();
		//createAnim();

		end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		leaveMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(InteractLiveActivity.this,LeaveMessageActivity.class);
				intent.putExtra("Name", name);
				intent.putExtra("url", url);
				startActivity(intent);
				overridePendingTransition(R.anim.card_flip_left_in, android.R.anim.fade_out);
			}
		});

	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    if(hasFocus){
	      createAnim();
	    }   
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.flip_in,R.anim.flip_out);
	}

	private void createAnim() {
		conatinerX1 = waitingLayout.getLeft();

		containerX2 = waitingLayout.getWidth();

		scrollBarWidth = scrollBar.getWidth();

		containerX2 = containerX2 - scrollBarWidth;
		Log.d(TAG, "x1 = " + conatinerX1 + " y1 = " + containerX2);

		moveLefttoRight = new TranslateAnimation(conatinerX1, containerX2, 0, 0);
		// moveLefttoRight.setInterpolator(new AccelerateInterpolator());
		moveLefttoRight.setDuration(2000);
		moveLefttoRight.setFillAfter(true);

		moveRightToLeft = new TranslateAnimation(containerX2, conatinerX1, 0, 0);
		// moveLefttoRight.setInterpolator(new AccelerateInterpolator());
		moveRightToLeft.setDuration(2000);
		moveRightToLeft.setFillAfter(true);

		moveLefttoRight.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				scrollBar.startAnimation(moveRightToLeft);

			}
		});

		moveRightToLeft.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				scrollBar.startAnimation(moveLefttoRight);
			}
		});

		scrollBar.startAnimation(moveLefttoRight);
	}

	private void setImage() {
		final ImageLoader imageLoader = ImageLoader.getInstance();
		// Initialize ImageLoader with configuration. Do it once.
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		// Load and display image asynchronously

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher) // this is the image that
														// will be displayed if
														// download fails
				.cacheInMemory().cacheOnDisc().build();
		imageLoader.displayImage(url, profilePic, options);
		//profilePic.setImageBitmap(bm);
	}

	private void retrieveValues() {
		Intent intent = getIntent();
		// int pos = intent.getIntExtra("position", 0);
		name = intent.getStringExtra("Name");
		url = intent.getStringExtra("url");
		//bm = intent.getParcelableExtra("image");
	}

	private void init() {
		end = (ImageView) findViewById(R.id.end_image);
		leaveMessage = (RelativeLayout) findViewById(R.id.leave_message);
		profilePic = (ImageView) findViewById(R.id.friend_image);
		friendName = (TextView) findViewById(R.id.friend_name);
		waitingLayout = (RelativeLayout) findViewById(R.id.waiting_layout);

		scrollBar = (FrameLayout) findViewById(R.id.scrollbar);

	}

}
