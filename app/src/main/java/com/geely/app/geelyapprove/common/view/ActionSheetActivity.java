package com.geely.app.geelyapprove.common.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oliver on 2016/11/15.
 */
public class ActionSheetActivity extends Activity {

    private static final String DATA = "DATA";
    private static final String DEFAULT_DATA = "DEFAULT_DATA";
    private static String mTitle;
    public static Boolean[] array;

    public interface OnResult {
        void onResult(int index, String value);
    }

    private static OnResult mResult;

    public static void openActionSheet(Activity context,String title, String[] data, String defData, OnResult result) {
        Intent intent = new Intent(context, ActionSheetActivity.class);
        mResult = result;
        mTitle = title;
        intent.putExtra(DATA, data);
        intent.putExtra(DEFAULT_DATA, defData);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet);
        TextView tv_title = (TextView) findViewById(R.id.action_sheet_title);
        tv_title.setText(mTitle);
        View viewById = findViewById(R.id.action_sheet_rl);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ListView listView = (ListView) findViewById(R.id.lst_container);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final String[] data = getIntent().getExtras().getStringArray(DATA);
        String defData = getIntent().getExtras().getString(DEFAULT_DATA);
        listView.setFilterTouchesWhenObscured(false);
        if (data != null) {
            final List<String> mData = Arrays.asList(data);
            final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_checked_text_color, mData);
            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);
            if (defData != null) {
                int index = -1;
                for (int i = 0; i < data.length; i++) {
                    if (data[i].equals(defData)) {
                        index = i;
                    }
                }
                listView.setItemChecked(index, true);
                listView.setSelection(index);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckedTextView tv = (CheckedTextView) view;
                        String val = tv.getText().toString();
                        listView.setItemChecked(position, true);
                        mResult.onResult(mData.indexOf(val), val);
                        finish();
                    }
                });
            }
        }
    }

}
