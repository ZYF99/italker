package com.zyf.italker.factory.presenter.search;

import com.zyf.italker.factory.presenter.BasePresenter;


/**
 * 搜多群的逻辑实现
 */
public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
        implements SearchContract.Presenter {


    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
