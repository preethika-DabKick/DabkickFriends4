package com.dabkick.choosefriends;

import java.util.ArrayList;

import com.dabkick.R;
import com.dabkick.homepage.HomePageActivity;
import com.dabkick.utils.Utils;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddressFragment extends Fragment {

	ListView contacts_list;
	RelativeLayout container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.address_fragment, container,
				false);
		contacts_list = (ListView) view.findViewById(R.id.contacts);
		container = (RelativeLayout) view.findViewById(R.id.container);
		LoadContacts lca = new LoadContacts();
		lca.execute();

        //addSearchView(container, view);

		contacts_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				interactLive(position);
			}
		});
		return view;
	}

    private void addSearchView(ViewGroup container, View view) {
        RelativeLayout additionalLayout = (RelativeLayout) getActivity().getLayoutInflater().inflate(
                R.layout.friend_requests, container, false);
        LayoutParams pendingRequestParams = (LayoutParams) additionalLayout
                .getLayoutParams();

        pendingRequestParams.addRule(RelativeLayout.BELOW, R.id.container);
        additionalLayout.setLayoutParams(pendingRequestParams);

        try {
            container.addView(additionalLayout);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) view.findViewById(R.id.container);
        LayoutParams lp3 = (LayoutParams) relativeLayout3
                .getLayoutParams();
        lp3.addRule(RelativeLayout.BELOW, R.id.parentLayout);
    }

	public void interactLive(int position) {
		final Dialog d = new Dialog(getActivity());
		//final String name = dbNames[position];
		
		d.setContentView(R.layout.alert_dialog);
		TextView tv = (TextView) d.findViewById(R.id.interact_live_message);
		//tv.setText(name + " - Proceed?");
		d.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		RelativeLayout no = (RelativeLayout) d.findViewById(R.id.layout_no);
		RelativeLayout yes = (RelativeLayout) d.findViewById(R.id.layout_yes);

		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});

		yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(context, InteractLiveActivity.class);
				intent.putExtra("Name", name);
				intent.putExtra("url", url);
				activity.startActivity(intent);
				activity.overridePendingTransition(R.anim.flip_in,
						R.anim.flip_out);
				activity.finish();*/
				d.dismiss();
				Intent intent = new Intent(getActivity(), HomePageActivity.class);
				intent.putExtra("Activity", Utils.INTERACT_LIVE);
				getActivity().overridePendingTransition(android.R.anim.fade_in, R.anim.choose_card_top_down);
				getActivity().startActivity(intent);

			}
		});

		d.show();
	}





	class LoadContacts extends AsyncTask<Void, Void, ArrayList<String>> {
		// ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*
			 * pd = ProgressDialog.show(getActivity(), "Loading Contacts",
			 * "Please Wait");
			 */
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<String> contacts = new ArrayList<String>();
			// PhoneContacts phoneContacts[]=new PhoneContacts[];
			Cursor c = getActivity().getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					null, null, null);
			int i = 0;
			while (c.moveToNext()) {

				String contactName = c
						.getString(c
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String phNumber = c
						.getString(c
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
				i++;
				contacts.add(contactName);

			}
			c.close();
			return contacts;
		}

		@Override
		protected void onPostExecute(ArrayList<String> contacts) {
			super.onPostExecute(contacts);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), R.layout.contacts_style, contacts);

			contacts_list.setAdapter(adapter);

		}

	}

}
