package com.yuanshi.maisong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenter;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenterIml;

/**
 * Created by Dengbocheng on 2017/11/8.
 */

public abstract class BaseFragment extends Fragment implements IBaseView{
    protected IHttpPresenter iHttpPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        iHttpPresenter = new IHttpPresenterIml(this,getActivity());
        return getMainView( inflater, container, savedInstanceState);
    }
    protected abstract View getMainView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {

    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {

    }

    @Override
    public void onError(String msgType, String msg, Object obj) {

    }

    @Override
    public void onGetPushMessage(String msg) {

    }
}
