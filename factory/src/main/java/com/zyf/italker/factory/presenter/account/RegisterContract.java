package com.zyf.italker.factory.presenter.account;


import com.zyf.italker.factory.presenter.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter {
        //发起一个注册
        void register(String phone,String password,String name);

        //检查手机号是否正确
        boolean checkMobile(String phone);
    }

}
