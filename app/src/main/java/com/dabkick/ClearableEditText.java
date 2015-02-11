package com.dabkick;

import java.util.Currency;
import java.util.Locale;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClearableEditText extends RelativeLayout {
	private LayoutInflater inflater = null;
	private EditText edit_text;
	private Button btn_clear;
	private TextView currency_symbol, cancel;
	private Context c;
	
	public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();

	}

	public ClearableEditText(Context context) {
		super(context);
		initViews();
	}

	void initViews() {
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.clearable_edit_text, this, true);
		edit_text = (EditText) findViewById(R.id.clearable_edit);
		// currency_symbol=(TextView)findViewById(R.id.symbol);
		// currency_symbol.setText(getsymbol());
		//cancel = (TextView) findViewById(R.id.cancel);
		btn_clear = (Button) findViewById(R.id.clearable_button_clear);
		removeCross();
		clearText();
		showHideClearButton();
		edit_text.setCursorVisible(true);
		edit_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit_text.requestFocus();
			}
		});
		
		
		/*cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cancel.setText("");
				edit_text.setText("");
				// edit_text.getWindowToken();
				hideSoftKeyboard(c);
			}
		});*/
	}
	
	public void hideSoftKeyboard (Context c) 
	{
	    InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(edit_text.getApplicationWindowToken(), 0);
	    edit_text.setCursorVisible(false);
	}

	private void cancelListener() {
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	void clearText() {
		btn_clear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				edit_text.setText("");
				//hideSoftKeyboard(c);
			}
		});
	}

	void showHideClearButton() {
		edit_text.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0)
					btn_clear.setVisibility(RelativeLayout.VISIBLE);
				else
					removeCross();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void afterTextChanged(Editable s) {

			}
		});
	}

	public Editable getText() {
		Editable text = edit_text.getText();
		return text;
	}
	
	public void clear() {
		edit_text.setText("");
	}

	/**
	 * Method for getting device selected Currency code and Symbol according to
	 * the country
	 */

	private String getsymbol() {
		String locale_language = getResources().getConfiguration().locale
				.getLanguage();
		String local_country = getResources().getConfiguration().locale
				.getCountry();
		Locale locale = new Locale(locale_language, local_country);
		Currency currency = Currency.getInstance(locale);
		String symbol = currency.getSymbol();
		String cur_name = currency.getCurrencyCode();
		return cur_name + "   " + symbol;

	}
	

	public Context getC() {
		return c;
	}

	public void setC(Context c) {
		this.c = c;
	}

	public void removeCross() {
		btn_clear.setVisibility(RelativeLayout.INVISIBLE);
	}
	

}
