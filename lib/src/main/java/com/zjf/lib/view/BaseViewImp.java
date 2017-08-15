package com.zjf.lib.view;

/**
 * @author :ZJF
 * @version : 2016-12-15 下午 4:55
 */

public interface BaseViewImp{

    void onFailure(String msg, int type);

    void errorLog(String msg);

    void showToast(String msg);

    void showProgressDialog(String msg,boolean cancelable);

    void dismissProgressDialog();

    void showSimpleDialog(String msg);

    void showSnackBar(String msg, int type);
}
