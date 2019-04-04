package com.zyf.italker.italker.activities;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

import com.zyf.italker.factory.Author;
import com.zyf.italker.italker.R;

public class MessageActivity extends Activity {

    /**
    * 显示人的聊天界面
    * */
    public static void show(Context context, Author author){
        //context.startActivity(context,MessageActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

}
