package tsou.cn.hxgfindviewbyid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tsou.cn.lib_hxgioc.HxgViewUtils;

/**
 * Created by 黄家三少 on 2018/7/26.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = HxgViewUtils.getView().inject(this, inflater, container);
        this.mActivity = getActivity();
        this.mContext = getContext();
        onCreateViewFrame(inflater, container, savedInstanceState);
        return mView;
    }


    protected abstract void onCreateViewFrame(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


}
