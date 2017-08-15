package com.zjf.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjf.lib.R;

/**
 * Created by zjf on 2017/7/10.
 */

public class TipView extends FrameLayout {

    private ShowType mType = ShowType.LOADING;
    private View mCurrentView;
    private int mResId;
    private String mMsg;

    public TipView(@NonNull Context context) {
        this(context, null);
    }

    public TipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        init();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TipView);
        mType = ShowType.getName(typedArray.getInt(R.styleable.TipView_zjf_type, 0));
        mResId = typedArray.getResourceId(R.styleable.TipView_zjf_tip_img, R.drawable.ic_adb_yellow_600_24dp);
        mMsg = typedArray.getString(R.styleable.TipView_zjf_tip_msg);
        if (TextUtils.isEmpty(mMsg)) {
            mMsg = getResources().getString(R.string.loading);
        }
        int resourceId = typedArray.getResourceId(R.styleable.TipView_zjf_custom_view, R.layout.loading_view_layout);
        if (resourceId != R.layout.loading_view_layout) {
            View view = LayoutInflater.from(getContext()).inflate(resourceId, this, false);
            showCustomView(view);
        }
        typedArray.recycle();

    }

    private void init() {
        if (mType == ShowType.LOADING) {
            showLoading();
        } else if (mType == ShowType.EMPTY_DATA) {
            showEmpty();
        } else if (mType == ShowType.COMMON_ERROR) {
            showError();
        } else if (mType == ShowType.NET_ERROR) {
            showNetError();
        }
        addView(mCurrentView);
    }


    public void setType(ShowType type) {
        mType = type;
        init();
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public void showCustomView(View currentView) {
        removeCurrentView();
        mCurrentView = currentView;
    }

    private void showLoading() {
        removeCurrentView();
        mCurrentView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view_layout, this, false);
        ((TextView) mCurrentView.findViewById(R.id.text_loading_tip)).setText(mMsg);

    }


    private void showEmpty() {
        removeCurrentView();
        mCurrentView = LayoutInflater.from(getContext()).inflate(R.layout.empty_data_layout, this, false);
        ((TextView) mCurrentView.findViewById(R.id.text_empty_tip)).setText(mMsg);
        ((ImageView) mCurrentView.findViewById(R.id.img_empty_tip)).setImageResource(mResId);
    }


    private void showError() {
        removeCurrentView();
        mCurrentView = LayoutInflater.from(getContext()).inflate(R.layout.error_view_layout, this, false);
        ((TextView) mCurrentView.findViewById(R.id.text_error_tip)).setText(mMsg);
        ((ImageView) mCurrentView.findViewById(R.id.img_error_tip)).setImageResource(mResId);
    }


    private void showNetError() {
        removeCurrentView();
        mCurrentView = LayoutInflater.from(getContext()).inflate(R.layout.net_error_view_layout, this, false);
        ((TextView) mCurrentView.findViewById(R.id.text_net_error_tip)).setText(mMsg);
        ((ImageView) mCurrentView.findViewById(R.id.img_net_error_tip)).setImageResource(mResId);
    }


    private void removeCurrentView() {
        if (mCurrentView != null) {
            removeView(mCurrentView);
            mCurrentView = null;
        }
    }


    public enum ShowType {
        LOADING(0), EMPTY_DATA(1), COMMON_ERROR(2), NET_ERROR(3), CUSTOM(4);

        ShowType(int value) {
        }

        public static ShowType getName(int value) {
            switch (value) {
                case 0:
                    return ShowType.LOADING;
                case 1:
                    return ShowType.EMPTY_DATA;
                case 2:
                    return ShowType.COMMON_ERROR;
                case 3:
                    return ShowType.NET_ERROR;
                case 4:
                    return ShowType.CUSTOM;
                default:
                    return ShowType.LOADING;
            }
        }
    }

}
