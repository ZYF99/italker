package com.zyf.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.factory.R;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.model.api.RspModel;
import com.zyf.italker.factory.model.api.user.UserUpdateModel;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.model.db.User_Table;
import com.zyf.italker.factory.net.Network;
import com.zyf.italker.factory.net.RemoteService;
import com.zyf.italker.utils.CollectionUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHelper {
    //更新用户信息，异步的
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        //得到一个Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        //网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    //唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    //返回成功
                    callback.onDataLoaded(userCard);

                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvaliable(R.string.data_network_error);
            }
        });

    }

    //搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {

        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_network_error);
            }
        });

        //把当前的调度者返回
        return call;
    }

    public static void follow(String id, final DataSource.Callback<UserCard> callback) {
        //搜索的方法
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    //唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    //返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_network_error);
            }
        });


    }


    //刷新联系人的操作，不需要callback,直接存储到数据库
    //并通过观察者进行通知界面更新
    //界面更新的时候进行个对比，然后差异更新
    public static void refreshContacts(DataSource.Callback<List<UserCard>> users) {
        RemoteService service = Network.remote();
        service.userContacts().enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    List<UserCard> cards = rspModel.getResult();
                    if (cards == null || cards.size() == 0)
                        return;
                    //唤起进行保存的操作
                    UserCard[] cards1 = CollectionUtil.toArray(cards, UserCard.class);
                    Factory.getUserCenter().dispatch(cards1);
                } else {
                    Factory.decodeRspCode(rspModel, null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                //nothing
            }
        });

    }


    //从本地查询一个用户的信息
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    //从本网络查询一个用户的信息
    public static User findFromNet(String id) {
        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {

                //TODO 数据库的存储但是没有通知更改
                User user = card.build();
                Factory.getUserCenter().dispatch(card);

                return user;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 搜索一个用户，优先本地缓存
     * 没有再从网络拉取
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }

    /**
     * 搜索一个用户，优先网络查询
     * 没有就从本地缓存拉取
     */
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }
}

