package com.zjf.lib.presenter;


import com.zjf.lib.model.BaseSingleModelImpl;

import io.reactivex.disposables.CompositeDisposable;

public abstract class Presenter<V, M extends BaseSingleModelImpl> implements BasePresenter<V> {

    protected V mView;
    protected boolean isAttached = false;
    protected M mModel;
    protected CompositeDisposable mDisposable;


    @Override
    public void onCreate(V view) {
        this.mView = view;
        this.mModel = onCreateModel();
        mDisposable = new CompositeDisposable();
        onViewCreated();
    }

    protected abstract M onCreateModel();


    protected abstract void onViewCreated();


    @Override
    public void onViewAttached() {
        isAttached = true;
    }

    @Override
    public void onViewDetached() {
        isAttached = false;
        mDisposable.dispose();
        mDisposable.clear();
    }

    @Override
    public void onDestroyed() {
        mView = null;
        if (mModel != null) {
            mModel.onDestroy();
            mModel = null;
        }
    }


}
