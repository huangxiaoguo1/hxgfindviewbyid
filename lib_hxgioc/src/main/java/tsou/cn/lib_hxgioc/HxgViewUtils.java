package tsou.cn.lib_hxgioc;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class HxgViewUtils {
    private HxgViewUtils() {
    }

    public static HxgViewInjector getView() {
        if (hxgExt.viewInjector == null) {
            HxgViewInjectorImpl.registerInstance();
        }
        return hxgExt.viewInjector;
    }
     static class hxgExt{
         private static HxgViewInjector viewInjector;
        private hxgExt() {
        }
        public static void setViewInjector(HxgViewInjector viewInjector) {
            hxgExt.viewInjector = viewInjector;
        }

    }
}
