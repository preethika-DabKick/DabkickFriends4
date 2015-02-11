package com.dabkick.freinds;

import java.util.ArrayList;
import java.util.List;

import com.dabkick.ClearableEditText;
import com.dabkick.CustomSearchViewHandler;
import com.dabkick.R;
import com.dabkick.contextmenu.AddFriend;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendsActivity extends Activity {

	public static final String TAG = "DabKick";
	SwipeListView swipelistview;
	ItemAdapter adapter;
	List<ItemRow> itemData;
	boolean listOpen = false;
	CustomSearchViewHandler searchBarHandler;
	int req = 0;
	int openPosition = -1;
	ViewGroup newView;
	RelativeLayout container;
	ImageView addFriend, cross;
	RelativeLayout pendingRequestsLayout, newRequestsLayout;
	LayoutParams newRequestParams, pendingRequestParams;
	Handler handler;
	ClearableEditText clearEditText;
	InputMethodManager imm;
	DisplayMetrics metrics;
	int measuredWidth = 0, measuredHeight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		int pos = intent.getIntExtra("position", 0);
		Log.d(TAG, "pos "+pos);
		init();
		
		onAddFriendClick();
		onCrossClick();
		swipeListViewListener();
		swipeSettings();
		swipelistview.setSmoothScrollbarEnabled(true);
		swipelistview.setSelection(pos);
		for (int i = 0; i < 10; i++) {
			itemData.add(new ItemRow("Swipe Item" + i, getResources()
					.getDrawable(R.drawable.dabby)));
		}
		adapter.notifyDataSetChanged();
		Log.d(TAG, "done");
	}

	private void swipeListViewListener() {
		swipelistview.setSwipeListViewListener(new BaseSwipeListViewListener() {
			@Override
			public void onOpened(int position, boolean toRight) {
				Log.d(TAG, "onOpened");
				listOpen = true;
			}

			@Override
			public void onClosed(int position, boolean fromRight) {
				Log.d(TAG, "onClosed");
				listOpen = false;
			}

			@Override
			public void onListChanged() {
				Log.d(TAG, "onListChanged");
			}

			@Override
			public void onMove(int position, float x) {
				Log.d(TAG, "onListChanged");
			}

			@Override
			public void onStartOpen(int position, int action, boolean right) {
				Log.d(TAG, "onStartOpen");
				Log.d(TAG, String.format("onStartOpen %d - action %d",
						position, action));
				swipelistview.closeOpenedItems();

			}

			@Override
			public void onStartClose(int position, boolean right) {
				Log.d(TAG, "onStartClose");
				Log.d(TAG, String.format("onStartClose %d", position));
			}

			@Override
			public void onClickFrontView(int position) {
				Log.d(TAG, "onClickFrontView");
				swipelistview.closeOpenedItems();
				swipelistview.openAnimate(position);
			}

			@Override
			public void onClickBackView(int position) {
				Log.d(TAG, "onClickBackView");
				Log.d(TAG, String.format("onClickBackView %d", position));
				swipelistview.closeAnimate(position); 
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				Log.d(TAG, "onDismiss");

			}

		});
	}

	private void onCrossClick() {
		cross.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "req " + req);
				if (req == 0) {
					addPendingRequestsView(pendingRequestsLayout);
					req++;
				} else if (req == 1) {
					removePendingRequestsView(pendingRequestsLayout);
					req++;
				} else if (req == 2) {
					addPendingRequestsView(newRequestsLayout);
					req++;
				} else if (req == 3) {
					removePendingRequestsView(newRequestsLayout);
					req = 0;
				}
			}
		});
	}

	private void onAddFriendClick() {
		addFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(FriendsActivity.this, AddFriend.class));
			}
		});
	}

	private void swipeSettings() {
		// These are the swipe listview settings. you can change these
		// setting as your requirement
		swipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		// there are five swiping modes
		swipelistview.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
		// swipelistview.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
		// there are four swipe actions
		swipelistview.setSwipeActionRight(SwipeListView.SWIPE_ACTION_DISMISS);
		//float width = measuredWidth;
		swipelistview.setOffsetLeft(measuredWidth - convertDpToPixel(164f)); // left side offset
		Log.d(TAG, "164 dp to pixels "+convertDpToPixel(164f));
		Log.d(TAG, "82 dp to pixels "+convertDpToPixel(82f));
		// swipelistview.setOffsetRight(convertDpToPixel(100f)); // right side
		// offsetrfg
		swipelistview.setAnimationTime(500); // Animation time
		swipelistview.setSwipeOpenOnLongPress(false);
		// enable or disable SwipeOpenOnLongPress
		swipelistview.setSwipeCloseAllItemsWhenMoveList(true);
		// swipelistview.

		swipelistview.setAdapter(adapter);
	}

	private void init() {
		swipelistview = (SwipeListView) findViewById(R.id.example_swipe_lv_list);
		addFriend = (ImageView) findViewById(R.id.imageView1);
		cross = (ImageView) findViewById(R.id.imageView3);
		container = (RelativeLayout) findViewById(R.id.container);
		//clearEditText = (ClearableEditText) findViewById(R.id.editText1);
		View searchBar = (View) findViewById(R.id.searchBar);
		searchBarHandler = new CustomSearchViewHandler(this, searchBar);
		itemData = new ArrayList<ItemRow>();
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		measuredHeight = metrics.heightPixels;
		measuredWidth = metrics.widthPixels;
		Log.d(TAG, "width " + measuredWidth + " and height "+ measuredHeight);
		handler = new Handler();
		adapter = new ItemAdapter(this, R.layout.custom_row, itemData, handler);
		
		params();
		imm = (InputMethodManager) getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		
		setupSearchBar();
		
	}

	private void params() {
		pendingRequestsLayout = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.friend_requests, container, false);
		pendingRequestParams = (LayoutParams) pendingRequestsLayout
				.getLayoutParams();
		
		pendingRequestParams.addRule(RelativeLayout.BELOW, R.id.search_bar);
		pendingRequestsLayout.setLayoutParams(pendingRequestParams);
		Log.d(TAG,"done");
		newRequestsLayout = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.new_request, container, false);
		newRequestParams = (LayoutParams) pendingRequestsLayout
				.getLayoutParams();
		newRequestParams.addRule(RelativeLayout.BELOW, R.id.search_bar);
		newRequestsLayout.setLayoutParams(newRequestParams);
	}

	/*private void searchTextListener() {
		et.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				EditText myEditText = (EditText) v;

				if (keyCode == EditorInfo.IME_ACTION_SEARCH
						|| keyCode == EditorInfo.IME_ACTION_DONE
						|| event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

					if (!event.isShiftPressed()) {
						Log.i(TAG, "Enter Key Pressed!");
						search(clearEditText.getText().toString());

						imm.hideSoftInputFromWindow(
								myEditText.getApplicationWindowToken(), 0);
						myEditText.setCursorVisible(false);

						return true;
					}

				}
				return false; // pass on to other listeners.

			}

		});
	}*/

	private void search(String DabName) {
		Toast.makeText(this, "Searching "+DabName+". Please wait...", Toast.LENGTH_LONG).show();
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */

	private void removePendingRequestsView(final RelativeLayout additionalLayout) {
		if (container.findViewById(R.id.parentLayout) != null) {
			container.removeView(additionalLayout);
			RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.listlayout);
			LayoutParams lp3 = (LayoutParams) relativeLayout3
					.getLayoutParams();
			lp3.addRule(RelativeLayout.BELOW, R.id.search_bar);
		}
	}

	private void addPendingRequestsView(final RelativeLayout additionalLayout) {
		try {
			container.addView(additionalLayout);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.listlayout);
		LayoutParams lp3 = (LayoutParams) relativeLayout3
				.getLayoutParams();
		lp3.addRule(RelativeLayout.BELOW, R.id.parentLayout);
	}

	@Override
	public void onBackPressed() {
		/*if (imm.isAcceptingText()) {
			clearEditText.removeCross();
		}*/
		super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in, R.anim.choose_card_top_down);

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    // Checks whether a hardware keyboard is available
	    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
	        Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
	        Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
	        //clearEditText.removeCross();
	        //clearEditText.clear();
	    }
	}
	
	private void setupSearchBar() {

        searchBarHandler.setOnQueryTextListener(new CustomSearchViewHandler.OnQueryTextListener() {
            @Override
            public void onQueryTextChanged(CharSequence s, int start, int before, int count) {

            }

            //This function get called when the query text become empty
            @Override
            public void onQueryTextEmpty() {
                //Reset all the data
                ArrayAdapter<String> mAdapter = (ArrayAdapter<String>) swipelistview.getAdapter();
                mAdapter.getFilter().filter("");
            }

            //This function get called when the search button pressed
            @Override
            public void onQuerySubmit(String queryText) {
                //Search data
                ArrayAdapter<String> mAdapter = (ArrayAdapter<String>) swipelistview.getAdapter();
                mAdapter.getFilter().filter(queryText);
            }
        });
    }
	
	/*private void setupListView() {
        ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();

        for (String s : Samples.sampleStrings) {
            UserInfo user = new UserInfo();
            user.name = s;
            arrayList.add(user);
        }

        CustomAdapter mAdapter = new CustomAdapter(this,
                R.layout.custom_row, arrayList);
        listView.setAdapter(mAdapter);
        listView.setTextFilterEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test", String.valueOf(position));
            }
        });

    }*/

	
}
