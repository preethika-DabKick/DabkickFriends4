package com.dabkick.livesession;


import com.dabkick.R;
import com.dabkick.contextmenu.AddMedia;
import com.dabkick.contextmenu.VideoMessage;
import com.dabkick.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LeaveMessageActivity extends Activity{
	
	EditText msg;
	String name, url;
	ImageView profilePic;
	TextView friendName;
	RelativeLayout videoMessage,addMedia;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_message);
		
		init();
		retrieveValues();
		friendName.setText(name);
		setImage();
		
		videoMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(LeaveMessageActivity.this,VideoMessage.class),1);
			}
		});
		
		addMedia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(LeaveMessageActivity.this,AddMedia.class),2);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1){
			if (resultCode==Utils.RESULT_CODE_VOICE){
				Toast.makeText(this, "Voice", Toast.LENGTH_SHORT).show();
			}
			else if (resultCode==Utils.RESULT_CODE_VIDEO){
				Toast.makeText(this, "Video", Toast.LENGTH_SHORT).show();
			}
		}
		else if (requestCode==2){
			if (resultCode==Utils.RESULT_CODE_PHOTOS){
				Toast.makeText(this, "Photos", Toast.LENGTH_SHORT).show();
			}
			else if (resultCode==Utils.RESULT_CODE_VIDEOS){
				Toast.makeText(this, "Videos", Toast.LENGTH_SHORT).show();
			}
			else if (resultCode==Utils.RESULT_CODE_MUSIC){
				Toast.makeText(this, "Music", Toast.LENGTH_SHORT).show();
			}
		}
		
		
	}

	private void init() {
		msg = (EditText) findViewById(R.id.message);
		profilePic = (ImageView) findViewById(R.id.friend_image);
		friendName = (TextView) findViewById(R.id.friend_name);
		videoMessage = (RelativeLayout) findViewById(R.id.video_message);
		addMedia = (RelativeLayout) findViewById(R.id.add_media);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.flip_in,R.anim.flip_out);
	}
	
	private void retrieveValues() {
		Intent intent = getIntent();
		name = intent.getStringExtra("Name");
		url = intent.getStringExtra("url");
	}

	private void setImage() {
		final ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher) 
				.cacheInMemory().cacheOnDisc().build();
		imageLoader.displayImage(url, profilePic, options);
	}
}
