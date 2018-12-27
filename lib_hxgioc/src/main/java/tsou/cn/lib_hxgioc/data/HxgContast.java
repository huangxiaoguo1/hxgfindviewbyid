package tsou.cn.lib_hxgioc.data;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public interface HxgContast {
    //显示默认的未联网提示
    int DEFAULT_TYPE = 1;
    //不显示未联网提示，直接拦截
    int NOHINT_TYPE = -1;
    //去除防止多次点击
    int DEFF_TIME = 0;
}
