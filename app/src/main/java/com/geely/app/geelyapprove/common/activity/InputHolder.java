package com.geely.app.geelyapprove.common.activity;

/**
 * Created by Oliver on 2016/11/18.
 */

public class InputHolder {
    String title;
    String buttonText;
    String textFieldHint;
    String textFieldDefText;
    boolean valueNotNone = true; //设置如果为空按钮不可用

    public InputHolder(String title, String buttonText, String textFieldHint, String textFieldDefText, boolean valueNotNone) {
        this.title = title;
        this.buttonText = buttonText;
        this.textFieldHint = textFieldHint;
        this.textFieldDefText = textFieldDefText;
        this.valueNotNone = true;
    }

    public String getTitle() {
        return title;
    }

    public InputHolder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getButtonText() {
        return buttonText;
    }

    public InputHolder setButtonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public String getTextFieldHint() {
        return textFieldHint;
    }

    public InputHolder setTextFieldHint(String textFieldHint) {
        this.textFieldHint = textFieldHint;
        return this;
    }

    public String getTextFieldDefText() {
        return textFieldDefText;
    }

    public InputHolder setTextFieldDefText(String textFieldDefText) {
        this.textFieldDefText = textFieldDefText;
        return this;
    }

    public boolean isValueNotNone() {
        return valueNotNone;
    }

    public InputHolder setValueNotNone(boolean valueNotNone) {
        this.valueNotNone = valueNotNone;
        return this;
    }
}
