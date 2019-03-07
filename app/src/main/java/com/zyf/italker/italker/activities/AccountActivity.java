package com.zyf.italker.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.zyf.italker.common.app.Activity;
import com.zyf.italker.common.app.Fragment;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.frags.account.UpdateInfoFragment;

public class AccountActivity extends Activity {

    private Fragment mCurFragment;
    /**
    * 账户Activity显示的入口
    *
     * @param context Context
    * */

    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //加载中间是个头像的fragment
        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,mCurFragment)
                .commit();
    }


    //Activity中收到剪切的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode,resultCode,data);
    }
}
