package com.zyf.italker.italker.frags.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zyf.italker.common.app.Application;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.frags.media.GalleryFragment;
import java.util.List;
import java.util.Objects;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权申请弹出框
 */
public class PermissionsFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {
    //权限回调标识
    private static final int RC = 0x0100;

    public PermissionsFragment() {
        // Required empty public constructor
    }



    //当创建dialog时
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //复用即可
        return new GalleryFragment.TransStausBottomSheetDialog(getContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取布局中的控件
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        //找到按钮，点击时申请权限
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPerm();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshState(Objects.requireNonNull(getView()));
    }

    /**
     * 刷新我们的布局中的图片的状态
     *
     * @param root 根布局
     */
    private void refreshState(View root) {
        if(root==null)
        {
            return;
        }
        Context context = getContext();
        root.findViewById(R.id.im_state_permission_network)
                .setVisibility(haveNetWorkPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_read)
                .setVisibility(haveReadPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_write)
                .setVisibility(haveWritePerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility(haveRecordAudioPerm(context) ? View.VISIBLE : View.GONE);

    }

    /**
     * 获取是否有网络权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveNetWorkPerm(Context context) {
        //需要检查的网络权限
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储读取权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveReadPerm(Context context) {
        //需要检查的外部存储读取权限
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储写入权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveWritePerm(Context context) {
        //需要检查的外部存储写入权限
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有录音权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveRecordAudioPerm(Context context) {
        //需要检查的录音权限
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };
        return EasyPermissions.hasPermissions(context, perms);
    }


    //私有的show方法
    private static void show(FragmentManager fragmentManager) {
        //调用BottomSheetDialog已经准备好的显示方法
        new PermissionsFragment().show(fragmentManager, PermissionsFragment.class.getName());
    }

    /**
     * 检查是否具有所有的权限
     *
     * @param context         Context
     * @param fragmentManager FragmentManager
     * @return 是否有权限
     */
    public static boolean haveAll(Context context, FragmentManager fragmentManager) {

        Boolean haveAll = haveNetWorkPerm(context)
                && haveReadPerm(context)
                && haveWritePerm(context)
                && haveRecordAudioPerm(context);
        if (!haveAll) {
            show(fragmentManager);
        }
        return haveAll;
    }


    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    private void requestPerm() {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            Application.showToast(R.string.label_permission_ok);
            //Fragment中调用getView得到根部局，前提是在onCreateView之后
            refreshState(Objects.requireNonNull(getView()));
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions), RC, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //如果权限中有 没有申请成功的权限存在，则弹出弹出框，用户点击后去设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
    }

    /**
     * 权限申请的时候回调的方法
     * 在这个方法中把对应的权限申请状态交给EasyPermissions框架
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //传递对应的参数，并且告知接受处理权限的处理者是我自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
