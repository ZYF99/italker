package com.zyf.italker.factory.presenter.user;

import android.text.TextUtils;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.factory.R;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.data.helper.UserHelper;
import com.zyf.italker.factory.model.api.user.UserUpdateModel;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.net.UploadHelper;
import com.zyf.italker.factory.presenter.BasePresenter;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter,DataSource.Callback<UserCard> {
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath,final String desc,final boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();
        //校验
        if(TextUtils.isEmpty(photoFilePath)||TextUtils.isEmpty(desc)){
            view.showError(R.string.data_account_update_invalid_parameter);
        }else {
            //上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if(TextUtils.isEmpty(photoFilePath)){
                        //上传失败
                        view.showError(R.string.data_upload_error);
                    }else {
                        //构建Model
                        UserUpdateModel model = new UserUpdateModel("",url,desc
                                ,isMan? User.SEX_MAN:User.SEX_WOMAN);
                        //进行网络请求上传
                        UserHelper.update(model,UpdateInfoPresenter.this);
                    }

                }
            });
        }
    }

    @Override
    public void onDataLoaded(UserCard userCard) {

        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;

        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面的注册成功
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvaliable(final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        //此时是从玩过回送回来的，并不保证属于主线程状态
        //强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面的注册成功
                view.showError(strRes);
            }
        });
    }
}
