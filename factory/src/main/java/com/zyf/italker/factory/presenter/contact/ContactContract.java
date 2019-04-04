package com.zyf.italker.factory.presenter.contact;

import com.zyf.italker.common.widget.recycler.RecyclerAdapter;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.presenter.BaseContract;

public interface ContactContract {
    //什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    //都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter,User> {



    }
}
