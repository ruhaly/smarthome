package com.changhong.smarthome.phone.cinema.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: ChildCinemaAdapter
* @author yang_jun
* @Description:child cinema gridview adapter 
*/
public class ChildCinemaAdapter extends BaseAdapter {

	// ListView listView;
	LayoutInflater m_inflater;

	Context context;

	ArrayList<Cinema> cinemaList;

	public ChildCinemaAdapter(Context context, ArrayList<Cinema> cinemaList2) {

		m_inflater = LayoutInflater.from(context);
		this.context = context;
		this.cinemaList = cinemaList2;
	}

	@Override
	public int getCount() {
		return cinemaList.size();
	}

	@Override
	public Object getItem(int position) {
		return cinemaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder2 viewholder;
		BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		if (convertView == null) {
			viewholder = new ViewHolder2();
			convertView = m_inflater.inflate(R.layout.childetc_item, parent,
					false);
			viewholder.childtext = (TextView) convertView
					.findViewById(R.id.childtext);

			viewholder.childimage = (ImageView) convertView
					.findViewById(R.id.childimage);
			convertView.setTag(viewholder);

		} else {
			viewholder = (ViewHolder2) convertView.getTag();
		}
		viewholder.childtext.setText(cinemaList.get(position).getContentName());

		bitmapUtilsHead.display(viewholder.childimage, cinemaList.get(position)
				.getPicUrl());

		// viewholder.filecontentProvider.setText(contentProviders[position]);
		return convertView;
	}

}

class ViewHolder2 {
	ImageView childimage;

	TextView childtext;

}
