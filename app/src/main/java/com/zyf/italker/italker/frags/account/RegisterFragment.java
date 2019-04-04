package com.zyf.italker.italker.frags.account;

import android.content.Context;
import android.widget.EditText;
import com.zyf.italker.common.app.PresenterFragment;
import com.zyf.italker.factory.presenter.account.RegisterContract;
import com.zyf.italker.factory.presenter.account.RegisterPresenter;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.activities.MainActivity;
import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册的界面
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);//父类中包含调用initPresenter的方法
        //拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        //调用p层进行注册
        mPresenter.register(phone,password,name);
    }

    @OnClick(R.id.txt_go_login)
    void onShowLoginClick(){
        //让AccountActivity进行界面切换
        mAccountTrigger.triggerView();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //正在进行时，正在进行注册，界面不可操作
        //开始Loading
        mLoading.start();
        //让控件不可以输入
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);
        //让按钮不可以点击
        mSubmit.setEnabled(false);

    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //当提示需要显示错误的时候触发，一定是结束了
        //停止Loading
        mLoading.stop();
        //让控件可以输入
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        //让按钮可以点击
        mSubmit.setEnabled(true);

    }

    @Override
    public void registerSuccess() {
        //注册成功，这个时候账户已经登陆
        //我们需要进行跳转到MainActivity界面
        MainActivity.show(getContext());
        //关闭当前界面
        getActivity().finish();
    }

}
