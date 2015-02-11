package com.dabkick.contextmenu;

import com.dabkick.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class InviteFriends extends Activity {
	RelativeLayout background, wrap, cancel;
	Animation anim;
	ImageView mail, message, facebook, twitter, rateApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share);

		background = (RelativeLayout) findViewById(R.id.layout_background);
		wrap = (RelativeLayout) findViewById(R.id.full_layout);
		cancel = (RelativeLayout) findViewById(R.id.mylayout);
		
		mail = (ImageView) findViewById(R.id.mail);
		message = (ImageView) findViewById(R.id.message);
		facebook = (ImageView) findViewById(R.id.fb);
		twitter = (ImageView) findViewById(R.id.twitter);
		rateApp = (ImageView) findViewById(R.id.rate);

		anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.choose_card_bottom_up);
		wrap.startAnimation(upAnimation());

		background.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				wrap.clearAnimation();
				Animation an = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.choose_card_top_down);
				an.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// my_layout.clearAnimation();
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						wrap.clearAnimation();
					}
				});

				wrap.startAnimation(an);

				onBackPressed();
			}
		});

		wrap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(InviteFriends.this, "Mail?", Toast.LENGTH_SHORT)
						.show();

			}
		});

		message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(InviteFriends.this, "Message?",
						Toast.LENGTH_SHORT).show();
			}
		});

		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(InviteFriends.this, "Facebook?",
						Toast.LENGTH_SHORT).show();
			}
		});

		twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(InviteFriends.this, "Twitter?",
						Toast.LENGTH_SHORT).show();
			}
		});

		rateApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(InviteFriends.this, "Rate App?",
						Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	private Animation upAnimation() {
		Animation anim = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(new AccelerateInterpolator());
		return anim;
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, R.anim.choose_card_top_down);
	}
	
	@Override
	public void onBackPressed() {
		wrap.clearAnimation();
		wrap.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.choose_card_top_down));
		super.onBackPressed();
	}

}