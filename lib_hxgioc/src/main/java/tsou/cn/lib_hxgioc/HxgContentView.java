package tsou.cn.lib_hxgioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by huangxiaoguo on 2018/6/20 0020.
 * <p>
 * View 注解的Annotation
 * <p>
 * ElementType.FIELD
 * 代表Annotation的位置，FIELD属性：字段、枚举的常量
 *
 * TYPE:接口、类、枚举、注解
 * <p>
 * RetentionPolicy.CLASS（什么时候生效）
 * 编译时注解： 默认的保留策略，注解会在class字节码文件中存在，
 * 但运行时无法获得
 * RetentionPolicy.RUNTIME：运行时注解
 * 注解会在class字节码文件中存在，在运行时可以通过反射获取到
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxgContentView {
    //@HxgContentView(R.layout.activity_main)
    int value();
}
