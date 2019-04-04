package com.zyf.italker.italker.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.zyf.italker.common.app.Activity;
import com.zyf.italker.common.widget.a.PortraitView;
import com.zyf.italker.factory.persistence.Account;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.frags.main.ActiveFragment;
import com.zyf.italker.italker.frags.main.ContactFragment;
import com.zyf.italker.italker.frags.main.GroupFragment;
import com.zyf.italker.italker.frags.user.UpdateInfoFragment;
import com.zyf.italker.italker.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnTabChangedListener<Integer> {
    private NavHelper<Integer> mNavHelper;
    @BindView(com.zyf.italker.italker.R.id.appbar)
    View mLayAppBar;

    @BindView(com.zyf.italker.italker.R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(com.zyf.italker.italker.R.id.txt_title)
    TextView mTitle;

    @BindView(com.zyf.italker.italker.R.id.lay_container)
    FrameLayout mContainer;

    @BindView(com.zyf.italker.italker.R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(com.zyf.italker.italker.R.id.btn_action)
    FloatActionButton mAction;

    public static void show(Context context) {
        //MainActivity显示的入口
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            //判断用户信息是否完全，完全则走正常流程
            return super.initArgs(bundle);
        } else {
            UserActivity.show(this);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return com.zyf.italker.italker.R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化底部辅助工具类
        mNavHelper = new NavHelper<>(this, com.zyf.italker.italker.R.id.lay_container
                , getSupportFragmentManager(), this);
        mNavHelper.add(com.zyf.italker.italker.R.id.action_home, new NavHelper.Tab<Integer>(ActiveFragment.class, com.zyf.italker.italker.R.string.title_home))
                .add(com.zyf.italker.italker.R.id.action_group, new NavHelper.Tab<Integer>(GroupFragment.class, com.zyf.italker.italker.R.string.title_group))
                .add(com.zyf.italker.italker.R.id.action_contact, new NavHelper.Tab<Integer>(ContactFragment.class, com.zyf.italker.italker.R.string.title_contact));


        //添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this).load(R.drawable.default_banner_chat).
                centerCrop().into(new ViewTarget<View, GlideDrawable>(mLayAppBar) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
        //从底部导航中接管Menu，然后手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(com.zyf.italker.italker.R.id.action_home, 0);

        //初始化头像加载
        mPortrait.setup(Glide.with(this), Account.getUser());
    }


    /**
     * 打开我的个人信息界面
     */
    @SuppressLint("CommitTransaction")
    @OnClick(com.zyf.italker.italker.R.id.im_portrait)
    void onPortraitClick() {
        PersonalActivity.show(this,Account.getUserId());
    }

    @OnClick(com.zyf.italker.italker.R.id.im_search)
    void onSearchMenuClick() {
        //在群的界面的时候，点击顶部的搜索就进入群搜索的界面
        //其他都为人搜索的界面
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group) ?
                SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;
        SearchActivity.show(this, type);
    }

    @OnClick(com.zyf.italker.italker.R.id.btn_action)
    void onActionClick() {
        //浮动按钮判断当前界面是群还是联系人界面
        //如果是群则打开群创建的界面
        //如果是其他，则打开添加用户的界面
        if (Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group)) {
            //TODO 打开群创建界面
            SearchActivity.show(this, SearchActivity.TYPE_GROUP);
        } else {
            //TODO 如果是其他，都打开添加用户的界面
            SearchActivity.show(this, SearchActivity.TYPE_USER);
        }


    }

    /*
     * 当底部导航被点击的时候触发
     * 返回true代表我们能处理这个点击
     * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //转接事件流到工具类中
        return mNavHelper.performClickMenu(menuItem.getItemId());
    }

    /**
     * NavHelper处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        //从额外字段中取出Title资源id
        mTitle.setText(newTab.extra);

        //对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (newTab.extra.equals(com.zyf.italker.italker.R.string.title_home)) {
            //主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            //transY 默认为0 则显示
            if (newTab.extra.equals(com.zyf.italker.italker.R.string.title_group)) {
                //群
                mAction.setImageResource(com.zyf.italker.italker.R.drawable.ic_group_add);
                rotation = -360;
            } else {
                //联系人
                mAction.setImageResource(com.zyf.italker.italker.R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        //开始动画
        //旋转，Y轴位移，弹性插值器
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();
    }


}
