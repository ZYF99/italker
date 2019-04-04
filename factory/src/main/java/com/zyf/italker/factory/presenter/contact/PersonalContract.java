package com.zyf.italker.factory.presenter.contact;

import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.presenter.BaseContract;

public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        //获取用户信息
        User getUserPersonal();
        //关注用户
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        //数据加载完成
        void onLoadDone(User user);

        //是否发起聊天
        void allowSayHello(boolean isAllow);

        //设置关注状态
        void setFollowStatus(boolean isFollow);


    }
}
