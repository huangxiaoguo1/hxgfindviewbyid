# hxgfindviewbyid

### 引入方式

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.huangxiaoguo1:hxgfindviewbyid:1.0.1'
}
```

### Activity使用简例

```

@HxgContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    /**
     * Hello World!
     **/
    @HxgBind(R.id.text_iv)
    private TextView mTextIv;
    /**
     * 点击
     **/
    @HxgBind(R.id.btn)
    private Button mBtn;
    @HxgBind(R.id.fl_main)
    private FrameLayout mFlMain;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HxgViewUtils.getView().inject(this);
        mTextIv.setText("haungxiaoguo");
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, new MyFragment())
                .commitAllowingStateLoss();
    }

    @HxgOnClick(R.id.text_iv)
    @HxgCheckNet(R.string.net_net_string)
    private void onClick(TextView view) {
        Toast.makeText(this, "点击了TextView", Toast.LENGTH_LONG).show();
    }

    @HxgOnClick({R.id.btn})
    @HxgCheckNet()
    private void onClick(Button view) {
        Toast.makeText(this, "点击了Button", Toast.LENGTH_LONG).show();
    }
}


```

### Fragment 使用简例

```
@HxgContentView(R.layout.layout_fragent)
public class MyFragment extends Fragment {

    /**
     * 按钮
     **/
    @HxgBind(R.id.btn_frist)
    private Button mBtnFrist;
    /**
     * text
     **/
    @HxgBind(R.id.tv_frist)
    private TextView mTvFrist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mView = HxgViewUtils.getView().inject(this, inflater, container);
        mBtnFrist.setText("button");
        mTvFrist.setText("textView");
        return mView;
    }

    @HxgOnClick(R.id.btn_frist)
    @HxgCheckNet(HxgContast.DEFAULT_TYPE)
    private void onClick(Button button) {
        Toast.makeText(getContext(), "点击了button", Toast.LENGTH_LONG).show();
    }

    @HxgOnClick(R.id.tv_frist)
    @HxgCheckNet(HxgContast.NOHINT_TYPE)
    private void onClick(TextView textView) {
        Toast.makeText(getContext(), "点击了textView", Toast.LENGTH_LONG).show();
    }

}

```

###使用简介

###### 设置布局

```
@HxgContentView(R.layout.activity_main)

@HxgContentView(R.layout.layout_fragent)

在Activity与Fragment中相同

```

###### 添加FindViewById

```
@HxgBind(R.id.text_iv)

```

###### 添加点击事件

```
@HxgOnClick(R.id.btn_frist)
```

### 检查网络

###### 带入提示语的注解

```
@HxgCheckNet(R.string.net_net_string)
```

###### 无任何提示语

```
@HxgCheckNet()

```
或
```
@HxgCheckNet(HxgContast.NOHINT_TYPE)
```
###### 使用默认的提示语

```
@HxgCheckNet(HxgContast.DEFAULT_TYPE)

```

### Activity中注入

```
  HxgViewUtils.getView().inject(this);
```

### Fragment中注入

```
View mView = HxgViewUtils.getView().inject(this, inflater, container);
```
### 防暴力点击

```
默认不做任何设置，带有的防暴力点击事件间隔是1500

不设置默认时间间隔为1500；设置为0，或者HxgContast.DEFF_TIME,表示不阻值可以多次连续点击

 @HxgClickMore(5000) :表示暴力点击事件间隔是5000，可以自行设置
 
 @HxgClickMore(0)或@HxgClickMore(HxgContast.DEFF_TIME)：表示去除防暴力点击设置

```
### 配合androidStudio使用

```
插件地址：

https://github.com/huangxiaoguo1/hxgfindviewbyid/tree/master/app/src/main/assets

```
