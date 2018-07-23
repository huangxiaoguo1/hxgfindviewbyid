package tsou.cn.lib_hxgioc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public interface HxgViewInjector {
    /**
     * 注入view
     *
     * @param view
     */
    void inject(View view);

    /**
     * 注入activity
     *
     * @param activity
     */
    void inject(Activity activity);

    /**
     * 注入view holder
     *
     * @param object view holder
     * @param view
     */
    void inject(View view,Object object);

    /**
     * 注入fragment
     *
     * @param fragment
     * @param inflater
     * @param container
     * @return
     */
    View inject(Object fragment, LayoutInflater inflater, ViewGroup container);
}
