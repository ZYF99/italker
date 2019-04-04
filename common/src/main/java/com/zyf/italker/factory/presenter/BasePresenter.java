package com.zyf.italker.factory.presenter;

public class BasePresenter<T extends BaseContract.View>
        implements BaseContract.Presenter {


    private T mView;

    public BasePresenter(T view) {
        setView(view);

    }

    /**
     * 将View与Presenter绑定
     */
    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }


    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return View
     */
    protected final T getView() {
        return mView;
    }

    /**
     *实现presenter必须写的公共presenter方法
     * */
    @Override
    public void start() {
        //开始的时候进行Loading调用
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void destroy() {
        T view = mView;
        if (view != null) {
            //把presenter设置为null
            view.setPresenter(null);
        }
    }
}
