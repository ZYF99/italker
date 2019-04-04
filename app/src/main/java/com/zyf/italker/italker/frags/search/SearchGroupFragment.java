package com.zyf.italker.italker.frags.search;

import com.zyf.italker.common.app.PresenterFragment;
import com.zyf.italker.factory.presenter.BaseContract;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.activities.SearchActivity;

/**
 * 搜索群的界面实现
 */
public class SearchGroupFragment extends PresenterFragment implements SearchActivity.SearchFragment {


    public SearchGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void search(String content) {

    }
}
