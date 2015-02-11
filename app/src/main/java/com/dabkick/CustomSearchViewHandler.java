package com.dabkick;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;


public class CustomSearchViewHandler{


    Activity mActivity;
    View mCustomSearchView;
    RelativeLayout mQueryHintLayout;
    RelativeLayout mQueryHintContent;
    LinearLayout mInnerRoundedRect;
    LinearLayout mEditContentLayout;
    TextView mQueryHintText;
    ListenerEditText mQueryText;
    EditText mQueryTextDummy;
    ImageView mCloseBtn;
    ImageView mEditSearchIcon;
    ImageView mHintSearchIcon;
    AnimatorSet mDismissHintAnim;
    ImageView mMaskView;

    //Listener
    OnQueryTextListener mOnQueryTextListener;
    OnCloseListener mOnCloseListener;

    boolean isSetup;
    float hintX;
    float editX;

    public CustomSearchViewHandler(Activity activity, View customSearchView) {

        mActivity = activity;
        mCustomSearchView = customSearchView;
        mQueryHintLayout = (RelativeLayout) mActivity.findViewById(R.id.queryHintLayout);
        mQueryHintContent = (RelativeLayout) mActivity.findViewById(R.id.queryHintContentLayout);
        mInnerRoundedRect = (LinearLayout) mActivity.findViewById(R.id.innerRoundedRect);
        mCloseBtn = (ImageView) mActivity.findViewById(R.id.searchCloseBtn);
        mEditContentLayout = (LinearLayout) mActivity.findViewById(R.id.editContentLayout);
        mQueryHintText = (TextView) mActivity.findViewById(R.id.queryHintTextView);
        mEditSearchIcon = (ImageView) mActivity.findViewById(R.id.editSearchIcon);
        mHintSearchIcon = (ImageView) mActivity.findViewById(R.id.hintSearchIcon);
        mQueryTextDummy = (EditText) mActivity.findViewById(R.id.searchEditTextDummy);
        mMaskView = (ImageView) mActivity.findViewById(R.id.maskView);

        //replace edit text
        EditText tmp = (EditText) mActivity.findViewById(R.id.searchEditText);
        mQueryText = new ListenerEditText(mActivity,null,tmp);
        RelativeLayout editTextLayout = (RelativeLayout)mActivity.findViewById(R.id.editTextLayout);
        editTextLayout.removeView(tmp);
        editTextLayout.addView(mQueryText);

        isSetup = false;

        //get x coordinate after xml is layout
        mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //prevent listener get called multiple times
                if (!isSetup) {
                    isSetup = true;

                    //get edit view global x
                    int[] location = new int[2];
                    mEditSearchIcon.getLocationOnScreen(location);
                    editX = location[0];

                    mHintSearchIcon.getLocationOnScreen(location);
                    hintX = location[0];

                    mDismissHintAnim = dismissHintAnimation();

                    setupListener();
                }
            }
        });



    }

    void setupListener() {

        mQueryHintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterEditMode();
            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePress();

                if (mOnCloseListener != null)
                {
                    mOnCloseListener.onClosePressed(mCloseBtn);
                }

                if (mOnQueryTextListener != null)
                {
                    mOnQueryTextListener.onQueryTextEmpty();
                }
            }
        });

        mQueryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().isEmpty())
                    mCloseBtn.setVisibility(View.INVISIBLE);
                else
                    mCloseBtn.setVisibility(View.VISIBLE);

                if (mOnQueryTextListener != null)
                {
                    mOnQueryTextListener.onQueryTextChanged(s,start,before,count);

                    if (s.toString().isEmpty())
                        mOnQueryTextListener.onQueryTextEmpty();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mQueryText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    mMaskView.setAlpha(0.5f);
                    resumeQueryMode();
                    mMaskView.setClickable(true);
                }
                else
                {
                    mMaskView.setAlpha(0.0f);
                    mMaskView.setClickable(false);
                }
            }
        });

        mQueryText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    if (mOnQueryTextListener != null)
                    {
                        mOnQueryTextListener.onQuerySubmit(mQueryText.getText().toString());
                    }

                    enterPendingQueryMode();
                    return true;
                }

                return false;
            }
        });

        mMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPendingQueryMode();
            }
        });

        mInnerRoundedRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeQueryMode();
            }
        });

    }

    String getQueryText(){
        return mQueryText.getText().toString();
    }

    EditText getQueryEditText(){
        return mQueryText;
    }

    void setQueryText(String s){
        mQueryText.setText(s);
    }

    ImageView getCloseBtn(){
        return mCloseBtn;
    }

    //handle the close button pressed
    public void closePress()
    {
        if (!mQueryText.getText().toString().isEmpty())
        {
            setQueryText("");
            mCloseBtn.setVisibility(View.INVISIBLE);
        }

        if (!mQueryText.hasFocus())
        {
            resumeQueryMode();
        }

    }

    //handle the settings for searchBar lost focus
    public void enterPendingQueryMode()
    {
        if (mQueryText.getText().toString().isEmpty())
            enterHintMode();

        mQueryText.clearFocus();
        mQueryTextDummy.requestFocus();
        dismissKeyboard();

    }

    //handle the settings for searchBar has focus back
    public void resumeQueryMode()
    {
        mMaskView.setClickable(true);
        mQueryText.requestFocus();

        if (mQueryText.getText().toString().isEmpty())
            mCloseBtn.setVisibility(View.INVISIBLE);
        else
            mCloseBtn.setVisibility(View.VISIBLE);

        showKeyboard();


    }

    //Show up the query hint text
    public void enterHintMode()
    {
        mQueryHintLayout.setAlpha(1);
        mQueryHintLayout.setClickable(true);
        mInnerRoundedRect.setAlpha(0);
        mInnerRoundedRect.setClickable(false);
        mQueryTextDummy.requestFocus();
        dismissKeyboard();
    }

    //Handle the settings for searchBar get focus
    //The only difference to the resumeQueryMode() is
    public void enterEditMode()
    {
        mCustomSearchView.bringToFront();
        mQueryHintLayout.setClickable(false);
        mDismissHintAnim.start();
    }

    //This is the hint dismiss animation
    AnimatorSet dismissHintAnimation()
    {
        int duration = 250;

        mQueryHintLayout.setAlpha(1);
        mInnerRoundedRect.setAlpha(0);
        mQueryHintLayout.setClickable(true);

        final  float hintX = mQueryHintContent.getX();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mQueryHintContent,"x",editX);
        objectAnimator1.setDuration(duration);

        final float hintTextX = mQueryHintText.getX();
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mQueryHintText,"x",-100);
        objectAnimator2.setDuration(duration);

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mQueryHintText,"alpha",0);
        objectAnimator3.setDuration(duration);

        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mInnerRoundedRect,"alpha",1);
        objectAnimator4.setDuration(duration);
        objectAnimator4.setStartDelay(100);

        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mMaskView,"alpha",0.5f);
        objectAnimator5.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1,objectAnimator2,objectAnimator3,objectAnimator4,objectAnimator5);

        objectAnimator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mQueryHintContent.setX(hintX);
                mQueryHintText.setX(hintTextX);
                mQueryHintText.setAlpha(1);
                mQueryHintLayout.setAlpha(0);

                mInnerRoundedRect.setClickable(true);
                mInnerRoundedRect.setAlpha(1.0f);
                mQueryText.requestFocus();


                mMaskView.setClickable(true);

                if (mOnQueryTextListener != null)
                {
                    mOnQueryTextListener.onQueryTextEmpty();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return animatorSet;

    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mQueryText, InputMethodManager.SHOW_FORCED);

    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mQueryText.getWindowToken(), 0);
    }

    //Listener
    public void setOnQueryTextListener(OnQueryTextListener listener)
    {
        mOnQueryTextListener = listener;
    }

    public interface OnQueryTextListener{
        void onQueryTextChanged(CharSequence s, int start, int before, int count);

        //called when query text is empty
        void onQueryTextEmpty();

        //called when search button pressed
        void onQuerySubmit(String queryText);
    }

    public void setOnCloseListener(OnCloseListener listener)
    {
        mOnCloseListener = listener;
    }

    public interface OnCloseListener{
        void onClosePressed(ImageView xButton);
    }

    //Custom editText
    //We need to override the editText to detect back button pressed
    class ListenerEditText extends EditText{

        public ListenerEditText(Context context, AttributeSet attrs,EditText editText) {
            super(context, attrs);
            clone(editText);
        }

        void clone(EditText editText){
            this.setLayoutParams(editText.getLayoutParams());
            this.setBackgroundResource(0);
            this.setCursorVisible(true);
            this.setCursorVisible(true);
            this.setFocusable(editText.isFocusable());
            this.setImeOptions(editText.getImeOptions());
            this.setInputType(editText.getInputType());
            this.setPadding(editText.getPaddingLeft(),editText.getPaddingTop(),editText.getPaddingRight(),editText.getPaddingBottom());
        }

        @Override
        public boolean onKeyPreIme (int keyCode, KeyEvent event){
            if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                enterPendingQueryMode();
            }
            return false;
        }
    }


}