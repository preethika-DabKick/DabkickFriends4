package com.dabkick.homepage;

import com.dabkick.CircleFlowIndicator;
import com.dabkick.PhotosAdapter;
import com.dabkick.R;
import com.dabkick.ViewFlow;
import com.dabkick.contextmenu.AddFriend;
import com.dabkick.freinds.FriendsActivity;
import com.dabkick.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony.Sms.Conversations;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends Activity {
	
	private static final String TAG = "DabKick";
	GridView grid;
	ImageView downArrow, notification, mail;
	int click_count[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	RelativeLayout translucentLayer, media, notify, ok, okMessage,gridLayout;
	
	TextView notifyText, gridText;
	Handler handler;
	CustomGridAdapter adapter;
	ViewPager viewPager;
	ViewFlow viewFlow;
	final int color_red = 0xCCED1C24;
	ViewPagerAdapter pagerAdapter;
	QuickAction redBand;
	LayoutParams params, message_band_params;
	AnimationDrawable newMailAnimation;
	DisplayMetrics metrics;
	int measuredWidth = 0, measuredHeight = 0,count;
	boolean b = false; //True if we have friend requests
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		Log.d(TAG, "entered");
		init();
		
		

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				redBand.dismissi();
				// Toast.makeText(getApplicationContext(), "ok pressed",
				// Toast.LENGTH_LONG).show();
			}
		});

		redBand
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						if (actionId == 1) {
							Toast.makeText(getApplicationContext(),
									"Add item selected", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(getApplicationContext(),
									actionItem.getTitle() + " selected",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		onRedBandDismissListener();
		
		
		downArrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadFriends();
			}
		});

		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gridItemInit(view);

				switch (position) {
				case 0:
					startActivity(new Intent(HomePageActivity.this,
							AddFriend.class));
					break;
				case 3:
					onMessageTileClick(view, position);
					break;
				default:
					defaultGridItemBehavior(parent, view, position);
					break;
				}

			}

		});
		
		grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (adapter.req) count = 5;
				else count = 4;
				if (position>count-1){
					intent.putExtra("position", position-count);
					loadFriends();
					return true;
				}
				return false;
			}
			
		});
		Log.d(TAG, "done");

	}

	private void onRedBandDismissListener() {
		redBand.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {
				View v;
				for (int pos = 0; pos < adapter.getCount(); pos++) {
					grid.setEnabled(true);
				
					v = grid.getChildAt(pos);
					if (v != null) {
						translucentLayer = (RelativeLayout) v
								.findViewById(R.id.transparant_layout);
						translucentLayer.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
	}

	private void init() {
		handler = new Handler();
		downArrow = (ImageView) findViewById(R.id.imageView1);
		grid = (GridView) findViewById(R.id.grid);
		gridLayout = (RelativeLayout) findViewById(R.id.gridlayout);
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		measuredHeight = metrics.heightPixels;
		measuredWidth = metrics.widthPixels;
		float height3Rows = measuredWidth*3/4;
		Log.d(TAG, "width " + measuredWidth + " and height "+ measuredHeight);
		adapter = new CustomGridAdapter(HomePageActivity.this, handler,
				measuredWidth);
		adapter.setScreenWidth(measuredWidth);
		//req = adapter.req;
		Log.d(TAG, "measuredHeight "+measuredHeight);
		Log.d(TAG, "measuredWidth "+measuredWidth);
		int minHeight = (int) (height3Rows+convertDpToPixel(296f));
		Log.d(TAG, "minHeight "+minHeight);
		if (minHeight<=measuredHeight) grid.getLayoutParams().height=(int) height3Rows;
		else grid.getLayoutParams().height=measuredHeight-convertDpToPixel(Utils.topToDotsLength);
		grid.setAdapter(adapter);
		redBand = new QuickAction(this);
		ok = (RelativeLayout) redBand.mTrack.findViewById(R.id.ok_band);
		okMessage = (RelativeLayout) redBand.mTrack.findViewById(R.id.message_band);
		intent = new Intent(HomePageActivity.this,FriendsActivity.class);
		if (adapter.req) count = 5;
		else count =4;
		viewFlow = (ViewFlow) findViewById(R.id.view_flow);
		viewFlow.setAdapter(new PhotosAdapter(this), 0);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
	}
	
	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	protected void alignToLeft(RelativeLayout ok, int i) {
		params = (LayoutParams) ok.getLayoutParams();
		message_band_params = (LayoutParams) okMessage.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
		ok.setLayoutParams(params);
		message_band_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		message_band_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		message_band_params.addRule(RelativeLayout.RIGHT_OF, R.id.ok_band);
		okMessage.setLayoutParams(message_band_params);
	}

	private void alignToRight(RelativeLayout ok, RelativeLayout okm) {
		params = (LayoutParams) ok.getLayoutParams();
		message_band_params = (LayoutParams) okMessage.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		ok.setLayoutParams(params);
		message_band_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
		message_band_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		message_band_params.addRule(RelativeLayout.LEFT_OF, R.id.ok_band);
		okMessage.setLayoutParams(message_band_params);
	}

	private void blurOtherTiles(int position) {
		View v;
		for (int pos = 0; pos < adapter.getCount(); pos++) {
			v = grid.getChildAt(pos);
			if (v != null) {
				translucentLayer = (RelativeLayout) v.findViewById(R.id.transparant_layout);
				if (pos == position) {
					translucentLayer.setVisibility(View.INVISIBLE);
				} else {
					translucentLayer.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private void startMailAnimation(View view) {
		mail.setBackgroundResource(0);
		//mail.setImageBitmap(null);
		translucentLayer.setBackgroundColor(color_red);
		gridText.setText("2 new");
		mail.setBackgroundResource(R.anim.logo_animation);
		newMailAnimation = (AnimationDrawable) mail
				.getBackground();
		newMailAnimation.start();
	}

	private void setUnreadMail() {
		mail.setBackgroundResource(0);
		mail.setBackgroundResource(R.drawable.unread);
		translucentLayer.setBackgroundColor(color_red);
		gridText.setText("2 new");
		//mail.setBackground(null);
	}

	
	private void defaultGridItemBehavior(AdapterView<?> parent, View view,
			int position) {
		switch (click_count[position]) {
		case 0:
			setTileFootertoRed();
			click_count[position] = 1;
			break;
		case 1:
			setTileFootertoGradient(position);
			click_count[position] = 2;
			break;
		case 2:
			click_count[position] = 0;
			redBand(parent, view, position);
			break;
		default:
			break;
		}
	}

	private void redBand(AdapterView<?> parent, View view, int position) {
		grid.setEnabled(false);
		if ((position == 1) || (position == 2) || (position == 5)
				|| (position == 6) || (position == 7)) {
			alignToLeft(ok, okMessage.getId());
		} else if ((position == 3) || (position == 4)) {
			alignToRight(ok, okMessage);
		}
		redBand.show(view);
		blurOtherTiles(position);
	}

	private void setTileFootertoGradient(int position) {
		if (position == 3)
			translucentLayer.setBackgroundColor(getResources().getColor(R.color.white));
		else
			translucentLayer.setBackgroundResource(R.drawable.name_gradient);
	}

	private void setTileFootertoRed() {
		translucentLayer.setBackgroundColor(getResources().getColor(R.color.red_translucent));
	}

	private void onMessageTileClick(View view, int position) {
		mail = (ImageView) view.findViewById(R.id.grid_image);
		if (click_count[position] == 0) {
			startMailAnimation(view);
			click_count[position] = 1;
		} else if (click_count[position] == 1) {
			stopMailAnimation();
			setUnreadMail();
			click_count[position] = 2;
		} else if (click_count[position] == 2) {
			setNoUnreadMail();
			click_count[position] = 0;
		}
	}

	private void setNoUnreadMail() {
		mail.setBackgroundResource(R.drawable.no_msg);
		translucentLayer.setBackgroundColor(getResources().getColor(R.color.white));
	}
	
	private void stopMailAnimation() {
		newMailAnimation.stop();
	}

	private void gridItemInit(View view) {
		translucentLayer = (RelativeLayout) view.findViewById(R.id.textlayout);
		gridText = (TextView) view.findViewById(R.id.grid_text);
	}

	private void loadFriends() {
		startActivityForResult(intent, 1);
		overridePendingTransition(R.anim.choose_card_bottom_up, android.R.anim.fade_out);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode==1){
			if (resultCode==1){
				startActivity(intent);
				overridePendingTransition(R.anim.flip_in,
						R.anim.flip_out);
			}
		}
	}

}
