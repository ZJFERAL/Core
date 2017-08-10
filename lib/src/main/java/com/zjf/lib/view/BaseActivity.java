package com.zjf.lib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tbruyelle.rxpermissions2.RxPermissions;


public abstract class BaseActivity extends AppCompatActivity {


    protected RxPermissions mPermissions;
    protected Bundle mBundle;
    protected String TAG;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBundle = savedInstanceState;
        mPermissions = new RxPermissions(this);
        String name = this.getClass().getName();
        TAG = name.substring(name.lastIndexOf("."), name.length());
        mContext = this;
        initVariables();
        initView();
        setListener();
    }


    /**
     * 做初始化方面的工作,比如接收上一个界面的Intent
     */
    public abstract void initVariables();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化监听
     */
    public abstract void setListener();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
