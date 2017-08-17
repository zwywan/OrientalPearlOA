package com.geely.app.geelyapprove.common.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.geely.app.geelyapprove.common.adapter.AreaAdapter;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/4/6.
 */

public class AddressDialog extends Dialog {

    OnSuccessCallback callback;
    OnClickListener clickListener;
    private ListView mLv_city;
    private ListView mLv_province;
    private AreaAdapter mProvinceAdapter;
    private AreaAdapter mCityAdapter;
    private List<DataListEntity> provinceLists = new ArrayList<>();
    private List<DataListEntity> cityLists = new ArrayList<>();
    private String title;
    private int mainIndex, subIndex = -1;
    private Context context;
    private String selectedVal;


    public AddressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public AddressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected AddressDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public interface OnSuccessCallback {
        void onSuccess(String province, String cityName);
        void onCancel();
    }

    public interface OnClickListener {
        void onMainItemClickListener(AddressDialog sheet, int index, String value, boolean hasValue);
        void onSubItemClickListener(int mainIndex, String mainValue, int subIndex, String subValue);
    }

    public AddressDialog setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public AddressDialog setOnSuccessCallback(OnSuccessCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        setContentView(getContentView());
    }

    public String getTitle() {
        return title;
    }

    public AddressDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    private View getContentView() {
        View view = getLayoutInflater().inflate(R.layout.pw_view, null);
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancel();
                cancel();
            }
        });
        view.findViewById(R.id.tv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subIndex != -1)
                    callback.onSuccess(provinceLists.get(mainIndex).getDicDesc(), cityLists.get(subIndex).getDicDesc());
                cancel();
            }
        });
        if(title!=null){
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvTitle.setText(title);
        }
        mLv_province = (ListView) view.findViewById(R.id.lv_province);
        mLv_city = (ListView) view.findViewById(R.id.lv_city);

        mProvinceAdapter = new AreaAdapter(context,provinceLists);
        mLv_province.setAdapter(mProvinceAdapter);

        mLv_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickListener.onMainItemClickListener(AddressDialog.this, i, provinceLists.get(i).getDicId(), provinceLists.size() > i ? true : false);
                mainIndex = i;
            }
        });
        return view;
    }


    public void updateSubAdapter(final int mainIndex, final List<DataListEntity> subArgs) {
        cityLists = subArgs;
        mCityAdapter = new AreaAdapter(context,cityLists);

        mLv_city.setAdapter(mCityAdapter);
        mLv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subIndex = position;
                clickListener.onSubItemClickListener(mainIndex, provinceLists.get(mainIndex).getDicDesc(), position, cityLists.get(position).getDicDesc());
            }
        });
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(getContentView());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(getContentView(), params);
    }

    public AddressDialog setData(List<DataListEntity> main, List<DataListEntity> sub, String selectedVal) {
        this.provinceLists = main;
        this.cityLists = sub;
        this.selectedVal = selectedVal;
        setContentView(null);
        return this;
    }

}
