package cn.pdc.calendar.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.pdc.calendar.R;
import cn.pdc.calendar.entity.MyActivity;

public class ActivityAdapter extends BaseAdapter {

	private Context mContext;
	private List<MyActivity> activityList;

	public ActivityAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public ActivityAdapter(Context mContext, List<MyActivity> activityList) {
		super();
		this.mContext = mContext;
		this.activityList = activityList;
	}

	public void setActivityList(List<MyActivity> activityList) {
		this.activityList = activityList;
	}

	@Override
	public int getCount() {
		return activityList.size();
	}

	@Override
	public Object getItem(int position) {
		return activityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ActivityHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_activity, null);
			holder = new ActivityHolder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.title);
			holder.tv_location = (TextView) convertView
					.findViewById(R.id.location);
			holder.tv_start = (TextView) convertView.findViewById(R.id.start);
			holder.tv_end = (TextView) convertView.findViewById(R.id.end);
			holder.tv_description = (TextView) convertView
					.findViewById(R.id.description);
			convertView.setTag(holder);
		} else {
			holder = (ActivityHolder) convertView.getTag();
		}

		MyActivity activity = activityList.get(position);
		holder.tv_title.setText(activity.getTitle());
		holder.tv_location.setText(activity.getLocation());
		holder.tv_start.setText(activity.getStart());
		holder.tv_end.setText(activity.getEnd());
		holder.tv_description.setText(activity.getDescription());

		return convertView;
	}

	private class ActivityHolder {
		TextView tv_title;
		TextView tv_location;
		TextView tv_start;
		TextView tv_end;
		TextView tv_description;
	}
}
