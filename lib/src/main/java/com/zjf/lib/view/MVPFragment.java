package com.zjf.lib.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;

import com.zjf.lib.R;
import com.zjf.lib.presenter.BasePresenter;
import com.zjf.lib.presenter.PresenterFactory;
import com.zjf.lib.presenter.PresenterLoader;
import com.zjf.lib.utils.LogUtils;
import com.zjf.lib.utils.SnackBarUtils;

/**
 * @author :ZJF
 * @version : 2016-12-19 下午 5:40
 */

public abstract class MVPFragment<T extends BasePresenter> extends BaseFragment implements BaseViewImp, PresenterFactory<T>, LoaderManager.LoaderCallbacks<T> {

    protected T mPresenter;
    protected View mLayoutView;
    private AlertDialog mDialog;

    public MVPFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onViewAttached();
        }
    }

    @Override
    public void loaderData() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<T> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), this);
    }

    @Override
    public void onLoadFinished(Loader<T> loader, T data) {
        this.mPresenter = data;
        if (mPresenter != null) {
            mPresenter.onCreate(this);
        }
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        if (mPresenter != null) {
            this.mPresenter.onDestroyed();
            this.mPresenter = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onViewDetached();
        }
    }


    @Override
    public void errorLog(String msg) {
        LogUtils.e(TAG, msg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog(String msg,boolean cancelable) {
        if (mDialog == null) {
            mDialog = new ProgressDialog.Builder(mContext)
                    .create();
        }
        mDialog.setCancelable(cancelable);
        mDialog.setMessage(msg);
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog(){
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void showSimpleDialog(String msg) {
        new AlertDialog.Builder(mContext)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.sure), null)
                .show();
    }

    @Override
    public void showSnackBar(String msg, int type) {
        if (mLayoutView != null) {
            SnackBarUtils.ShortSnackbar(mLayoutView, msg, type).show();
        } else {
            mLayoutView = getLayoutView();
        }
    }

    protected abstract View getLayoutView();
}
