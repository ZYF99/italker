package com.zyf.italker.factory.presenter.contact;

import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.data.helper.UserHelper;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;


/**
 * 关注的逻辑实现
 */
public class FollowPresenter extends BasePresenter<FollowContract.View>implements FollowContract.Presenter, DataSource.Callback<UserCard> {
    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        start();

        UserHelper.follow(id, this);

    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        //成功
        final FollowContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onFollowSucceed(userCard);
                }
            });
        }
    }

    @Override
    public void onDataNotAvaliable(final int strRes) {
        final FollowContract.View view = getView();
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
