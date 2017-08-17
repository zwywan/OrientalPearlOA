package com.geely.app.geelyapprove.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.home.view.DetailFragment;
import com.geely.app.geelyapprove.activities.home.view.StartNewFragment;
import com.geely.app.geelyapprove.common.entity.AddressEntity;
import com.geely.app.geelyapprove.common.entity.CompanyEntity;
import com.geely.app.geelyapprove.common.entity.PayeeEntity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.CacheManger;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.Map;

public class LibMainActivity extends AppCompatActivity implements HandToolbar.OnButtonsClickCallback {

    private HandToolbar handToolbar;
    private String username;
    public static final String USER_NAME = "USER_NAME";
    public static final String PASS_WORD = "PASS_WORD";
    private boolean loginSucceed = false;
    private RadioGroup bottomBar;
    private Fragment[] fragments = new Fragment[3];
    private FragmentManager supportFragmentManager;
    private int lastIndex = -1, currentIndex;
    private static final int LOGIN_SUCESS = 0;
    private static final int LOGIN_FAILED = 1;
    private String[] title;
    private String password;
    private RadioButton todo;
    private RadioButton start;
    private RadioButton news;


    /**
     * 调用入口
     * @param context
     * @param username
     */
    public static void startActivity(Context context, String username,String password) {
        Intent intent = new Intent(context, LibMainActivity.class);
        intent.putExtra(USER_NAME, username);
        intent.putExtra(PASS_WORD,password);
        context.startActivity(intent);

    }

    private void initTitle() {
        title = new String[]{this.getString(R.string.to_do_list),this.getString(R.string.my_request),this.getString(R.string.add_request)};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initView();
        acceptParam();
        supportFragmentManager = getSupportFragmentManager();
        initFragment(false);
        getData();

    }

    private void acceptParam() {
        username = getIntent().getStringExtra(USER_NAME);
        password = getIntent().getStringExtra(PASS_WORD);
        if (username != null) {
            login(username,password);
        } else {
            ToastUtil.showToast(getApplicationContext(),"未输入登陆用户");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void login(final String username,String password) {
//        Map<String, Object> param = CommonValues.getCommonParams(geta());
//        param.put("userId", username);
//        param.put("password", password);
//        param.put("tg","oa");
//        HttpManager.getInstance().requestResultForm(CommonValues.REQ_LOGIN_GET, param, LoginEntity.class, new HttpManager.ResultCallback<LoginEntity>() {
//            @Override
//            public void onSuccess(String content, final LoginEntity entity) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (entity != null ) {
//                            if (entity.getCode().equals("100")  && entity.getCode() != null) {
//                                GeelyApp.setLoginEntity(entity);
                                loginSucceed = true;
                                handler.sendEmptyMessage(LOGIN_SUCESS);
//                            } else {
//                                loginSucceed = false;
//                                handler.sendEmptyMessage(LOGIN_FAILED);
//                            }
//                        } else {
//                            loginSucceed = false;
//                            handler.sendEmptyMessage(LOGIN_FAILED);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(String content) {
//                LoginBean loginEntity = GsonUtil.parseJsonToBean(content, LoginBean.class);
//                show(loginEntity.getMsg());
//                loginSucceed = false;
//                handler.sendEmptyMessage(LOGIN_FAILED);
//            }
//        });
    }

    private void show(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void changeFragment(Fragment frag) {
        if (frag == null) return;
        if (lastIndex == currentIndex) {
            return;
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (lastIndex < currentIndex) {
            transaction.setCustomAnimations(R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out, R.anim.fragment_slide_left_in, R.anim.fragment_slide_right_out);
        } else {
            transaction.setCustomAnimations(R.anim.fragment_slide_left_in, R.anim.fragment_slide_right_out, R.anim.fragment_slide_left_out, R.anim.fragment_slide_right_in);
        }
        transaction.hide(fragments[lastIndex]);
        transaction.show(frag);
        transaction.commitNow();

    }


    private void initView() {
        setContentView(R.layout.lib_activity_main);
        //pb = (ProgressBar) findViewById(R.id.lib_pb);
        handToolbar = (HandToolbar) findViewById(R.id.toolbar);
        handToolbar.setButtonsClickCallback(LibMainActivity.this);
        handToolbar.setDisplayHomeAsUpEnabled(true, this);
        handToolbar.setBackHome(true,this);
        bottomBar = (RadioGroup) findViewById(R.id.bottom_bar);
        todo = (RadioButton) findViewById(R.id.rb_main_todo);
        start = (RadioButton) findViewById(R.id.rb_main_start);
        news = (RadioButton) findViewById(R.id.rb_main_new);
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                lastIndex = currentIndex;

                if (checkedId == R.id.rb_main_todo) {
                    currentIndex = 0;
                    changeFragment(fragments[0]);
                    fragments[0].onPause();
                    handToolbar.setTitle(title[0]);

                } else if (checkedId == R.id.rb_main_start) {
                    currentIndex = 1;
                    changeFragment(fragments[1]);
                    fragments[1].onPause();
                    handToolbar.setTitle(title[1]);

//                case R.id.rb_main_tome:
//                    currentIndex = 2;
//                    changeFragment(fragments[2]);
//                    break;
                } else if (checkedId == R.id.rb_main_new) {
                    currentIndex = 2;
                    changeFragment(fragments[2]);
                    handToolbar.setTitle(title[2]);

                }
            }
        });
    }

    private void initFragment(boolean loginSucceed) {
        if(fragments == null) return;
        if (loginSucceed) {
            if (supportFragmentManager.getFragments() != null && supportFragmentManager.getFragments().size() != 0) {
                supportFragmentManager.getFragments().clear();
            }
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            fragments[0] = DetailFragment.newInstance(PageConfig.PAGE_MY_COMMISSION);
            fragments[1] = DetailFragment.newInstance(PageConfig.PAGE_MY_LAUNCH);
            //fragments[2] = DetailFragment.newInstance(PageConfig.PAGE_CC_TO_ME);
            fragments[2] = new StartNewFragment();
            for (int i = 0; i < fragments.length; i++) {
                transaction.add(R.id.fl_container, fragments[i]);
                transaction.hide(fragments[i]);
            }

            if(transaction!=null){
                transaction.show(fragments[0]);
                transaction.commitNow();
            }
            currentIndex = 0;


        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == LOGIN_SUCESS) {
                initFragment(true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todo.setEnabled(true);
                        news.setEnabled(true);
                        start.setEnabled(true);
                    }
                });


            } else if (what == LOGIN_FAILED) {
                initFragment(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todo.setEnabled(false);
                        news.setEnabled(false);
                        start.setEnabled(false);
                    }
                });

                //pb.setVisibility(View.VISIBLE);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onButtonClickListner(HandToolbar.VIEWS views, int radioIndex) {

    }

    public void getData() {
        HttpManager manager = HttpManager.getInstance();
        final Map<String, Object> params = CommonValues.getCommonParams(getApplicationContext());

        //公司集合
        manager.requestResultForm(CommonValues.GET_COMPANY_LIST, params, CompanyEntity.class, new HttpManager.ResultCallback<CompanyEntity>() {
            @Override
            public void onSuccess(String content, CompanyEntity companyEntity) {
                CacheManger.getInstance().saveData(CommonValues.GET_COMPANY_LIST,content);
            }

            @Override
            public void onFailure(String content) {
            }
        });
        //银行集合
        manager.requestResultForm(CommonValues.GET_PAYEEBANK_LIST, params, PayeeEntity.class, new HttpManager.ResultCallback<PayeeEntity>() {
            @Override
            public void onSuccess(String content, PayeeEntity payeeEntity) {
                CacheManger.getInstance().saveData(CommonValues.GET_PAYEEBANK_LIST,content);
            }

            @Override
            public void onFailure(String content) {
            }
        });
       //省份集合
        manager.requestResultForm(CommonValues.GET_PRIVINCE_LIST, params, AddressEntity.class, new HttpManager.ResultCallback<AddressEntity>() {
            @Override
            public void onSuccess(String content, AddressEntity addressEntity) {
                CacheManger.getInstance().saveData(CommonValues.GET_PRIVINCE_LIST,content);
            }

            @Override
            public void onFailure(String content) {
            }
        });
    }
}
