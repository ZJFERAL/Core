package com.zjf.lib.model;

import io.reactivex.Observable;

/**
 * Created by jifengZhao on 2017/5/3.
 */

public interface BaseSingleModelImpl<T> {

    Observable<T> getData();

    void onDestroy();
}
