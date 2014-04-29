package com.changhong.smarthome.phone.foundation.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.PreferencesUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.MsgTip;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * 消息设置 [功能详细描述]
 * 
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class MsgTipActivity extends BaseActivity {

	private List<MsgTip> list = new ArrayList<MsgTip>();

	private Adapter adapter;

	private ImageView msg_back_list;

	@ViewInject(R.id.listView)
	private ListView listView;

	@Override
	public void initData() {
		list = getMsgTipList();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.msg_tip_layout);
		ViewUtils.inject(this);
		initAdapter();
		
		msg_back_list = (ImageView) findViewById(R.id.msg_back_list);
		msg_back_list.setOnClickListener(this);
	}

	public void initAdapter() {
		if (null == adapter) {
			adapter = new Adapter();
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.msg_back_list:
			this.finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public MsgTip getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (null == convertView) {
				convertView = LayoutInflater.from(getBaseContext()).inflate(
						R.layout.msg_tip_item_layout, null);
				viewHolder = new ViewHolder();
				viewHolder.tvName = (TextView) convertView
						.findViewById(R.id.tvName);
				viewHolder.cbSwitch = (CheckBox) convertView
						.findViewById(R.id.cbSwitch);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.cbSwitch.setTag(position);
			viewHolder.cbSwitch
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							int position = (Integer) buttonView.getTag();
							if (isChecked) {
								buttonView
										.setButtonDrawable(R.drawable.msg_tip_on);
								PreferencesUtils.putInt(getBaseContext(),
										adapter.getItem(position).getName(), 1);
							} else {
								PreferencesUtils.putInt(getBaseContext(),
										adapter.getItem(position).getName(), 0);
								buttonView
										.setButtonDrawable(R.drawable.msg_tip_off);
							}
						}
					});
			viewHolder.tvName.setText(getItem(position).getName());
			if (0 == getItem(position).getState()) {
				viewHolder.cbSwitch.setButtonDrawable(R.drawable.msg_tip_off);
			} else {
				viewHolder.cbSwitch.setButtonDrawable(R.drawable.msg_tip_on);
			}
			return convertView;
		}

		class ViewHolder {

			public TextView tvName;

			public CheckBox cbSwitch;
		}
	}

	@Override
	public void clearData() {
		list.clear();
	}
}
