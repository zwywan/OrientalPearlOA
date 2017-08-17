package com.geely.app.geelyapprove.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.orientalpearl.R;

import java.util.List;

/**
 * Created by xx on 2016/6/22.
 */
public class AreaAdapter extends BaseAdapter {

    private List<DataListEntity> mDatas;
    private Context mContext;

    public AreaAdapter(Context context, List<DataListEntity> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataListEntity areaBean = (DataListEntity) getItem(position);
        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_listview, null);//布局文件的第一个控件的属性会失效
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview, parent,false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
        tv.setText(areaBean.getDicDesc());
        return convertView;
    }
}
