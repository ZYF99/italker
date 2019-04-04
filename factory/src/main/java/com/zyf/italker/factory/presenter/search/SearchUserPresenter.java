package com.zyf.italker.factory.presenter.search;

import com.zyf.italker.common.app.Application;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.data.helper.UserHelper;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;


/**
 * 搜索人的逻辑实现
 */

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter, DataSource.Callback<List<UserCard>> {

    private Call searchCall;

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();
        Call call = searchCall;
        if (call != null && !call.isCanceled()) {
            //如果有上一次的请求并且没有取消则调用取消操作
            call.cancel();
        }
        searchCall = UserHelper.search(content, this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCardList) {
        final SearchContract.UserView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                         view.onSearchDone(userCardList);
                }
            });
        }
    }

    @Override
    public void onDataNotAvaliable(final int strRes) {
        final SearchContract.UserView view = getView();
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
