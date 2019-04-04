package com.zyf.italker.common.app;


import com.zyf.italker.factory.presenter.BaseContract;


public abstract class PresenterToolbarActivity<Presenter extends BaseContract.Presenter>
        extends ToolbarActivity implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    @Override
    protected void initBefore() {
        super.initBefore();
        //初始化Presenter
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭时进行销毁
        if(mPresenter!=null){
            mPresenter.destroy();
        }
    }

    /**
     * 初始化Presenter
     * @return Presenter
     * */
    protected abstract Presenter initPresenter();



    @Override
    public void showError(int str) {
        //显示错误，优先使用占位布局
        if(mPlaceHolderView!=null)
        {
            mPlaceHolderView.triggerError(str);
        }else {
            //显示错误
            Application.showToast(str);
        }

    }
    @Override
    public void showLoading() {
        if(mPlaceHolderView!=null){
            mPlaceHolderView.triggerLoading();
        }
    }

    public void hideLoading() {
        if(mPlaceHolderView!=null){
            mPlaceHolderView.triggerOk();
        }
    }
    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }
}
