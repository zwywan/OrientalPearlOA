package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;


/**
 * Created by Oliver on 2016/9/12.
 */
public class DoubleSelectBSheet extends BottomSheetDialog {
    private SparseArray<String[]> sub;
    private String[] main;
    private ArrayAdapter[] arrayAdapters;
    OnSuccessCallback callback;
    private int[] selectedVal;
    OnClickListener clickListener;
    private boolean allowMultiChoose;
    private ListView lvSub;
    private String title;
    private int mainIndex, subIndex = -1;

    public interface OnSuccessCallback {
        void onSuccess(String province, String cityName);
        void onCancel();
    }

    public boolean isAllowMultiChoose() {
        return allowMultiChoose;
    }

    public DoubleSelectBSheet setAllowMultiChoose(boolean allowMultiChoose) {
        this.allowMultiChoose = allowMultiChoose;
        return this;
    }

    public interface OnClickListener {
        void onMainItemClickListener(DoubleSelectBSheet sheet, int index, String value, boolean hasValue);
        void onSubItemClickListener(int mainIndex, String mainValue, int subIndex, String subValue);
    }

    public DoubleSelectBSheet setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public DoubleSelectBSheet setOnSuccessCallback(OnSuccessCallback callback) {
        this.callback = callback;
        return this;
    }

    public DoubleSelectBSheet(@NonNull Context context) {
        super(context);
    }

    public DoubleSelectBSheet(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected DoubleSelectBSheet(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        setContentView(getContentView());
    }

    public String getTitle() {
        return title;
    }

    public DoubleSelectBSheet setTitle(String title) {
        this.title = title;
        return this;
    }

    private View getContentView() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_query_condition, null);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancel();
                cancel();
            }
        });
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subIndex != -1)
                    callback.onSuccess(main[mainIndex], sub.get(mainIndex)[subIndex]);
                cancel();
            }
        });
        if(title!=null){
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_bs_title);
            tvTitle.setText(title);
        }
        final ListView lvMain = (ListView) view.findViewById(R.id.lv_bs_main);
        lvSub = (ListView) view.findViewById(R.id.lv_bs_sub);
        if (sub == null) {
            sub = new SparseArray<>();
        }
        if (arrayAdapters == null) {
            int size = sub.size();
            arrayAdapters = new ArrayAdapter[main.length];
            for (int i = 0; i < size; i++) {
                if (selectedVal.length > i) {
                    arrayAdapters[i] = new ArrayAdapter(getContext(), R.layout.list_item_checked_text_color, sub.get(i));
                }
            }
        }
        lvMain.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item_checked_background, main));
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                clickListener.onMainItemClickListener(DoubleSelectBSheet.this, pos, main[pos], sub.size() > pos ? true : false);
                mainIndex = pos;
            }
        });
        return view;
    }

    public void updateSubAdapter(final int mainIndex, String[] subArgs) {
        if (sub == null) {
            sub = new SparseArray<>();
        }
        sub.put(mainIndex, subArgs);

        arrayAdapters[mainIndex] = new ArrayAdapter(getContext(), R.layout.list_item_checked_text_color, sub.get(mainIndex));

        lvSub.setAdapter(arrayAdapters[mainIndex]);
        lvSub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //   selectedVal[mainIndex] = position;
                        if (allowMultiChoose) {
                            lvSub.setItemChecked(position, true);
                        }
                        subIndex = position;
                        clickListener.onSubItemClickListener(mainIndex, main[mainIndex], position, sub.get(mainIndex)[position]);
                    }
                });
        arrayAdapters[mainIndex].notifyDataSetChanged();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(getContentView());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(getContentView(), params);
    }

    public DoubleSelectBSheet setData(String[] main, SparseArray<String[]> sub, int[] selectedVal) {
        this.main = main;
        this.sub = sub;
        this.selectedVal = selectedVal;
        setContentView(null);
        return this;
    }

}
