package tsou.cn.hxgfindviewbyid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tsou.cn.lib_hxgioc.HxgBind;
import tsou.cn.lib_hxgioc.HxgCheckNet;
import tsou.cn.lib_hxgioc.HxgContentView;
import tsou.cn.lib_hxgioc.HxgOnClick;
import tsou.cn.lib_hxgioc.data.HxgContast;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
@HxgContentView(R.layout.layout_fragent)
public class MyFragment extends BaseFragment {

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

    @Override
    protected void onCreateViewFrame(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBtnFrist.setText("button");
        mTvFrist.setText("textView");
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
