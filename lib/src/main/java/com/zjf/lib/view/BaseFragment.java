package com.zjf.lib.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;
    protected Context mContext;
    protected RxPermissions mPermissions;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPermissions = new RxPermissions(getActivity());
        String name = this.getClass().getName();
        TAG = name.substring(name.lastIndexOf("."), name.length());
        mContext = getActivity();

        initVariables();
        View view = initView(inflater, container, savedInstanceState);
        initListener();
        return view;
    }

    public abstract void initVariables();

    public abstract View initView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState);

    public abstract void initListener();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loaderData();
    }

    public abstract void loaderData();

}
