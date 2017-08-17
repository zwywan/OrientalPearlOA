package com.geely.app.geelyapprove.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;


/**
 * Created by Oliver on 2016/10/24.
 */
public class InputActivity extends AppCompatActivity {

    private static Callback mCallback;
    private static InputHolder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_input_textfield);
        initView();
    }

    private void initView(){
        if (holder == null) {
            onBackPressed();
            return;
        }
        View layout = (View) findViewById(R.id.view_outside_input_textfield);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        final EditText etContent = (EditText) findViewById(R.id.tv_value);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);

        btnConfirm.setText(holder.getButtonText() == null ? "" : holder.getButtonText());
        etContent.setHint(holder.getTextFieldHint() == null ? "" : holder.getTextFieldHint());
        if (holder.isValueNotNone()) {
            btnConfirm.setEnabled(false);
            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        btnConfirm.setEnabled(false);
                    } else {
                        btnConfirm.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if (!TextUtils.isEmpty(holder.getTextFieldDefText())) {
            etContent.setText(holder.getTextFieldDefText());
        }
        tvTitle.setText(holder.getTitle() == null ? "" : holder.getTitle());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onResult(etContent.getText().toString());
                onBackPressed();
            }
        });
    }

    public static void showBottomInputTextField(Activity context, InputHolder inputHolder, Callback callback) {
        Intent intent = new Intent(context,InputActivity.class);
        holder = inputHolder;
        mCallback = callback;
        context.startActivity(intent);
    }

    public interface Callback {
        void onResult(String value);
    }



}
