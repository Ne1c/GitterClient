package com.ne1c.developerstalk.ui.views;

import android.support.annotation.StringRes;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void successAuth();

    void errorAuth(@StringRes int resId);
}
