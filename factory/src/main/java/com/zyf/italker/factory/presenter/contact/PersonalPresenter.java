package com.zyf.italker.factory.presenter.contact;

import com.zyf.italker.common.app.Application;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.data.helper.UserHelper;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.persistence.Account;
import com.zyf.italker.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter, DataSource.Callback<UserCard> {
    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        Application.showToast("运行了follow");
        start();

        UserHelper.follow(id, this);

    }

    @Override
    public void start() {
        super.start();


        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if (view != null) {
                    String id = getView().getUserId();
                    //个人界面用户数据优先从网络拉起
                    User user = UserHelper.searchFirstOfNet(id);
                    onLoaded(view, user);
                }

            }
        });


    }

    private void onLoaded(final PersonalContract.View view, final User user) {
        this.user = user;
        //是否是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        //是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        //已经关注同时不是自己才能开始聊天
        final boolean allowSayHello = isFollow && !isSelf;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }
    @Override
    public User getUserPersonal() {
        return user;
    }


    /**
     * 关注成功后的回调
     */
    @Override
    public void onDataLoaded(final UserCard userCard) {
        //成功
        final PersonalContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.setFollowStatus(userCard.isFollow());
                }
            });
        }
    }

    /**
     * 关注失败后的回调
     */
    @Override
    public void onDataNotAvaliable(final int strRes) {
        final PersonalContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }
}
