package com.dabkick.contextmenu;

import com.dabkick.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class FindFriends extends Activity {

	ListView lv;
	ArrayAdapter<String> menuAdapter;
	String[] menuList = { "address book friends",			
			"Facebook friends","Twitter friends" };
	RelativeLayout background,cancel,my_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend);

		background = (RelativeLayout) findViewById(R.id.layout_background);
		cancel = (RelativeLayout) findViewById(R.id.layout_cancel);
		lv = (ListView) findViewById(R.id.listView1);
		menuAdapter = new ArrayAdapter<String>(this, R.layout.text_style,
				menuList);
		my_layout = (RelativeLayout) findViewById(R.id.my_layout);
		lv.setAdapter(menuAdapter);

        startBeginAnimation();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position==0){
					
				}
				else if (position==1){
					
				}
				
			}
		});

		background.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

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

    void startBeginAnimation()
    {
        final RelativeLayout maskView = (RelativeLayout)findViewById(R.id.maskView);
        maskView.setAlpha(0.7f);
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
                        //maskView.startAnimation(alphaAnimation);
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


    @Override
    public void onBackPressed() {

        final RelativeLayout maskView = (RelativeLayout)findViewById(R.id.maskView);

        //set up the top-down animation
        Animation topDownAnimation = AnimationUtils.loadAnimation(this,R.anim.choose_card_top_down);
        //set listener to the top-down animation to detect when it's end.
        topDownAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                my_layout.setVisibility(View.INVISIBLE);
                finish();
                //set it as no leaving animation
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //We create a thread to handle the background dismiss to make
        //it executed at the same time as the top-down animation
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //Subviews control must be executed by UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlphaAnimation anim = new AlphaAnimation(0.7f,0);
                        anim.setDuration(500);
                        maskView.startAnimation(anim);
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

        //Start top-down menu sheet animation
        topDownAnimation.setDuration(500);
        my_layout.startAnimation(topDownAnimation);

    }
}
