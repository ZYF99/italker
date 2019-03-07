package com.zyf.italker.italker.frags.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.zyf.italker.common.app.Application;
import com.zyf.italker.common.widget.a.PortraitView;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.frags.media.GalleryFragment;
import java.io.File;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 用户更新信息的界面
 */
public class UpdateInfoFragment extends com.zyf.italker.common.app.Fragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

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
    * */
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
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }
}
