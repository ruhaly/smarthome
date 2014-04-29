package com.changhong.smarthome.phone.cinema.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.activity.MyShareActivity;
import com.changhong.smarthome.phone.cinema.activity.RecordActivity;
import com.changhong.smarthome.phone.cinema.activity.UploadingActivity;

public class TitleFragment extends Fragment implements OnClickListener{

	private int mColorRes = -1;
	private LinearLayout share;
	private LinearLayout uploading;
	private LinearLayout record;

	public TitleFragment() {
		setRetainInstance(true);
	}

	public TitleFragment(int colorRes) {
		mColorRes = colorRes;
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	/*	if (savedInstanceState != null)
			mColorRes = savedInstanceState.getInt("mColorRes");
		int color = getResources().getColor(mColorRes);*/
		// construct the RelativeLayout
		/*
		 * RelativeLayout v = new RelativeLayout(getActivity());
		 * v.setBackgroundColor(color);
		 */
		View v = inflater.inflate(R.layout.left_title, container, false);
		share =(LinearLayout) v.findViewById(R.id.share);
		uploading = (LinearLayout) v.findViewById(R.id.uploading);
		record = (LinearLayout) v.findViewById(R.id.record);
		share.setOnClickListener(this);
		uploading.setOnClickListener(this);
		record.setOnClickListener(this);
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putInt("mColorRes", mColorRes);
	}

	@Override
	public void onClick(View v) {
          Intent intent = new Intent();
		intent.putExtra("titleLogo", v.getId());
		if(v.getId()==R.id.share){
			intent.setClass(getActivity(), MyShareActivity.class);
			startActivity(intent);
		}else if(v.getId()==R.id.uploading){
			intent.setClass(getActivity(), UploadingActivity.class);
			startActivity(intent);
		}else if(v.getId()==R.id.record){
			intent.setClass(getActivity(), RecordActivity.class);
			startActivity(intent);
		}
		
		
	}
	/**
	*/
	public static int dip2px(Context context, float dpValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (dpValue * scale + 0.5f);
	}

	/**
	*/
	public static int px2dip(Context context, float pxValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (pxValue / scale + 0.5f);
	}

}
