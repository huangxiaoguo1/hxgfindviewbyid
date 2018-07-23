package tsou.cn.lib_hxgioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2018/6/20 0020.
 * <p>
 * FindViewById的辅助类
 */

 class HxgViewHelper {
    private Activity mActivity;
    private View mView;

    public HxgViewHelper(Activity activity) {
        this.mActivity = activity;
    }

    public HxgViewHelper(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
