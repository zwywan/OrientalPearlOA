package com.geely.app.geelyapprove.activities.persons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geely.app.geelyapprove.common.adapter.ListAdapter;
import com.geely.app.geelyapprove.common.holder.ViewHolder1;
import com.geely.app.geelyapprove.common.holder.ViewHolder2;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 2017/6/5.
 */

public class PersonListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, HandToolbar.OnButtonsClickCallback {

    private Set<String> set = new HashSet<>();
    private int resultCode = 23333;
    private HandToolbar handToolbar;
    private ListView lv;
    private UsersAdapter myAdapter = null;
    private List<BaseItem> mData = new ArrayList<>();
    private List<BaseItem> baseEntityList = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> userIds = new ArrayList<>();
    private ListAdapter listAdapter = new ListAdapter(strings, R.layout.item_string) {
        @Override
        public void bindView(ViewHolder holder, Object obj) {
            holder.setText(R.id.item_string_tv,obj.toString());
        }

    };
    private RelativeLayout pb;
    private TextView num;
    private Button btn;
    private ListView listView;
    private SearchView etSearch;
    private EditText textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initView() {
        handToolbar = (HandToolbar) findViewById(R.id.toolbar);
        handToolbar.setButtonsClickCallback(PersonListActivity.this);
        handToolbar.setDisplayHomeAsUpEnabled(true, this);
        handToolbar.setTitle("人员选取");
        lv = (ListView) findViewById(R.id.journal_receiver_lv);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
        num = (TextView) findViewById(R.id.attention_person_list_num);
        btn = (Button) findViewById(R.id.attention_person_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addattentionperson();
            }
        });
        listView = (ListView) findViewById(R.id.person_list_lv);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = strings.get(i);
                for (int j = 0; j < mData.size(); j++) {
                    if (mData.get(j).getItem_type() == ViewHolder1.ITEM_VIEW_TYPE_1){
                        ItemBean1 baseItem = (ItemBean1) mData.get(j);
                        if (baseItem.getName().equals(s)){
                            lv.setSelection(j);
                            return;
                        }
                    }
                }
            }
        });
        etSearch = (SearchView) findViewById(R.id.et_search);
        etSearch.setSubmitButtonEnabled(false);
        etSearch.setIconifiedByDefault(false);
        etSearch.setFocusableInTouchMode(false);
        etSearch.setFocusable(false);
        etSearch.setOnClickListener(this);
        textView = (EditText) etSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHintTextColor(0xffbfbdbd);
        textView.setTextColor(0xff333333);
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doFilter(newText);
                return true;
            }
        });
    }

    private void doFilter(String key) {
        if (mData != null && !key.equals("")) {
            if (baseEntityList.size() == 0){
                baseEntityList.addAll(mData);
            }
            List<BaseItem> list = new ArrayList<>();
            for (int i = 0; i < baseEntityList.size(); i++) {
                if (baseEntityList.get(i).getItem_type() == ViewHolder2.ITEM_VIEW_TYPE_2){
                    ItemBean2 baseItem = (ItemBean2) baseEntityList.get(i);
                    if (baseItem.getName() != null && baseItem.getName().contains(key)){
                        list.add(baseItem);
                    }
                    if (baseItem.getZw() != null && baseItem.getZw().contains(key)){
                        list.add(baseItem);
                    }
                    if (baseItem.getStr() != null && baseItem.getStr().toLowerCase().contains(key.toLowerCase())){
                        list.add(baseItem);
                    }
                }
            }
            mData.clear();
            mData.addAll(list);
            myAdapter.notifyDataSetChanged();
        } else if (mData != null) {
            if (baseEntityList != null) {
                mData.clear();
                mData.addAll(baseEntityList);
                baseEntityList.clear();
                myAdapter.notifyDataSetChanged();
            }

        }
    }

    private void addattentionperson() {
        Intent intent = new Intent();
        String name = "";
        for (int i = 0; i < names.size(); i++) {
            if (i == names.size() -1){
                name += names.get(i);
            }else {
                name += names.get(i) + ";";
            }
        }
        String ids = "";
        for (int i = 0; i < userIds.size(); i++) {
            if (i == userIds.size() - 1){
                ids += userIds.get(i);
            }else {
                ids += userIds.get(i) + ";";
            }
        }
        intent.putExtra("name",name);
        intent.putExtra("id",ids);
        intent.putExtra("key",getIntent().getExtras().getString("key"));
        PersonListActivity.this.setResult(resultCode,intent);
        PersonListActivity.this.finish();
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getCommonParams(getApplicationContext());
        HttpManager.getInstance().requestResultForm(CommonValues.GET_ALL_USER, getmap, UsersEntity.class, new HttpManager.ResultCallback<UsersEntity>() {
            @Override
            public void onSuccess(String json, UsersEntity usersEntity) throws InterruptedException {
                final List<UsersEntity.RetDataBean> data = usersEntity.getRetData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        setlist(data);
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getApplicationContext(),"获取数据失败");
                        finish();
                    }
                });
            }
        });
    }

    private void setlist(List<UsersEntity.RetDataBean> data) {
        for (UsersEntity.RetDataBean dataBeanX : data) {
            mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1,dataBeanX.getLetter()));
            strings.add(dataBeanX.getLetter());
            for (UsersEntity.RetDataBean.EntityBean bean : dataBeanX.getEntity()) {
                mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2,false,bean.getNameCN(),bean.getPositionNameCN(),bean.getUserId(),bean.getFirstLetter()));
            }
        }
        myAdapter = new UsersAdapter(this,mData);
        lv.setAdapter(this.myAdapter);
        lv.setOnItemClickListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
                num.setText(set.size() + "人");
                btn.setText("确认(" + set.size() + ")");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mData.get(i).getItem_type() == ViewHolder2.ITEM_VIEW_TYPE_2){
            ItemBean2 baseItem = (ItemBean2) mData.get(i);
            if (baseItem.getChecked()){
                set.remove(baseItem.getUsetId());
                names.remove(baseItem.getName());
                userIds.remove(baseItem.getUsetId());
            }else {
                userIds.add(baseItem.getUsetId());
                set.add(baseItem.getUsetId());
                names.add(baseItem.getName());
            }
            baseItem.setChecked(!baseItem.getChecked());

        }else if (mData.get(i).getItem_type() == ViewHolder1.ITEM_VIEW_TYPE_1){
            ItemBean1 baseItem = (ItemBean1) mData.get(i);
            ToastUtil.showToast(getApplicationContext(),baseItem.getName());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
                num.setText(set.size() + "人");
                btn.setText("确认(" + set.size() + ")");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.et_search) {
            etSearch.setFocusable(true);
            toggleSearchBox(true);
        }
    }

    private void toggleSearchBox(boolean enable) {
        if (enable) {
            etSearch.setFocusableInTouchMode(true);
            etSearch.requestFocus();
        } else {
            etSearch.setFocusableInTouchMode(false);
            etSearch.clearFocus();
        }
    }

    @Override
    public void onButtonClickListner(HandToolbar.VIEWS views, int radioIndex) {

    }
}
