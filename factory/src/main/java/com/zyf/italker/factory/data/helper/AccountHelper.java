package com.zyf.italker.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.factory.R;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.model.api.RspModel;
import com.zyf.italker.factory.model.api.account.AccountRspModel;
import com.zyf.italker.factory.model.api.account.LoginModel;
import com.zyf.italker.factory.model.api.account.RegisterModel;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.net.Network;
import com.zyf.italker.factory.net.RemoteService;
import com.zyf.italker.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {


    /**
     * 注册的接口
     *
     * @param model    传递一个注册model的接口
     * @param callback 成功与失败的接口回送
     */
    public static void register(final RegisterModel model, final DataSource.Callback<User> callback) {
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        //异步的请求
        call.enqueue(new AccountRspCallback(callback));

    }

    /**
     * 登陆的接口
     *
     * @param model    传递一个登陆model的接口
     * @param callback 成功与失败的接口回送
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        //异步的请求
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {

        //检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) {
            return;
        }
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));

    }


    /**
     * 请求的回调部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call
                , Response<RspModel<AccountRspModel>> response) {
            //请求成功返回
            //从返回中得到我们的全局Model，内部是使用的Gson进行解析

            RspModel<AccountRspModel> rspModel = response.body();
            Log.d("RSP", rspModel.toString());
            if (rspModel.success()) {
                //拿到实体
                AccountRspModel accountRspModel = rspModel.getResult();
                //获取我的信息
                final User user = accountRspModel.getUser();
                DbHelper.save(User.class, user);

                //第一种，直接保存
                //user.save();
                //第二种，本质与第一种一样，但是这种可以存储集合
/*                FlowManager.getModelAdapter(User.class)
                        //.save() 本质与第一种一样，但是这种可以存储集合
                        .save(user);*/
                        /*
                        //第三种，事物中
                        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                        definition.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                FlowManager.getModelAdapter(User.class)
                                        //.save()
                                        .save(user);
                            }
                        }).build().execute();
                        */
                //同步到XML持久化当中
                Account.login(accountRspModel);

                //判断绑定状态，是否绑定设备
                if (accountRspModel.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    //然后返回
                    if (callback != null) {
                        callback.onDataLoaded(user);
                    }
                } else {
                    //进行绑定的唤起
                    bindPush(callback);
                }
            } else {
                //错误解析
                Factory.decodeRspCode(rspModel, callback);
            }

        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            //网络请求失败
            if (callback != null) {
                callback.onDataNotAvaliable(R.string.data_network_error);
            }

        }
    }

}
