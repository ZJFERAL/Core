package com.zjf.lib.model;

import io.reactivex.Observable;

/**
 * Created by zhaojifeng on 2017/7/7.
 */

public interface BaseModelImple<T> extends BaseSingleModelImpl<T> {
    Observable<T> getMoreData();
}
