package com.szyl.szyllibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.szyl.szyllibrary.R;

public class TsIPEditText extends LinearLayout {

    private EditText firIPEdit;
    private EditText secIPEdit;
    private EditText thirIPEdit;
    private EditText fourIPEdit;

    private String firstIP = "";
    private String secondIP = "";
    private String thirdIP = "";
    private String fourthIP = "";

    private boolean isFourAuto; //最后一个 点击删除是否向前移到第三个位置

    public TsIPEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TsIPEditText);
        isFourAuto = typedArray.getBoolean(R.styleable.TsIPEditText_isFourAuto, true);
        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_ip_text, this);
        firIPEdit = (EditText) findViewById(R.id.Fist_Text);
        secIPEdit = (EditText) findViewById(R.id.Second_Text);
        thirIPEdit = (EditText) findViewById(R.id.Third_Text);
        fourIPEdit = (EditText) findViewById(R.id.Four_Text);

        setIPEditTextListener(context);
    }

    public void setIPEditTextListener(final Context context) {
        //设置第一个IP字段的事件监听
        firIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals(".")) {
                    firstIP = "";
                    return;
                }
                if (s.length() > 2 || s.toString().trim().contains(".")) {
                    if (s.toString().trim().contains(".")) {
                        firstIP = s.toString().trim().substring(0, s.length() - 1);
                    } else {
                        firstIP = s.toString().trim();
                    }
                    if (Integer.parseInt(firstIP) > 255) {
                        firstIP = "255";
                    }
                    secIPEdit.setFocusable(true);
                    secIPEdit.requestFocus();

                } else {
                    firstIP = s.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                firIPEdit.removeTextChangedListener(this);
                firIPEdit.setText(firstIP);
                firIPEdit.setSelection(firIPEdit.length());
                firIPEdit.addTextChangedListener(this);
            }
        });
        //设置第二个IP字段的事件监听
        secIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //若长度为0，返回到上一个文本编辑框
                if (s.toString().length() == 0) {
                    //编辑框获取焦点
                    firIPEdit.setFocusable(true);
                    firIPEdit.requestFocus();
                }
                if (s.toString().trim().equals(".")) {
                    secondIP = "";
                    return;
                }
                if (s.length() > 2 || s.toString().trim().contains(".")) {
                    if (s.toString().trim().contains(".")) {
                        secondIP = s.toString().trim().substring(0, s.length() - 1);
                    } else {
                        secondIP = s.toString().trim();
                    }
                    if (Integer.parseInt(secondIP) > 255) {
                        secondIP = "255";
                    }
                    thirIPEdit.setFocusable(true);
                    thirIPEdit.requestFocus();
                } else {
                    secondIP = s.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                secIPEdit.removeTextChangedListener(this);
                secIPEdit.setText(secondIP);
                secIPEdit.setSelection(secondIP.length());
                secIPEdit.addTextChangedListener(this);
            }
        });

        //设置第三个IP字段的事件监听
        thirIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    secIPEdit.setFocusable(true);
                    secIPEdit.requestFocus();
                }
                if (s.toString().trim().equals(".")) {
                    thirdIP = "";
                    return;
                }
                if (s.length() > 2 || s.toString().trim().contains(".")) {
                    if (s.toString().trim().contains(".")) {
                        thirdIP = s.toString().trim().substring(0, s.length() - 1);
                    } else {
                        thirdIP = s.toString().trim();
                    }
                    if (Integer.parseInt(thirdIP) > 255) {
                        thirdIP = "255";
                    }
                    fourIPEdit.setFocusable(true);
                    fourIPEdit.requestFocus();
                } else {
                    thirdIP = s.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                thirIPEdit.removeTextChangedListener(this);
                thirIPEdit.setText(thirdIP);
                thirIPEdit.setSelection(thirdIP.length());
                thirIPEdit.addTextChangedListener(this);
            }
        });

        //设置第四个IP字段的事件监听
        fourIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFourAuto && s.toString().length() == 0) {
                    thirIPEdit.setFocusable(true);
                    thirIPEdit.requestFocus();
                }
                if (s.toString().trim().equals(".")) {
                    fourthIP = "";
                    return;
                }
                if (s.length() > 2 || s.toString().trim().contains(".")) {
                    if (s.toString().trim().contains(".")) {
                        fourthIP = s.toString().trim().substring(0, s.length() - 1);
                    } else {
                        fourthIP = s.toString().trim();
                    }
                    if (Integer.parseInt(fourthIP) > 255) {
                        fourthIP = "255";
                    }
                } else {
                    fourthIP = s.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                fourIPEdit.removeTextChangedListener(this);
                fourIPEdit.setText(fourthIP);
                fourIPEdit.setSelection(fourthIP.length());
                fourIPEdit.addTextChangedListener(this);
            }
        });
    }

    /**
     * 返回整个ip地址
     *
     * @return
     */
    public String getIpText() {
        if (TextUtils.isEmpty(firstIP) || TextUtils.isEmpty(secondIP)
                || TextUtils.isEmpty(thirdIP) || TextUtils.isEmpty(fourthIP)) {
            return "";
        }
        return firstIP + "." + secondIP + "." + thirdIP + "." + fourthIP;
    }

    /**
     * 本地读取的ip地址显示至界面
     *
     * @param ipText
     */
    public void setIpText(String ipText) {
        if (TextUtils.isEmpty(ipText) || ipText == null) {
            return;
        }
        String[] temp = null;
        temp = ipText.split("\\.");
        if (temp != null) {
            firIPEdit.setText(temp[0]);
            secIPEdit.setText(temp[1]);
            thirIPEdit.setText(temp[2]);
            fourIPEdit.setText(temp[3]);

            fourIPEdit.setFocusable(true);
            fourIPEdit.requestFocus();
        }
    }
}