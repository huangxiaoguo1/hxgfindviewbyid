package tsou.cn.hxgfindviewbyid;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import tsou.cn.lib_hxgioc.HxgBind;
import tsou.cn.lib_hxgioc.HxgCheckNet;
import tsou.cn.lib_hxgioc.HxgClickMore;
import tsou.cn.lib_hxgioc.HxgContentView;
import tsou.cn.lib_hxgioc.HxgOnClick;
import tsou.cn.lib_hxgioc.HxgViewUtils;

@HxgContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    /**
     * Hello World!
     **/
    @HxgBind(R.id.text_iv)
    private TextView mTextIv;

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
    @HxgClickMore(5000)//防止多次点击间隔时间（不设置默认时间间隔为1500；设置为0，或者HxgContast.DEFF_TIME,表示不阻值可以多次连续点击）
    private void onClick(Button view) {
        Toast.makeText(this, "点击了Button", Toast.LENGTH_LONG).show();
    }

}
