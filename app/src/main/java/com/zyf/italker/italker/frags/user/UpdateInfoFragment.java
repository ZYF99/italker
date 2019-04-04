package com.zyf.italker.italker.frags.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.zyf.italker.common.app.Application;
import com.zyf.italker.common.app.PresenterFragment;
import com.zyf.italker.common.widget.a.PortraitView;
import com.zyf.italker.factory.presenter.user.UpdateInfoContract;
import com.zyf.italker.factory.presenter.user.UpdateInfoPresenter;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.activities.MainActivity;
import com.zyf.italker.italker.frags.media.GalleryFragment;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;
import butterknife.BindView;
import butterknife.OnClick;
import static android.app.Activity.RESULT_OK;

/**
 * 用户更新信息的界面
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
implements UpdateInfoContract.View{

    @BindView(R.id.im_sex)
    ImageView mSex;

    @BindView(R.id.edit_desc)
    EditText mDesc;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;


    //头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        //show的时候建议使用getChildFragmengtManager
        //tag GalleryFragment class名
        new GalleryFragment()
                .setListener((String path) -> {//lambda 接口中有且仅有一个方法需要实现时

                    //当有图片被点击时触发
                    UCrop.Options options = new UCrop.Options();
                    //设置图片处理的格式JPEG
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    //设置压缩后的图片精度
                    options.setCompressionQuality(96);

                    //得到头像的缓存地址
                    File dPath = Application.getPortraitTmpFile();
                    UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                            .withAspectRatio(1, 1)//1:1比例
                            .withMaxResultSize(520, 520)//返回最大的尺寸
                            .withOptions(options)//相关参数
                            .start(getActivity());

                }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }


    /**
     * 处理照片后返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        //如果是我能处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //通过得到对应的uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        mPortraitPath = uri.getPath();
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);

    }

    @OnClick(R.id.im_sex)
    void onSexClick(){
        //性别图标点击触发
        isMan = !isMan;//反向性别

        Drawable drawable = getResources().getDrawable(isMan?
        R.drawable.ic_sex_man:R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
        //设置背景的层级，切换颜色
        mSex.getBackground().setLevel(isMan?0:1);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String desc = mDesc.getText().toString();
        //调用p层进行更新
        mPresenter.update(mPortraitPath,desc,isMan);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //正在进行时，正在进行注册，界面不可操作
        //开始Loading
        mLoading.start();
        //让控件不可以输入
        mDesc.setEnabled(false);
        mPortrait.setEnabled(false);
        mSex.setEnabled(false);
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
        mDesc.setEnabled(true);
        mPortrait.setEnabled(true);
        mSex.setEnabled(true);
        //让按钮可以点击
        mSubmit.setEnabled(true);

    }



    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        //初始化presenter
        return new UpdateInfoPresenter(this);
    }

    @Override
    public void updateSucceed() {
        //更新成功跳转到主界面
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
