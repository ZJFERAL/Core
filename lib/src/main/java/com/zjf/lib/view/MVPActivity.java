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


public abstract class MVPActivity<T extends BasePresenter> extends BaseActivity implements BaseViewImp,PresenterFactory<T>,LoaderManager.LoaderCallbacks<T> {

    protected T mPresenter;
    protected View mLayoutView;

    @Override
    public void initVariables() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<T> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, this);
    }


    @Override
    public void onLoadFinished(Loader<T> loader, T data) {
        this.mPresenter = data;
        mPresenter.onCreate(this);
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        this.mPresenter.onDestroyed();
        this.mPresenter = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onViewAttached();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onViewDetached();
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
    public void showProgressDialog(String msg) {
        new ProgressDialog.Builder(mContext)
                .setMessage(msg)
                .show();
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
        SnackBarUtils.ShortSnackbar(getLayoutView(),msg,type).show();
    }

    protected abstract View getLayoutView();
}
