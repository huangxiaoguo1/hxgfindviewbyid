package tsou.cn.lib_hxgioc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

import tsou.cn.lib_hxgioc.data.HxgContast;
import tsou.cn.lib_hxgioc.utils.ClickMoreUtils;
import tsou.cn.lib_hxgioc.utils.NetUtils;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

class HxgViewInjectorImpl implements HxgViewInjector {
    private static final HashSet<Class<?>> IGNORED = new HashSet<Class<?>>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    private static HxgViewInjectorImpl instance;

    private HxgViewInjectorImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (HxgViewInjectorImpl.class) {
                if (instance == null) {
                    instance = new HxgViewInjectorImpl();
                }
            }
        }
        HxgViewUtils.hxgExt.setViewInjector(instance);
    }

    /**
     * 注入view
     *
     * @param view
     */
    @Override
    public void inject(View view) {
        inject(new HxgViewHelper(view), view);
    }

    /**
     * 注入activity
     *
     * @param activity
     */
    @Override
    public void inject(Activity activity) {
        inject(new HxgViewHelper(activity), activity);
    }

    /**
     * 注入view holder
     *
     * @param object view holder
     * @param view
     */
    @Override
    public void inject(View view, Object object) {
        injectObject(new HxgViewHelper(view), object);
    }

    /**
     * 注入fragment
     *
     * @param fragment
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        View view = null;
        Class<?> handlerType = fragment.getClass();
        try {
            HxgContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    view = inflater.inflate(viewId, container, false);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        injectObject(new HxgViewHelper(view), fragment);
        return view;
    }

    /**
     * 兼容上面三个方法
     * (注入view,注入activity,注入view holder)
     *
     * @param finder
     * @param object 反射需要执行的类
     */
    private static void inject(HxgViewHelper finder, Object object) {
        inContentView(finder, object);
    }


    /**
     * 注入ContentView
     *
     * @param finder
     * @param object
     */
    private static void inContentView(HxgViewHelper finder, Object object) {
        Class<?> handlerType = object.getClass();
        try {
            HxgContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);
                    setContentViewMethod.invoke(object, viewId);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        injectObject(finder, object);
    }


    /**
     * 从父类获取注解View
     */
    private static HxgContentView findContentView(Class<?> thisCls) {
        if (thisCls == null || IGNORED.contains(thisCls)) {
            return null;
        }
        HxgContentView contentView = thisCls.getAnnotation(HxgContentView.class);
        if (contentView == null) {
            return findContentView(thisCls.getSuperclass());
        }
        return contentView;
    }

    private static void injectObject(HxgViewHelper finder, Object object) {
        if (object == null || IGNORED.contains(object)) {
            return;
        }
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(HxgViewHelper finder, Object object) {
        //1、获取类里面所有的属性
        Class<?> aClass = object.getClass();
        //  获取所有的属性包括私有和公有
        Field[] fields = aClass.getDeclaredFields();
        //2、获取viewById里面的value值
        for (Field field : fields) {
            HxgBind viewById = field.getAnnotation(HxgBind.class);
            if (null != viewById) {
                //获取注解里面的id值—>R.id.xxx
                int viewId = viewById.value();
                //3、findViewById找到View
                View view = finder.findViewById(viewId);
                if (null != view) {
                    //能够注入所有修饰符：private，public
                    field.setAccessible(true);
                    //4、动态的注入找到View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 事件注入
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(HxgViewHelper finder, Object object) {
        //1、获取类里面所有的方法
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        //2、获取OnClick里面的value
        for (Method method : methods) {
            HxgOnClick onClick = method.getAnnotation(HxgOnClick.class);
            if (null != onClick) {
                int[] viewIds = onClick.value();
                //能够注入所有方法：private，public
                method.setAccessible(true);
                for (int viewId : viewIds) {
                    //3、findViewById找到View
                    View view = finder.findViewById(viewId);

                    /**
                     * 扩展，检查网络
                     */
                    HxgCheckNet checkNet = method.getAnnotation(HxgCheckNet.class);

                    int stringName = HxgContast.DEFAULT_TYPE;
                    if (null != checkNet) {
                        stringName = checkNet.value();
                    }
                    boolean isCheckNet = checkNet != null;

                    /**
                     * 扩展，防止多次点击
                     */
                    HxgClickMore clickMore = method.getAnnotation(HxgClickMore.class);
                    int deffTime = 1500;
                    if (null != clickMore) {
                        deffTime = clickMore.value();
                    }
                    if (null != view) {
                        //4、setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListner(method, object,
                                isCheckNet, stringName, deffTime));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListner implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;
        private int mStringName;
        private int mDeffTime;

        public DeclaredOnClickListner(Method method, Object object, boolean isCheckNet, int stringName, int deffTime) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
            this.mStringName = stringName;
            this.mDeffTime = deffTime;
        }

        @Override
        public void onClick(View view) {
            //检查网络
            if (mIsCheckNet) {
                if (!NetUtils.isNetworkConnected(view.getContext())) {
                    //不显示未联网提示，直接拦截
                    if (mStringName == HxgContast.NOHINT_TYPE) {
                        return;
                    }
                    if (mStringName != HxgContast.DEFAULT_TYPE) {
                        try {
                            String context = view.getContext().getString(mStringName);
                            Toast.makeText(view.getContext(), context, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                        }
                    } else {
                        String context = view.getContext().getString(R.string.check_default_string);
                        Toast.makeText(view.getContext(), context, Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
            if (mDeffTime != HxgContast.DEFF_TIME) {
                if (mDeffTime > 0) {
                    if (ClickMoreUtils.isFastDoubleClick(view.getId(), mDeffTime)) {
                        return;
                    }
                }else {
                    if (ClickMoreUtils.isFastDoubleClick(view.getId())) {
                        return;
                    }
                }
            }
            //5、反射注入方法
            try {
                mMethod.invoke(mObject, view);
            } catch (Exception e) {
                e.printStackTrace();
                //mMethod.invoke(mObject, null);
            }
        }
    }
}
