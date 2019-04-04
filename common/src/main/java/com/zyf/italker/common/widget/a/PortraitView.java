package com.zyf.italker.common.widget.a;

import android.content.Context;
import android.util.AttributeSet;
import com.bumptech.glide.RequestManager;
import com.zyf.italker.common.R;
import com.zyf.italker.factory.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 *头像控件
 * */
public class PortraitView extends CircleImageView {

    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager,Author author){
        if(author==null){
            return;
        }
        //进行显示
        setup(manager,author.getPortrait());
    }

    public void setup(RequestManager manager,String url){
       setup(manager, R.drawable.default_portrait,url);
    }

    public void setup(RequestManager manager,int resourceId,String url){
        if(url == null)
        {
            url = "";
        }
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate()//CircleImageView空间中不能使用渐变动画，会导致显示延迟
                .into(this);
    }

}
