package com.dabkick.homepage;

import java.util.LinkedList;

import com.dabkick.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

public class CustomGridAdapter extends BaseAdapter {
	private Context mContext;
	Handler handler;
	boolean req = true;
	int count;
	int screenWidth;
	ViewGroup vg;
	// AnimationDrawable dabbersAnimation;
	TextView textView;
	ImageView imageView;
	RelativeLayout text_layout, item_container, item;
	int dabberImageCounter = 0;
	int requestImageCounter = 0;
	// ImageLoader imageLoader;
	LayoutInflater inflater;
	DisplayImageOptions options;
	String TAG = "DabKick Adapter";
	ImageChangeAnimation dabberImageChangeAnimation;
	ImageChangeAnimation requestImageChangeAnimation;

	ImageChangeAnimation.OnImageChangedListener dabberImageChangedListener;
	ImageChangeAnimation.OnImageChangedListener requestImageChangedListener;
	
	LinkedList<Drawable> bufferdListOfDabberImages = new LinkedList<Drawable>();
	LinkedList<Drawable> bufferdListOfRequestImages = new LinkedList<Drawable>();
	
	int maxDabberListBufferSize = 1; // should be 1 more than the required buffered size of image list
	int maxRequestListBufferSize = 1; // should be 1 more than the required buffered size of image list
	
	boolean isDabbersAnimationRunning = false;
	boolean isRequestAnimationRunning = false;
	
	Drawable lastDabberImage;
	Drawable lastRequestImage;
	
	private static String[] dataObjects = {
			"http://www.stunningmesh.com/wp-content/uploads/2011/06/stunningmesh-ipad-wallpapers-61-small.jpg",
			"http://wallpapers55.com/wp-content/uploads/2014/05/beautiful-lovely-puppy-small-dog-wallpaper.jpg",
			"http://animal-backgrounds.com/file/1082/600x338/16:9/cute-and-funny-puppies-small-dog-wallpaper--wallpaper_273272404.jpg",
			"http://2.bp.blogspot.com/-1ok1OhzA-8Y/UCOWAKU746I/AAAAAAAAAu0/qZoa4JYhmgE/s1600/10558905.jpg",
			"http://hdwallpaperia.com/wp-content/uploads/2013/11/Pink-Flowers-Love-Wallpaper-640x400.jpg",
			"https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg",
			"http://zwallpaper.biz/hinhanh/anhto/14110Two-samll-hearts-pink-and-white-mobile-wallpaper.jpg",
			"http://hdwallpaperia.com/wp-content/uploads/2013/11/Small-Waterfall-Wallpaper.jpg",
			"http://4.bp.blogspot.com/-mFpy4m4Y4BI/U0g_H_l4NZI/AAAAAAAAB6c/ZjuENaQWbCg/s1600/BaTsH6iIgAAFYum.jpg",
			"http://wallko.com/wp-content/uploads/2014/02/Beautiful-and-Lovely-White-Flower-690x388.jpg"};

	private static String[] dataRequests = {
			"http://4.bp.blogspot.com/-zUNZGYbUjAk/UxpJG07LqbI/AAAAAAAAAyg/4OnbQ39n5yI/s1600/friends-2.png",
			"http://1.bp.blogspot.com/-j3RW7j8qEhg/TyL93l0P8TI/AAAAAAAABTc/IhaFS0eulNU/s1600/KCHIP-Children%2520holding%2520hands.jpg",
			"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSGvHUUDDjELY-0OXClPxjTu2hB99kWsRK8r9YPXSvwZBbxiUBi" };

	private static String[] dataNames = { "Preethika ", "Liya ", "Gaurav ",
			"Amit ", "Shwetha ", "Balaji ", "Srinivas ", "Michael ", "Dabby ",
			"Sunil "};

	String friend_request = "http://4.bp.blogspot.com/-zUNZGYbUjAk/UxpJG07LqbI/AAAAAAAAAyg/4OnbQ39n5yI/s1600/friends-2.png";
	String dabbers = "http://1.bp.blogspot.com/-j3RW7j8qEhg/TyL93l0P8TI/AAAAAAAABTc/IhaFS0eulNU/s1600/KCHIP-Children%2520holding%2520hands.jpg";
	String groups = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSGvHUUDDjELY-0OXClPxjTu2hB99kWsRK8r9YPXSvwZBbxiUBi";

	public CustomGridAdapter(Context c, Handler handler, int screenWidth) {
		mContext = c;
		this.handler = handler;
		this.screenWidth = screenWidth;

		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();
		if(req) {
			count = 5;
		} else {
			count = 4;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataNames.length + count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View grid;
		this.vg = parent;
		grid = init();

		// setDabbersAnimation();

		switch (position) {
		case 0:
			addFriend();
			break;
		case 1:
			item.setVisibility(View.INVISIBLE);
			if (req) {
				setRequestsTile(grid);
			} else {
				setDabbersTile(grid);
			}
			break;
		case 2:
			setGroupsTile();
			break;
		case 3:
			setMailsTile();
			break;

		default:
			item.setVisibility(View.INVISIBLE);
			if (req) {
				count = 5;
				if (position == 4) {
					// textView.setText("Dabbers");
					// displayImages(imageLoader, options, imageView, dabbers);
					setDabbersTile(grid);

				} else {
					populateFriendsDabPics(position, textView, imageView);
				}
			} else {
				count = 4;
				Log.d("Pritz", "pos " + position);
				populateFriendsDabPics(position, textView, imageView);
			}
			break;

		}
		return grid;
	}

	private void setMailsTile() {
		item.setVisibility(View.INVISIBLE);
		textView.setText("");
		imageView.setBackgroundResource(R.drawable.no_msg);
		text_layout.setBackgroundColor(0xFFFFFF);
	}

	private void setGroupsTile() {
		item.setVisibility(View.INVISIBLE);
		textView.setText("Groups");
		// imageLoader.displayImage(groups, imageView, options);
		// displayImages(imageLoader, options, imageView, groups);
		ImageLoader.getInstance().displayImage(groups, imageView, options);
	}

	private void setRequestsTile(View grid) {
        isRequestAnimationRunning = false;
		ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);


        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        textView.setText("Requests");
        requestImageChangeAnimation = new ImageChangeAnimation(imageView,
                mContext);

        requestImageChangedListener = new RequestImageChangedListener();

        requestImageChangeAnimation
                .setOnImageChangedListener(requestImageChangedListener);

		if(lastRequestImage!=null){
			/*ImageLoader.getInstance().displayImage(dataRequests[requestImageCounter-1],
					imageView, options);*/
			imageView.setImageDrawable(lastRequestImage);
            startRequestSlideShow();
			return;
		} else {
            imageView.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.placeholder));
            startRequestSlideShow();
        }
		



	}

	private void setDabbersTile(View grid) {
		ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);


        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        textView.setText("Dabbers");
        dabberImageChangeAnimation = new ImageChangeAnimation(imageView,
                mContext);

        dabberImageChangedListener = new DabberImageChangedListener();

        dabberImageChangeAnimation
                .setOnImageChangedListener(dabberImageChangedListener);
        isDabbersAnimationRunning = false;
		if(lastDabberImage!=null){
			/*ImageLoader.getInstance().displayImage(dataObjects[dabberImageCounter-1],
					imageView, options);*/
			imageView.setImageDrawable(lastDabberImage);
            startDabberSlideShow();
			return;
		} else {
            imageView.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.placeholder));
            startDabberSlideShow();
        }
		


	}

	private void addFriend() {
		imageView.setImageResource(R.drawable.plus);
		item_container.setBackgroundColor(Color.BLACK);
	}

	private View init() {
		req = true;
		View grid;
		handler = new Handler();
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		grid = new View(mContext);
		grid = inflater.inflate(R.layout.grid_single, null);

		textView = (TextView) grid.findViewById(R.id.grid_text);
		imageView = (ImageView) grid.findViewById(R.id.grid_image);

		text_layout = (RelativeLayout) grid.findViewById(R.id.textlayout);
		item_container = (RelativeLayout) grid.findViewById(R.id.item_full);
		item = (RelativeLayout) grid.findViewById(R.id.transparant_layout);
		item.setVisibility(View.INVISIBLE);
		item_container.getLayoutParams().height = screenWidth / 4;
		item_container.getLayoutParams().width = screenWidth / 4;
		return grid;
	}

	private void populateFriendsDabPics(int position, TextView textView,
			ImageView imageView) {
		textView.setText(dataNames[position - count]);
		imageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.placeholder));
		/*
		 * displayImages(imageLoader, options, imageView, dataObjects[position -
		 * count]);
		 */

		ImageLoader.getInstance().displayImage(dataObjects[position - count],
				imageView, options);
	}

	public void enableBlur(int position) {

	}

	private void fetchDabberImage(final String Url) {
		String ul = Url;
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... dataObjects) {
				// loadImage(position, holder);
				// Yet to code
				handler.post(new Runnable() {

					@Override
					public void run() {
						
						
						
						/*
						 * imageLoader .displayImage(Url, imageView, options);
						 */
						ImageLoader.getInstance().loadImage(Url, options,
								new ImageLoadingListener() {

									@Override
									public void onLoadingCancelled(String arg0,
											View arg1) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onLoadingComplete(String arg0,
											View arg1, Bitmap arg2) {
										if (arg2 != null) {

											Drawable drawable = new BitmapDrawable(
													mContext.getResources(),
													arg2);
											Log.d(TAG, "On Loading Complete"
													+ Url);
											bufferdListOfDabberImages.addFirst(drawable);
											//startDabberAnimation(drawable);
											//prefetchAndSlideShowDabberImages();

										} else {

											//fetchNextDabberImage();
											//prefetchAndSlideShowDabberImages();

										}
										prefetchAndSlideShowDabberImages();
									}

									@Override
									public void onLoadingFailed(String arg0,
											View arg1, FailReason arg2) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onLoadingStarted(String arg0,
											View arg1) {
										// TODO Auto-generated method stub

									}

								});
					}
				});

				return null;
			}
		}.execute(Url);
	}

	private void fetchRequestImage(final String Url) {
		String ul = Url;
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... dataObjects) {
				// loadImage(position, holder);
				// Yet to code
				handler.post(new Runnable() {

					@Override
					public void run() {
						/*
						 * imageLoader .displayImage(Url, imageView, options);
						 */
						ImageLoader.getInstance().loadImage(Url, options,
								new ImageLoadingListener() {

									@Override
									public void onLoadingCancelled(String arg0,
											View arg1) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onLoadingComplete(String arg0,
											View arg1, Bitmap arg2) {
										if (arg2 != null) {

											Drawable drawable = new BitmapDrawable(
													mContext.getResources(),
													arg2);
											Log.d(TAG, "On Loading Complete"
													+ Url);

											//startRequestAnimation(drawable);
											bufferdListOfRequestImages.addFirst(drawable);
											//prefetchAndSlideShowRequestImages();

										} else {

											//fetchNextRequestImage();
											//prefetchAndSlideShowRequestImages();

										}
										prefetchAndSlideShowRequestImages();
									}

									@Override
									public void onLoadingFailed(String arg0,
											View arg1, FailReason arg2) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onLoadingStarted(String arg0,
											View arg1) {
										// TODO Auto-generated method stub

									}

								});
					}
				});

				return null;
			}
		}.execute(Url);
	}

	private void startDabberSlideShow() {
		dabberImageCounter = 0;
		fetchNextDabberImage();
	}

	private void fetchNextDabberImage() {
		if (dabberImageCounter < dataObjects.length) {
			fetchDabberImage(dataObjects[dabberImageCounter]);
			Log.d(TAG, "return from fetchDabberImage funtion");
			dabberImageCounter++;
		} else {
			dabberImageCounter = 0;
			fetchDabberImage(dataObjects[dabberImageCounter]);
			Log.d(TAG, "return from fetchDabberImage funtion");
			dabberImageCounter++;
		}
	}
	
	private void startDabberAnimation(Drawable drawable) {

		Log.d(TAG, "Starting Dabbers Animation");
		isDabbersAnimationRunning = true;
		lastDabberImage = drawable;
		dabberImageChangeAnimation.addImageAndStartAnimation(drawable);
		Log.d(TAG, "Return from Dabber Animation");
		
	}

	private void startRequestSlideShow() {
		requestImageCounter = 0;
		fetchNextRequestImage();
	}

	private void fetchNextRequestImage() {
		if (requestImageCounter < dataRequests.length) {
			fetchRequestImage(dataRequests[requestImageCounter]);
			Log.d(TAG, "return from fetchRequestImage funtion");
			requestImageCounter++;
		} else {
			requestImageCounter = 0;
			fetchRequestImage(dataRequests[requestImageCounter]);
			Log.d(TAG, "return from fetchRequestImage funtion");
			requestImageCounter++;
		}
	}
	
	private void startRequestAnimation(Drawable drawable) {

		Log.d(TAG, "Starting Request Animation");
		isRequestAnimationRunning = true;
		lastRequestImage = drawable;
		requestImageChangeAnimation.addImageAndStartAnimation(drawable);
		Log.d(TAG, "Return from Request Animation");
	}
	
	private void prefetchAndSlideShowDabberImages() {
		if(bufferdListOfDabberImages.size() < maxDabberListBufferSize) {
			fetchNextDabberImage();
		}
		if(!isDabbersAnimationRunning && bufferdListOfDabberImages.size()>0) {
			Drawable drawable = bufferdListOfDabberImages.poll();
			if(drawable!=null) {
				startDabberAnimation(drawable);
			}
		}
	}
	
	private void prefetchAndSlideShowRequestImages() {
		if(bufferdListOfRequestImages.size() < maxRequestListBufferSize) {
			fetchNextRequestImage();
		}
		if(!isRequestAnimationRunning && bufferdListOfRequestImages.size()>0) {
			Drawable drawable = bufferdListOfRequestImages.poll();
			if(drawable!=null) {
				startRequestAnimation(drawable);
			}
		}
	}

	private class DabberImageChangedListener implements
			ImageChangeAnimation.OnImageChangedListener {

		@Override
		public void onImageChanged() {

			Log.d(TAG, "On Dabber Image Changed");
			//fetchNextDabberImage();
			isDabbersAnimationRunning = false;
			prefetchAndSlideShowDabberImages();
		}

	}

	private class RequestImageChangedListener implements
			ImageChangeAnimation.OnImageChangedListener {

		@Override
		public void onImageChanged() {
			Log.d(TAG, "On Request Image Changed");
			//fetchNextRequestImage();
			isRequestAnimationRunning = false;
			prefetchAndSlideShowRequestImages();
		}

	}
}
