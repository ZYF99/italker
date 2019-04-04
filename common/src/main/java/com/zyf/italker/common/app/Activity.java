package com.zyf.italker.common.app;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zyf.italker.common.widget.convention.PlaceHolderView;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {

    protected PlaceHolderView mPlaceHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化内部界面（需要权限）
        initWindows();
    }

    //初始化内部内容
    protected void initWindows() {
        if (initArgs(getIntent().getExtras())) {
            //得到界面ID并设置到界面中
            int layId = getContentLayoutId();
            setContentView(layId);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }
    }


    /**
     * 初始化控件调用之前
     */
    protected void initBefore() {

    }

    /*初始化相关参数 参数bundle
     参数正确返回true
     错误返回false*/
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    //得到当前资源文件Id
    protected abstract int getContentLayoutId();

    //初始化控件
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    //初始化数据
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        //得到当前activity下的所有Fragment
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否为空

        if (fragments != null && fragments.size() > 0) {
            for (android.support.v4.app.Fragment fragment : fragments) {
                //判断是否为我们能够处理的Fragment类型
                if (fragment instanceof com.zyf.italker.common.app.Fragment) {
                    //判断是否拦截了返回按钮
                    if (((com.zyf.italker.common.app.Fragment) fragment).onBackPressed()) {
                        //如果有直接Return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，Finish当前界面
        return super.onSupportNavigateUp();
    }


    /**
     * 设置占位布局
     * @param  placeHolderView 实现了占位布局规范的View
     * */
    public void setmPlaceHolderView(PlaceHolderView placeHolderView){
        this.mPlaceHolderView = placeHolderView;
    }

}
