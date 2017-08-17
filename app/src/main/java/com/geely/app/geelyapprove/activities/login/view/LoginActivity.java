package com.geely.app.geelyapprove.activities.login.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.LibMainActivity;
import com.geely.app.geelyapprove.activities.login.entity.LoginBean;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.Fields;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.SpUtils;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.Locale;
import java.util.Map;

import static com.geely.app.geelyapprove.common.utils.Fields.SAVE_PASSWORD;
import static com.geely.app.geelyapprove.common.utils.Fields.USERID;


public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextInputLayout inputLayout;
    ImageView ivAvatar;
    private boolean savePassword;
    private ProgressBar pb;
    private CheckBox savepassword;
    private RadioButton rb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh")){
            GeelyApp.setLanguage("CN");
        }else {
            GeelyApp.setLanguage("EN");
        }
        etUsername.setText(SpUtils.getString(getApplicationContext(),Fields.USERID));
        savePassword = SpUtils.getisBoolean_false(getApplicationContext(), Fields.SAVE_PASSWORD,false);
        etPassword.setText(SpUtils.getString(getApplicationContext(),Fields.PASSWORD));
        savepassword.setChecked(savePassword);
    }



    private void initView() {
        pb = (ProgressBar) findViewById(R.id.login_pb);
        etUsername = (EditText) findViewById(R.id.login_en_username);
        etPassword = (EditText) findViewById(R.id.login__ed_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        savepassword = (CheckBox) findViewById(R.id.login_cb_savepassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onPasswordChanged();
            }
        });
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onUsernameChanged();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.saveisBoolean(getApplicationContext(), SAVE_PASSWORD,savepassword.isChecked());
                login(etUsername.getText().toString(), etPassword.getText().toString());
                SpUtils.saveString(getApplicationContext(), USERID,etUsername.getText().toString());
                if (savepassword.isChecked()){
                    SpUtils.saveString(getApplicationContext(),Fields.PASSWORD,etPassword.getText().toString());
                }else {
                    SpUtils.saveString(getApplicationContext(),Fields.PASSWORD,"");
                }
                pb.setVisibility(View.VISIBLE);
                savepassword.setEnabled(false);
                btnLogin.setEnabled(false);
                etUsername.setEnabled(false);
                etPassword.setEnabled(false);
            }
        });

    }


    void onPasswordChanged() {
        onUsernameChanged();
    }

    void onUsernameChanged() {
        if (etUsername.length() > 0 && etPassword.length() > 0) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void login(final String username, final String password) {
        Map<String, Object> param = CommonValues.getCommonParams(getApplicationContext());
        param.put("userId", username);
        param.put("password", password);
        param.put("tg","oa");
//        param.put("tg","approve");
        String reqLoginGet = CommonValues.REQ_LOGIN_GET;
        if (reqLoginGet.startsWith("http://bpmservice.geely.com")){
            Toast.makeText(getApplicationContext(),"    注意，现在登陆的是生产环境！！",Toast.LENGTH_SHORT).show();
        }
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_LOGIN_GET, param, LoginEntity.class, new HttpManager.ResultCallback<LoginEntity>() {
            @Override
            public void onSuccess(String content, final LoginEntity entity) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        savepassword.setEnabled(true);
                        etUsername.setEnabled(true);
                        etPassword.setEnabled(true);
                        btnLogin.setEnabled(true);
                        if (entity != null && entity.getCode() != null) {
                            if (entity.getCode().equals("100")) {
                                GeelyApp.setLoginEntity(entity);
                                toLibMainActivity(username,password);
                            } else {
                                show(entity.getMsg());
                            }
                        } else {
                            show("请检查网络连接");
                        }
                    }
                });

            }

            @Override
            public void onFailure(final String content) {
                final LoginBean loginEntity = GsonUtil.parseJsonToBean(content, LoginBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginEntity !=null){
                            show(loginEntity.getMsg());
                        }else {
                            show(content);
                        }
                        pb.setVisibility(View.GONE);
                        savepassword.setEnabled(true);
                        btnLogin.setEnabled(true);
                        etUsername.setEnabled(true);
                        etPassword.setEnabled(true);
                    }
                });
            }
        });
    }

    private void toLibMainActivity(String username,String password) {
        LibMainActivity.startActivity(this,username,password);
    }

    private void show(final String asd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(getApplicationContext(),asd);
            }
        });
    }
}
