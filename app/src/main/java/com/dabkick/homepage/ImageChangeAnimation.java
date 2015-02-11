package com.dabkick.homepage;

import com.dabkick.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class ImageChangeAnimation {
	private ImageView imageView;

	private Context mContext;

	private Animation fadeOut;

	private Animation fadeIn;

	private OnImageChangedListener onImageChangedListener;

	private String TAG = "ImageChangeAnimation";

	public ImageChangeAnimation(ImageView imageView, Context mContext) {
		super();
		this.imageView = imageView;
		this.mContext = mContext;
		init();
	}

	private void init() {
		fadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
		fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
		long duration = fadeIn.getDuration() + fadeOut.getDuration();
		Log.d(TAG, "Total Animation Duration = " + duration);
	}

	public void addImageAndStartAnimation(final Drawable drawable) {

		fadeOut.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d(TAG, "FadeOut Animation Over");
				fadeIn.reset();				
				/*Log.d(TAG, "Setting Image");
				imageView.setBackgroundDrawable(drawable);
				Log.d(TAG, "Calling ImageChangedListener");
				onImageChangedListener.onImageChanged();*/
				
				Log.d(TAG, "Setting Image");
				imageView.setImageDrawable(drawable);
				imageView.setAnimation(fadeIn);
				fadeIn.start();

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});

		fadeIn.setAnimationListener(new AnimationListener() {

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
				Log.d(TAG, "FadeIn Animation Finished");
				fadeOut.reset();
				/*imageView.setAnimation(fadeOut);
				fadeOut.start();*/
				
				
				Log.d(TAG, "Calling ImageChangedListener");
				onImageChangedListener.onImageChanged();
			}
		});

		Log.d(TAG, "Starting FadeOut Animation");
		//fadeIn.reset();
		/*imageView.setAnimation(fadeIn);
		fadeIn.start();*/
		imageView.startAnimation(fadeOut);
	}

	public interface OnImageChangedListener {
		public void onImageChanged();
	}

	public void setOnImageChangedListener(
			OnImageChangedListener onImageChangedListener) {
		this.onImageChangedListener = onImageChangedListener;
	}

}
