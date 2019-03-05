package com.zyf.italker.italker;

import android.widget.TextView;
import com.zyf.italker.common.app.Activity;
import butterknife.BindView;

public class MainActivity extends Activity {

    @BindView(R2.id.txt_test) TextView mTestView;

    @Override
    protected int getContentLayoutId() {
        return R2.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestView.setText("Test Hello.");
    }
}
