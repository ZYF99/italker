package com.zyf.italker.common.app;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zyf.italker.common.app.utils.PermissionUtils;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("permission",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //查看是否获得过权限
        if(sharedPreferences.getBoolean("file",false)){
            //有权限则直接初始化界面
            initWindows();
        }else {
            //无权限则申请权限
            PermissionUtils.verifyStoragePermissions(this);
        }




    }

    //初始化内部内容
    protected void initWindows() {
        if (initArgs(getIntent().getExtras())) {
            //得到界面ID并设置到界面中
            int layId = getContentLayoutId();
            setContentView(layId);
            initWidget();
            initData();
        } else {
            finish();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {                                          //这个0是requestCode，上面requestPermissions有用到
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "正在进入", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("file",true);
                    editor.commit();
                    //初始化内部界面（需要权限）
                    initWindows();
                } else {
                    Toast.makeText(this, "您拒绝了写文件权限，无法进去APP", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }

}
