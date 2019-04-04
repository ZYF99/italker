package com.zyf.italker.italker.frags.account;



import android.content.Context;
import android.widget.EditText;

import com.zyf.italker.common.app.PresenterFragment;
import com.zyf.italker.factory.presenter.account.LoginContract;
import com.zyf.italker.factory.presenter.account.LoginPresenter;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆的界面
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
implements LoginContract.View{
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger)context;

    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        //调用p层进行注册
        mPresenter.login(phone,password);
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick(){
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
        mPassword.setEnabled(true);
        //让按钮可以点击
        mSubmit.setEnabled(true);

    }

    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }


}
