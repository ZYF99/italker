package com.zyf.italker.factory.presenter.contact;

import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.presenter.BaseContract;


/**
* 关注的接口定义
* */
public interface FollowContract {
    //任务调度者
    interface Presenter extends BaseContract.Presenter {
        //关注一个人
        void follow(String id);

    }
    interface View extends BaseContract.View<Presenter>{
        //关注成功的情况下返回一个用户信息回来
        void onFollowSucceed(UserCard userCard);
    }
}
