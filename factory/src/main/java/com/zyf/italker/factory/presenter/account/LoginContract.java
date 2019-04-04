package com.zyf.italker.factory.presenter.account;


import com.zyf.italker.factory.presenter.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        //登陆成功
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter {
        //发起一个登陆
        void login(String phone,String password);

    }

}
