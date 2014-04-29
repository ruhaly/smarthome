package com.changhong.smarthome.phone.cinema.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.Title;

/**
* @ClassName: PopupAdapter
* @author yang_jun
* @Description:弹出窗口的adapter,like 欧美，高清，内地，武侠 
*/
public class PopupAdapter extends BaseAdapter {

	// ListView listView;
	LayoutInflater m_inflater;
	Context context;
	ArrayList<Title> cinemaList;

	public PopupAdapter(Context context,ArrayList<Title> cinemaList2) {

		m_inflater = LayoutInflater.from(context);
		this.context = context;
		this.cinemaList=cinemaList2;
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
	    ViewHolderTitle viewholder;
		if (convertView == null) {
			viewholder = new ViewHolderTitle();
			convertView = m_inflater.inflate(R.layout.popup_item,
					parent, false);
			viewholder.title_name = (TextView) convertView
					.findViewById(R.id.popup_text);
			//viewholder.hottitle_point = (TextView) convertView
			convertView.setTag(viewholder);
			
		} else {
			viewholder = (ViewHolderTitle) convertView.getTag();
		}
		viewholder.title_name.setText(cinemaList.get(position).getTitle_name().toString());
		//viewholder.hottitle_point.setText(cinemaList.get(position).getPoint().toString());
		// viewholder.filecontentProvider.setText(contentProviders[position]);
		return convertView;
	}

}

class ViewHolderTitle {
	TextView title_name;
	TextView title_id;
}
