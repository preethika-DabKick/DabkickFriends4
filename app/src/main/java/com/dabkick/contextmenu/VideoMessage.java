package com.dabkick.contextmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dabkick.R;
import com.dabkick.utils.Utils;

public class VideoMessage extends Activity {

	RelativeLayout voice, video, cancel, my_layout,background;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_message);

		init();
		startAnimation();
		
		background.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                onBackPressed();
			}
		});

		voice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//finishActivity(getIntent()));
				setResult(Utils.RESULT_CODE_VOICE);
				finish();
			}
		});

		video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(Utils.RESULT_CODE_VIDEO);
				finish();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

	}

	private void init() {
		voice = (RelativeLayout) findViewById(R.id.voice_layout);
		video = (RelativeLayout) findViewById(R.id.video_layout);
		cancel = (RelativeLayout) findViewById(R.id.layout_cancel);
		my_layout = (RelativeLayout) findViewById(R.id.my_layout);
		background = (RelativeLayout) findViewById(R.id.layout_background);
	}
	
	void startAnimation()
    {
        final ImageView maskView = (ImageView)findViewById(R.id.maskView);

        final AlphaAnimation alphaAnimation = new AlphaAnimation(0,0.7f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setFillEnabled(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Subviews control must be executed by UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alphaAnimation.setDuration(500);
                        maskView.startAnimation(alphaAnimation);
                    }
                });
            }
        });
        thread.start();
        try {
            //make sure it doesn't end before main thread or it would crash...
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //maskView.setImageAlpha(1);
        my_layout.startAnimation(upAnimation());
    }
	
	private Animation upAnimation() {
		Animation anim = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		anim.setDuration(500);
		return anim;
	}

	@Override
	public void onBackPressed() {

		final ImageView maskView = (ImageView) findViewById(R.id.maskView);

		// set up the top-down animation
		Animation topDownAnimation = AnimationUtils.loadAnimation(this,
				R.anim.choose_card_top_down);
		// set listener to the top-down animation to detect when it's end.
		topDownAnimation
				.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						my_layout.setVisibility(View.INVISIBLE);
						finish();
						// set it as no leaving animation
						overridePendingTransition(0, 0);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});

		// We create a thread to handle the background dismiss to make
		// it executed at the same time as the top-down animation
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				// Subviews control must be executed by UI thread
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						AlphaAnimation anim = new AlphaAnimation(0.7f, 0);
						anim.setFillAfter(true);
						anim.setFillEnabled(true);
						anim.setDuration(500);
						maskView.startAnimation(anim);
					}
				});

			}
		});
		thread.start();
		try {
			// make sure it doesn't end before main thread or it would crash...
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Start top-down menu sheet animation
		topDownAnimation.setDuration(500);
		my_layout.startAnimation(topDownAnimation);

	}

}
