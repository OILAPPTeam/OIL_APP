package com.oil.adapter;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oil.activity.R;
import com.oil.adapter.PcListAdapter.ListItemView;
import com.oil.utils.ParamsUtil;

@SuppressLint("NewApi")
public class FMListAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	public List<Map<String, Object>> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private boolean[] hasChecked; // 记录商品选中状态

	public final class ListItemView { // 自定义控件集合
		public TextView pl_name;
		public TextView jinzhan;
		public TextView create_date;
		public TextView create_time;
		public TextView verfier;
		public TextView status;
	}

	public FMListAdapter(Context context, List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		hasChecked = new boolean[getCount()];
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 记录勾选了哪个物品
	 * 
	 * @param checkedID
	 *            选中的物品序号
	 */
	private void checkedChange(int checkedID) {
		hasChecked[checkedID] = !hasChecked[checkedID];
	}

	/**
	 * 判断物品是否选择
	 * 
	 * @param checkedID
	 *            物品序号
	 * @return 返回是否选中状态
	 */
	public boolean hasChecked(int checkedID) {
		return hasChecked[checkedID];
	}

	/**
	 * 显示物品详情
	 * 
	 * @param clickID
	 */
	private void showDetailInfo(int clickID) {
		new AlertDialog.Builder(context)
				.setTitle("物品详情：" + listItems.get(clickID).get("info"))
				.setMessage(listItems.get(clickID).get("detail").toString())
				.setPositiveButton("确定", null).show();
	}

	/**
	 * ListView Item设置
	 */
	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("method", "getView");
		final int selectID = position;
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.fmaint_list_layout,
					null);
			// 获取控件对象
			listItemView.pl_name = (TextView) convertView
					.findViewById(R.id.pl_name);
			listItemView.jinzhan = (TextView) convertView
					.findViewById(R.id.jinzhan);
			listItemView.create_date = (TextView) convertView
					.findViewById(R.id.create_date);
			listItemView.create_time = (TextView) convertView
					.findViewById(R.id.create_time);
			listItemView.verfier = (TextView) convertView
					.findViewById(R.id.verfier);
			listItemView.status = (TextView) convertView
					.findViewById(R.id.status);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// Log.e("image", (String) listItems.get(position).get("title")); //测试
		// Log.e("image", (String) listItems.get(position).get("info"));

		// 设置文字和图片
		listItemView.pl_name.setText((String) listItems.get(position).get(
				"pl_name")
				+ "|"
				+ (String) listItems.get(position).get("pl_section_name")
				+ "|" + (String) listItems.get(position).get("pl_spec_name"));
		listItemView.jinzhan.setText((String) listItems.get(position).get(
				"jinzhan"));
		listItemView.create_date.setText((String) listItems.get(position).get(
				"create_date"));
		listItemView.create_time.setText((String) listItems.get(position).get(
				"create_time"));
		listItemView.verfier.setText((String) listItems.get(position).get(
				"verfier"));
		listItemView.status.setText((String) listItems.get(position).get(
				"verify"));
		Drawable btnDrawable = ParamsUtil.getBackground(context, (String) listItems.get(position).get(
				"verify")); 
		listItemView.status.setBackgroundDrawable(btnDrawable);

		return convertView;
	}

	public void addItem(Map<String, Object> item) {
		listItems.add(item);
	}
}
