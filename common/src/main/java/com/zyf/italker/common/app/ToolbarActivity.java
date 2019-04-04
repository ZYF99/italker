package com.zyf.italker.common.app;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.zyf.italker.common.R;

public abstract class ToolbarActivity extends Activity {
    protected Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    /**
     * 初始化toolbar
     *
     * @param toolbar
     */
    public void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void initTitleNeedBack() {
        //设置左上角返回按钮为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
